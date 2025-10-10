# File System Simulation: Implementing File Allocation Algorithms

This project simulates how different file allocation methods manage disk space in an operating system using Java. It demonstrates contiguous, linked, and indexed allocation techniques through a simple command-line simulation.

## 🎯 Objectives
- Implement three file allocation methods: Contiguous, Linked, and Indexed.
- Simulate file creation, deletion, and disk space management (e.g. free, used, or reserved).
- Measure efficiency in terms of disk utilization and access performance.


## 🗂️ Project Structure
```
FileSystemSimulation/
│
├── src/
│   ├── Main.java                         # Entry point of the program
│   ├── filesystem/
│   │   ├── FileSystem.java               # Manages disk, has inner FileEntry
│   │   ├── AllocationMethod.java         # Interface defining create/delete/show methods
│   │   ├── ContiguousAllocation.java     # Continuous block storage implementation
│   │   ├── LinkedAllocation.java         # Scattered blocks linked by pointers
│   │   └── IndexedAllocation.java        # Uses index block for scattered storage
│   ├── FileSystemTest.java  # Use JUnit 5 for testing 
└── README.md

```

## ▶️ How to Run
1. Compile all Java files:
   ```bash
   javac src/**/*.java


## Terminal User Flow 

1️⃣ Start Simulation

⚙️ CONTIGUOUS ALLOCATION FLOW

=== FILE SYSTEM SIMULATION ===
Select Allocation Method:
1. Contiguous
2. Linked
3. Indexed
Enter choice (e.g. 1-3): 1

Select storage device size:
1. 100 MB
2. 500 MB
3. 1 GB
4. Custom (MB)
Enter choice: 2

Select block size:
1. 4 KB
2. 8 KB
3. 16 KB
4. Custom (KB) # set limitation
Enter choice: 1

Storage: 500 MB, Block Size: 4 KB → Total Blocks: 128,000
Contiguous Allocation selected.

2️⃣ File System Menu
---- FILE SYSTEM MENU ----
1. Create File
2. Delete File
3. Show File System Status
4. Exit
Enter your choice (e.g. 1-4):


💾 CASE 1 — Create File
Enter file name: report
Enter file size (KB): 12
File 'report.txt' created successfully (blocks 0–2).

Disk (Contiguous Allocation):
[ A ][ A ][ A ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]

If you create another file:

Enter file name: data
Enter file size (KB): 16
File 'data.txt' created successfully (blocks 3–6).

Disk:
[ A ][ A ][ A ][ B ][ B ][ B ][ B ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]



🗑️ CASE 2 — Delete File
Enter file name to delete: report
File 'report.txt' deleted successfully.

Disk:
[ ][ ][ ][ B ][ B ][ B ][ B ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]



📊 CASE 3 — Show File System Status [after the "report" is deleted, case 3 shows the status]
---- FILE SYSTEM STATUS ----
Total Blocks: 128,000
Used Blocks: 4
Free Blocks: 127,996

Files:
1. data.txt → blocks [3–6]
Disk Visual:
[ ][ ][ ][ B ][ B ][ B ][ B ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]



🚪 CASE 4 — Exit
Exiting simulation...

Would you like to try another allocation method? (y/n): y

-------------------------------------------------------------------------
1️⃣ Start Simulation
⚙️ LINKED ALLOCATION FLOW
Select Allocation Method:
1. Contiguous
2. Linked
3. Indexed
Enter choice (e.g. 1-3): 2
Linked Allocation selected.

Enter storage device size (MB):
> 100

Enter block size (KB):
> 4

Storage: 100 MB, Block Size: 4 KB → Total Blocks: 25,600
Disk initialized.



🧩 Case 1: Create File
Enter file name: notes.txt
Enter file size (KB): 12

Each block = 4 KB → File requires 3 blocks.

File 'notes.txt' created using Linked Allocation.

📊 Visual Representation of Case 1:
Disk Blocks:
[0:A→7] [7:A→12] [12:A→null] [ ][ ][ ][ ][ ] ...

Legend:
A → notes.txt
Each block stores data + a pointer to the next block



🧹 Case 2: Delete File
---- File System Menu ----
1. Create File
2. Delete File
3. Show File System Status
4. Exit
Enter your choice: 2

Enter file name to delete: notes.txt 
File 'notes.txt' deleted successfully.

[ ][ ][ ][ ][ ][ ][ ][ ][ ] ...
All blocks freed. 



📦 Case 3: Show File System Status
---- File System Menu ----
1. Create File
2. Delete File
3. Show File System Status
4. Exit
Enter your choice: 3

Disk Status:
Total Blocks: 25,600
Used Blocks: 0
Free Blocks: 25,600

Linked Files:
(none)

❌ Exit
Enter your choice: 4
Exiting simulation...

Would you like to try another allocation method? (y/n): y

-------------------------------------------------------------------------
1️⃣ Start Simulation
⚙️INDEXED ALLOCATION FLOW

Select Allocation Method:
1. Contiguous
2. Linked
3. Indexed
Enter choice (e.g. 1-3): 3
Indexed Allocation selected.

Enter storage device size (MB):
> 50

Enter block size (KB):
> 4

Storage: 50 MB, Block Size: 4 KB → Total Blocks: 12,800
Disk initialized.


---- File System Menu ----
1. Create File
2. Delete File
3. Show File System Status
4. Exit
Enter your choice: 1


🧩 Case 1: Create File
Enter file name: report.txt
Enter file size (KB): 12

Each block = 4 KB → File requires 3 blocks.

File 'report.txt' created using Indexed Allocation.
Index block: 0 → [2, 5, 8]

📊 Visual Representation of Case 1:

[0: Index of report.txt → 2,5,8]
[2: Data] [5: Data] [8: Data] [ ][ ][ ][ ] ...

Legend:
Block 0 = index (holds pointers)
Blocks 2,5,8 = data blocks



🧹 Case 2: Delete File
---- File System Menu ----
1. Create File
2. Delete File
3. Show File System Status
4. Exit
Enter your choice: 2

Enter file name to delete: report.txt
File 'report.txt' deleted successfully.


📊 Disk After Deletion:

[ ][ ][ ][ ][ ][ ][ ][ ][ ] ...
Index and data blocks freed.



📦 Case 3: Show File System Status
---- File System Menu ----
1. Create File
2. Delete File
3. Show File System Status
4. Exit
Enter your choice: 3

Disk Status:
Total Blocks: 12,800
Used Blocks: 0
Free Blocks: 12,800

Indexed Files:
(none)

❌ Exit
Enter your choice: 4
Exiting simulation...

Would you like to try another allocation method? (y/n): n
Simulation ended.
