# JVM Report

This report covers how the Java Virtual Machine works under the hood — the key components that make Java programs run, and why "Write Once, Run Anywhere" actually works in practice.

## 1. Class Loader Subsystem

The class loader is responsible for finding, loading, and linking `.class` files into the JVM at runtime. It works in three phases:

**Loading** — The class loader reads the bytecode from `.class` files and creates a `Class` object in memory. There are three built-in class loaders:
- **Bootstrap ClassLoader** — Loads core Java classes from `rt.jar` (like `java.lang.*`). It's written in native code.
- **Extension/Platform ClassLoader** — Loads classes from the `ext` directory or platform-specific modules.
- **Application ClassLoader** — Loads our application classes from the classpath. When we run `java -cp out com.airtribe.meditrack.Main`, this is the loader that picks up our compiled files.

**Linking** — This happens in three steps:
1. *Verification* — Checks that the bytecode is valid and doesn't violate Java's security constraints.
2. *Preparation* — Allocates memory for static variables and sets them to default values.
3. *Resolution* — Replaces symbolic references with direct references (e.g., resolving method calls to actual memory addresses).

**Initialization** — Runs static initializers and static blocks. In MediTrack, the `Constants` class has a `static` block that prints the app name and version when the class is first loaded. This is when that code executes.

### Delegation Model

Class loaders follow a parent-delegation model. When asked to load a class, a loader first delegates to its parent. This prevents duplicate loading and ensures core Java classes always come from the bootstrap loader, not from some random jar on the classpath.

## 2. Runtime Data Areas

When the JVM starts, it allocates several memory regions:

### Heap
The heap is where all objects live. When we do `new Patient(...)`, that object gets allocated on the heap. It's shared across all threads and is managed by the garbage collector. In MediTrack, every `Patient`, `Doctor`, `Appointment`, and `Bill` instance lives here.

### Stack
Each thread gets its own stack. Every method call creates a new "stack frame" that holds local variables, the operand stack, and a reference back to the method's class. When a method returns, its frame is popped off. So when `PatientService.addPatient()` calls `Validator.validateName()`, a new frame is pushed for the validator, and it's popped when validation completes.

### Method Area (Metaspace)
Stores class-level data — class structure, method bytecode, static variables, and the constant pool. Since Java 8, this is called Metaspace and it's allocated from native memory (no more PermGen space issues). Our enum classes like `Specialization` and `AppointmentStatus` have their constants stored here.

### PC Register
Each thread has a program counter register that tracks which JVM instruction is currently being executed. It's a small piece of memory, but it's how the JVM knows where it is in the bytecode at any given moment.

### Native Method Stack
Similar to the Java stack, but for native methods (JNI calls). We don't use native methods directly in MediTrack, but the JVM itself relies on them internally.

## 3. Execution Engine

The execution engine is what actually runs our bytecode. It has three main components:

### Interpreter
Reads bytecode instructions one by one and executes them. It's simple and starts fast, but it's not the most efficient for code that runs repeatedly. When MediTrack first starts up, the interpreter handles the initial execution.

### JIT Compiler (Just-In-Time)
This is where the performance magic happens. The JIT compiler identifies "hot spots" — methods or loops that are called frequently — and compiles them directly to native machine code. After that, the JVM runs the native code instead of interpreting bytecode, which is significantly faster.

For example, if a user is doing a lot of searches and the `DataStore.filter()` method is called hundreds of times, the JIT compiler will eventually compile it to native code for better performance.

The JIT uses two compilation tiers:
- **C1 (Client Compiler)** — Quick compilation with basic optimizations. Used for methods that are "warm" but not heavily used.
- **C2 (Server Compiler)** — More aggressive optimizations for truly hot methods. Takes longer to compile but produces faster code.

### Garbage Collector
Automatically reclaims memory from objects that are no longer referenced. When a `Bill` object is created, used, and then goes out of scope, the GC eventually frees that heap memory. We never have to manually free memory — that's one of Java's biggest advantages over languages like C/C++.

Modern JVMs offer several GC algorithms (G1, ZGC, Shenandoah) that balance throughput vs. pause times.

## 4. JIT Compiler vs. Interpreter

| Aspect | Interpreter | JIT Compiler |
|---|---|---|
| Speed | Slower (line by line) | Fast (native code) |
| Startup | Immediate | Needs warm-up time |
| Memory | Low overhead | Caches compiled code |
| When used | Cold code, first execution | Hot methods after threshold |

In practice, the JVM uses both together. The interpreter handles startup and infrequently called code, while the JIT kicks in for performance-critical paths. This hybrid approach gives Java both reasonable startup time and strong runtime performance.

## 5. Write Once, Run Anywhere

This is Java's core portability promise, and it works because of the compilation model:

1. **We write** `.java` source code
2. **javac compiles** it to platform-independent `.class` files containing bytecode
3. **The JVM interprets/compiles** that bytecode on whatever platform it's running on

The bytecode is the same whether you're on macOS, Windows, or Linux. The JVM is the platform-specific piece — there's a different JVM implementation for each OS, but they all understand the same bytecode format.

In MediTrack's case, I can compile the project on my Mac, hand someone the `.class` files, and they can run it on a Windows machine with a JVM installed. The bytecode doesn't change. The JVM handles all the OS-specific details (file paths, memory management, threading) under the covers.

This is fundamentally different from languages like C++, where you'd need to recompile for each target platform. With Java, the compilation step is platform-independent, and the JVM acts as an abstraction layer between the bytecode and the hardware.

## Summary

The JVM is essentially a well-designed runtime that gives us:
- Automatic memory management (GC)
- Platform independence (bytecode + platform-specific JVM)
- Performance (JIT compilation for hot code)
- Security (bytecode verification, class loader isolation)

Understanding these internals helps when debugging performance issues, memory leaks, or class loading problems in larger applications.
