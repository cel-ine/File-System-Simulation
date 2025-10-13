package filesystem;

import java.util.*;

public class FileSystem {
    private final int totalBlocks;
    private final int blockSizeKB;
    private final boolean[] disk; // true = used, false = free
    private final Map<String, FileEntry> files;

    public FileSystem(int totalBlocks, int blockSizeKB) {
        this.totalBlocks = totalBlocks;
        this.blockSizeKB = blockSizeKB;
        this.disk = new boolean[totalBlocks];
        this.files = new HashMap<>();
    }

    public int getTotalBlocks() {
        return totalBlocks;
    }

    public int getBlockSizeKB() {
        return blockSizeKB;
    }

    public boolean isFree(int index) {
        return !disk[index];
    }

    public void occupyBlock(int index) {
        disk[index] = true;
    }

    public void freeBlock(int index) {
        disk[index] = false;
    }

    public void addFile(FileEntry file) {
        files.put(file.getFileName(), file);
    }

    public void removeFile(String fileName) {
        files.remove(fileName);
    }

    public Map<String, FileEntry> getFiles() {
        return files;
    }

    public void showDiskVisual() {
        System.out.print("Disk Visual: ");
        for (int i = 0; i < Math.min(20, totalBlocks); i++) {
            System.out.print(disk[i] ? "[#]" : "[ ]");
        }
        System.out.println(" ...");
    }

    // Inner class representing a file entry
    public static class FileEntry {
        private final String fileName;
        private final List<Integer> allocatedBlocks;

        public FileEntry(String fileName, List<Integer> allocatedBlocks) {
            this.fileName = fileName;
            this.allocatedBlocks = allocatedBlocks;
        }

        public String getFileName() {
            return fileName;
        }

        public List<Integer> getAllocatedBlocks() {
            return allocatedBlocks;
        }
    }
}
