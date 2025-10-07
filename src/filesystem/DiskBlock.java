package src.filesystem;

/**
 * Represents a single disk block in the simulated file system.
 * Each block can be free or occupied by a file.
 */
public class DiskBlock {
    private boolean isFree;     // true if the block is available, false if used
    private String fileName;    // name of the file using this block (optional info)

    // Constructor: starts as free
    public DiskBlock() {
        this.isFree = true;
        this.fileName = null;
    }

    // Allocate this block to a file
    public void allocate(String fileName) {
        this.isFree = false;
        this.fileName = fileName;
    }

    // Free this block
    public void free() {
        this.isFree = true;
        this.fileName = null;
    }

    // Getters
    public boolean isFree() {
        return isFree;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        return isFree ? "[FREE]" : "[" + fileName + "]";
    }
}

