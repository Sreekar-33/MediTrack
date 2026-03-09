# Setup Instructions

This guide walks you through setting up and running the MediTrack project on your machine.

## 1. Install Java JDK

You need JDK 11 or higher. I used JDK 24 during development, but anything 11+ works fine.

### macOS
If you have Homebrew:
```bash
brew install openjdk@24
```

Then add it to your path:
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

### Windows
1. Download the JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/)
2. Run the installer
3. Add `JAVA_HOME` to your System Environment Variables pointing to the JDK folder
4. Add `%JAVA_HOME%\bin` to your `PATH`

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

## 2. Verify Installation

Open a terminal and run:
```bash
java -version
javac -version
```

You should see something like:
```
java version "24.0.x" ...
javac 24.0.x
```

This confirms both the JRE (runtime) and JDK (compiler) are properly installed.

## 3. Clone / Download the Project

```bash
git clone <repository-url>
cd MediTrack
```

Or just download and extract the zip.

## 4. Project Layout

```
MediTrack/
├── src/main/java/com/airtribe/meditrack/   # All source files
├── data/                                    # CSV files (created at runtime)
├── docs/                                    # Documentation
└── README.md
```

## 5. Compile

From the `MediTrack` root directory:

```bash
javac -d out $(find src -name "*.java")
```

This compiles everything into the `out/` directory. On Windows, you can list the files manually:

```cmd
dir /s /B src\*.java > sources.txt
javac -d out @sources.txt
```

## 6. Run the Application

```bash
java -cp out com.airtribe.meditrack.Main
```

You'll see the main menu. Just follow the prompts.

### Loading Saved Data

If you previously saved data (option 8 in the menu), you can reload it on startup:
```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

This reads from the CSV files in the `data/` folder.

## 7. Run Tests

```bash
java -cp out com.airtribe.meditrack.test.TestRunner
```

This runs all 57 manual tests and prints PASS/FAIL for each one.

## 8. Generate JavaDoc (Optional)

```bash
javadoc -d javadoc -sourcepath src/main/java -subpackages com.airtribe.meditrack
```

Open `javadoc/index.html` in your browser to view the generated docs.

## Troubleshooting

- **"javac not found"** — Make sure `JAVA_HOME/bin` is in your PATH
- **"could not find or load main class"** — Make sure you compiled with `-d out` and are running with `-cp out`
- **CSV errors on load** — The `data/` folder must exist. It's created automatically when you save, but if you cloned fresh and try `--loadData` without saving first, it'll just warn and continue
