# MediTrack — Clinic & Appointment Management System

MediTrack is a console-based clinic management system built in Core Java. It handles patient registration, doctor management, appointment scheduling, and billing — all through a clean, menu-driven interface.

The project was built to practice and demonstrate core Java concepts: OOP fundamentals, design patterns, generics, exception handling, file I/O, streams, and basic concurrency.

## Features

- **Patient & Doctor CRUD** — Add, update, remove, and search
- **Appointments** — Create, confirm, cancel with status tracking via enums
- **Billing** — Multiple billing strategies (Standard, Premium, Insurance) using the Strategy pattern
- **Search** — Overloaded search by ID, name, or age
- **AI Recommendations** — Rule-based doctor suggestions based on symptoms
- **CSV Persistence** — Save/load all data to CSV files
- **Observer Notifications** — Console alerts on appointment events

## Quick Start

### Prerequisites
- Java JDK 11 or higher

### Compile
```bash
cd MediTrack
javac -d out $(find src -name "*.java")
```

### Run the Application
```bash
java -cp out com.airtribe.meditrack.Main
```

### Run with Persisted Data
```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

### Run Tests
```bash
java -cp out com.airtribe.meditrack.test.TestRunner
```

## Sample Run Output

```
[Config] MediTrack v1.0.0 initialized.

===================================================
  Welcome to MediTrack v1.0.0
  Clinic & Appointment Management System
===================================================

--- Main Menu ---
1. Manage Patients
2. Manage Doctors
3. Manage Appointments
4. Billing
5. Search
6. AI Doctor Recommendation
7. Analytics (Streams)
8. Save Data
9. Save & Exit
Enter choice: 1

--- Patient Management ---
1. Add Patient
2. View All Patients
3. Update Patient
4. Remove Patient
5. Add Medical Record
6. Back
Enter choice: 1
Name: John Doe
Age: 30
Phone (10 digits): 9876543210
Email: john@example.com
Blood Group: O+
Patient added successfully! ID: PAT-101
Patient | Name: John Doe | Age: 30 | Phone: 9876543210 | Email: john@example.com | Blood Group: O+ | History: []
```

## Concepts Demonstrated

| Concept | Where |
|---|---|
| Encapsulation | Private fields + getters/setters in all entities |
| Inheritance | Person → Doctor, Patient; MedicalEntity → Person, Appointment |
| Polymorphism | Overloaded search methods; overridden generateBill() |
| Abstraction | MedicalEntity (abstract), Person (abstract) |
| Interfaces | Searchable, Payable with default methods |
| Generics | DataStore\<T\>, Searchable\<T\> |
| Enums | Specialization, AppointmentStatus |
| Cloning | Deep copy in Patient and Appointment |
| Immutability | BillSummary (final class, final fields) |
| Static blocks | Constants class initialization |
| Custom Exceptions | InvalidDataException, AppointmentNotFoundException |
| Exception chaining | Wrapping root causes in custom exceptions |
| Try-with-resources | CSVUtil file operations |
| File I/O | CSV save/load for all entities |
| Singleton | IdGenerator (eager initialization) |
| Factory | BillFactory.createBill() |
| Strategy | BillingStrategy with Standard/Premium/Insurance |
| Observer | AppointmentObserver notifications |
| Streams & Lambdas | Analytics, filtering, grouping |
| Concurrency | AtomicInteger (IdGenerator), TimerTask (reminders) |

## Documentation

- [Setup Instructions](docs/Setup_Instructions.md)
- [JVM Report](docs/JVM_Report.md)
- [Design Decisions](docs/Design_Decisions.md)
