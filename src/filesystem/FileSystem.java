package filesystem;

import java.util.*;

/**
 * FileSystem class
 * -----------------
 * Controls the simulated file system and visually displays storage blocks.
 * Supports Contiguous, Linked, and Indexed allocation strategies.
 */
public class FileSystem {
    private final AllocationMethod method;
    private final boolean[] freeBlocks;
    private final Map<String, FileEntry> fileTable = new HashMap<>();
    private final char[] blockSymbols; // stores which file letter occupies each block

    public FileSystem(AllocationMethod method, int totalBlocks) {
        this.method = method;
        this.freeBlocks = new boolean[totalBlocks];
        this.blockSymbols = new char[totalBlocks];
        Arrays.fill(freeBlocks, true);
        Arrays.fill(blockSymbols, ' ');
    }

    /** Create a file and visually show allocation */
    public void createFile(String name, int sizeKB, int blockSizeKB) {
        if (fileTable.containsKey(name)) {
            System.out.println("❌ File already exists: " + name);
            return;
        }

        int blocksNeeded = (int) Math.ceil((double) sizeKB / blockSizeKB);
        List<Integer> allocated = method.createFile(name, blocksNeeded, freeBlocks);

        if (allocated != null && !allocated.isEmpty()) {
            fileTable.put(name, new FileEntry(name, allocated, sizeKB));
            char symbol = getFileSymbol(name);
            for (int block : allocated) blockSymbols[block] = symbol;
            System.out.println("\n✅ Created file '" + name + "' (" + blocksNeeded + " blocks)");
            showDisk();
        } else {
            System.out.println("❌ Not enough space for '" + name + "'.");
        }
    }

    /** Delete file and show updated disk */
    public void deleteFile(String name) {
        if (!fileTable.containsKey(name)) {
            System.out.println("❌ File not found: " + name);
            return;
        }

        List<Integer> blocks = fileTable.get(name).blocks;
        method.deleteFile(name, freeBlocks, blocks);

        for (int block : blocks) blockSymbols[block] = ' ';
        fileTable.remove(name);
        System.out.println("\n🗑️ Deleted file '" + name + "'");
        showDisk();
    }

    /** Show file system summary and disk visualization */
    public void showStatus() {
        System.out.println("\n---- FILE SYSTEM STATUS ----");
        int used = 0;
        for (boolean b : freeBlocks) if (!b) used++;

        System.out.println("Total Blocks: " + freeBlocks.length);
        System.out.println("Used Blocks: " + used);
        System.out.println("Free Blocks: " + (freeBlocks.length - used));
        System.out.println("\nFiles:");
        if (fileTable.isEmpty()) {
            System.out.println("(none)");
        } else {
            for (FileEntry entry : fileTable.values()) {
                System.out.println(entry.name + " → " + entry.blocks);
            }
        }

        showDisk();
    }

    /** Visually prints the disk with each block as [A], [B], [ ] etc. */
    private void showDisk() {
        System.out.println("\nDisk Visualization:");
        int limit = Math.min(50, freeBlocks.length); // show only first 50
        for (int i = 0; i < limit; i++) {
            System.out.print("[" + (blockSymbols[i] == ' ' ? " " : blockSymbols[i]) + "]");
        }
        if (freeBlocks.length > 50) System.out.print(" ...");
        System.out.println();
    }

    /** Assign unique letter per file for display */
    private char getFileSymbol(String fileName) {
        char base = Character.toUpperCase(fileName.charAt(0));
        if (fileTable.containsKey(String.valueOf(base)))
            base = (char) ('A' + (fileTable.size() % 26)); // alternate if duplicate
        return base;
    }

    /** Inner class to track file metadata */
    private static class FileEntry {
        String name;
        List<Integer> blocks;
        int sizeKB;

        FileEntry(String name, List<Integer> blocks, int sizeKB) {
            this.name = name;
            this.blocks = blocks;
            this.sizeKB = sizeKB;
        }
    }
}
