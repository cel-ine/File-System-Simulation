package src.filesystem;

import java.util.Arrays;

/**
 * FileSystem class
 * -----------------
 * Acts as the main controller for the simulated file system.
 * It manages the available disk blocks and delegates all file operations
 * (create, delete, show) to a chosen file allocation method (strategy).
 */
public class FileSystem {
    // Reference to the chosen file allocation method (Contiguous, Linked, or Indexed)
    private final AllocationMethod method;

    // Boolean array representing disk blocks (true = free, false = used)
    private final boolean[] freeBlocks;

    /**
     * Constructor
     * Initializes the file system with a specific allocation method
     * and the total number of available disk blocks.
     */
    public FileSystem(AllocationMethod method, int totalBlocks) {
        this.method = method;
        this.freeBlocks = new boolean[totalBlocks];
        Arrays.fill(freeBlocks, true); // All blocks start as free (unallocated)
    }

    /**
     * createFile()
     * Requests the allocation method to create a file of a given size.
     * The method will find free blocks and allocate them as needed.
     */
    public void createFile(String name, int size) {
        method.createFile(name, size, freeBlocks);
    }

    /**
     * deleteFile()
     * Requests the allocation method to delete the specified file.
     * The method will free up the blocks that were used by that file.
     */
    public void deleteFile(String name) {
        method.deleteFile(name, freeBlocks);
    }

    /**
     * showStatus()
     * Displays the current state of the file system,
     * including which files exist and how blocks are allocated.
     */
    public void showStatus() {
        method.showStatus();
    }
}

