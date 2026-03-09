# Design Decisions

This document explains the key architectural and design choices I made while building MediTrack.

## Package Structure

The packages are organized by responsibility:
- `entity` — Data models (what things are)
- `service` — Business logic (what things do)
- `util` — Shared utilities (helpers)
- `exception` — Custom exception types
- `interfaces` — Contracts and abstractions
- `constants` — Application-wide configuration
- `test` — Manual test runner

## Inheritance Hierarchy

I introduced `MedicalEntity` as the abstract base class for all trackable entities. `Person` extends it and adds personal info fields. `Doctor` and `Patient` extend `Person`. `Appointment` and `Bill` extend `MedicalEntity` directly since they're not people but still need an ID and common behavior.

```
MedicalEntity (abstract)
├── Person (abstract)
│   ├── Doctor
│   └── Patient
├── Appointment
└── Bill
```

This felt like a natural hierarchy. I considered making `Appointment` independent (not extending MedicalEntity), but having a shared `id` field and `getDetails()` contract across all entities made the code cleaner.

## Why DataStore<T> Instead of Raw Collections

The project requires demonstrating generics, so I created `DataStore<T extends MedicalEntity>` as a typed wrapper around a `HashMap`. It gives us:
- Type-safe storage per entity
- O(1) lookups by ID
- Built-in filtering with Predicate (lambda-friendly)
- Implements `Iterable<T>` for for-each loops

I could have just used `ArrayList` everywhere, but the generic store makes the service layer cleaner and demonstrates generics + iterators in a practical way.

## Billing: Strategy + Factory

Billing was a good fit for combining two patterns:

**Strategy Pattern** — `BillingStrategy` interface with three implementations (Standard, Premium, Insurance). Each calculates the total differently. This makes it trivial to add new billing types later without touching existing code.

**Factory Pattern** — `BillFactory.createBill()` takes a strategy name string and constructs the right `Bill` object. The caller doesn't need to know about strategy classes directly.

I put the strategy implementations as static inner classes inside `BillFactory` since they're small and tightly coupled to the factory. If they grew more complex, I'd move them to their own files.

## Singleton for IdGenerator

`IdGenerator` uses eager initialization (a `static final` instance) because:
- It's needed immediately on first use
- There's no expensive setup to defer
- It's the simplest thread-safe approach

I used `AtomicInteger` for the counters to make ID generation thread-safe without explicit synchronization. Even though MediTrack is mostly single-threaded, the `TimerTask` runs on a separate thread, so thread safety matters.

## Deep Cloning

Both `Patient` and `Appointment` implement `Cloneable` with proper deep copy:
- `Patient.clone()` creates a new copy of the `medicalHistory` list so modifying the clone's history doesn't affect the original
- `Appointment.clone()` deep-clones the nested `Patient` reference

I demonstrated this explicitly in the test runner to show the difference between shallow and deep copies.

## Immutable BillSummary

`BillSummary` is a `final` class with all `final` fields and no setters. Once created, it can't be modified. This is useful for creating read-only snapshots of billing data that can be safely shared across threads or stored for audit purposes.

## Exception Strategy

I created two custom exceptions:
- `InvalidDataException` — For validation failures (bad input)
- `AppointmentNotFoundException` — For missing appointment lookups

Both support exception chaining (wrapping a root cause). The `Validator` class throws `InvalidDataException` with descriptive messages, and the service layer lets these propagate up to `Main.java` where they're caught and displayed to the user.

I used `try-with-resources` in `CSVUtil` for all file operations to ensure proper resource cleanup.

## Concurrency

I kept concurrency minimal since this is a CLI app:
- `AtomicInteger` in `IdGenerator` for thread-safe counters
- `TimerTask` running on a daemon thread for periodic appointment reminders
- `synchronized` isn't explicitly used but the data structures are safe for the single-writer pattern we have


## AI Helper

The "AI" feature is rule-based — a static map of symptoms to specializations. It matches keywords in the user's symptom description and recommends doctors with matching specializations, sorted by consultation fee (cheapest first).

It's simple but demonstrates streams, maps, and functional-style filtering. A real implementation would obviously need something more sophisticated, but this covers the project requirements nicely.

## CSV Persistence

I chose CSV over Java serialization for the primary persistence format because:
- CSV files are human-readable (easy to debug)
- No dependency on class versions (serialization breaks if you change class structure)
- The brief specifically mentioned `String.split(",")` and `CSVUtil`

The `--loadData` command-line argument triggers data loading on startup, matching the project requirements.
