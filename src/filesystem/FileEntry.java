package src.filesystem;

import java.util.List;

/**
 * Represents a file stored in the simulated file system.
 * Holds basic metadata like name, size, starting block, and block list (for non-contiguous methods).
 */
public class FileEntry {
    private String name;           // Name of the file
    private int size;              // File size in number of blocks
    private int startBlock;        // Starting block (used in contiguous allocation)
    private List<Integer> blocks;  // List of block indices (used in linked or indexed allocation)

    // Constructor for contiguous allocation
    public FileEntry(String name, int size, int startBlock) {
        this.name = name;
        this.size = size;
        this.startBlock = startBlock;
    }

    // Constructor for linked or indexed allocation
    public FileEntry(String name, int size, List<Integer> blocks) {
        this.name = name;
        this.size = size;
        this.blocks = blocks;
    }

    // Getters
    public String getName() { return name; }
    public int getSize() { return size; }
    public int getStartBlock() { return startBlock; }
    public List<Integer> getBlocks() { return blocks; }

    // To display file information
    @Override
    public String toString() {
        if (blocks != null)
            return name + " (Blocks: " + blocks + ")";
        else
            return name + " (Start: " + startBlock + ", Size: " + size + ")";
    }
}

