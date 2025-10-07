# File System Simulation: Implementing File Allocation Algorithms

This project simulates how different file allocation methods manage disk space in an operating system using Java. It demonstrates contiguous, linked, and indexed allocation techniques through a simple command-line simulation.

## 🎯 Objectives
- Implement three file allocation methods: Contiguous, Linked, and Indexed.
- Simulate file creation, deletion, and disk space management.
- Measure efficiency in terms of disk utilization and access performance.

## 🗂️ Project Structure
```
FileSystemSimulation/
│
├── src/
│   ├── Main.java                         # Entry point of the program
│   ├── filesystem/
│   │   ├── FileSystem.java               # Manages disk and delegates to allocation method
│   │   ├── FileEntry.java                # Stores file details
│   │   ├── DiskBlock.java                # Represents an individual disk block (free/used)
│   │   ├── AllocationMethod.java         # Interface defining create/delete/show methods
│   │   ├── ContiguousAllocation.java     # Continuous block storage implementation
│   │   ├── LinkedAllocation.java         # Scattered blocks linked by pointers
│   │   └── IndexedAllocation.java        # Uses index block for scattered storage
│   └── simulation/
│       └── FileSystemSimulation.java     # Runs and compares different allocation methods
├── ui/
│   ├── index.html               # Simple interactive UI
│   ├── style.css                # Basic clean styling
│   └── script.js                # Handles user interactions
```

## ▶️ How to Run
1. Compile all Java files:
   ```bash
   javac src/**/*.java


## Empty Java File to be modified:
- ContiguousAllocation.java
- LinkedAllocation.java 
- IndexedAllocation.java
