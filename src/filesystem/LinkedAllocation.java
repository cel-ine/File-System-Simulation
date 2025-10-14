
package filesystem;

import java.util.*;

// Brief Description: It implements the linked allocation (each file is stored in scattered blocks and connected by pointers. Each block can only know the next block connected to it but not the overall linked data block locations)

public class LinkedAllocation implements AllocationMethod {
    private final FileSystem fs;
    private final Map<String, Map<Integer, Integer>> pointers = new HashMap<>();

    public LinkedAllocation(FileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void createFile(String fileName, int fileSizeKB) {
        int blocksNeeded = (int) Math.ceil((double) fileSizeKB / fs.getBlockSizeKB());
        List<Integer> allocated = new ArrayList<>();

        for (int i = 0; i < fs.getTotalBlocks() && allocated.size() < blocksNeeded; i++) {
            if (fs.isFree(i)) {
                fs.occupyBlock(i);
                allocated.add(i);
            }
        }

        if (allocated.size() < blocksNeeded) {
            System.out.println("❌ Not enough space for file: " + fileName);
            for (int i : allocated) fs.freeBlock(i);
            return;
        }

        Map<Integer, Integer> links = new LinkedHashMap<>();
        for (int i = 0; i < allocated.size() - 1; i++) {
            links.put(allocated.get(i), allocated.get(i + 1));
        }
        links.put(allocated.get(allocated.size() - 1), null);
        pointers.put(fileName, links);

        fs.addFile(new FileSystem.FileEntry(fileName, allocated));
        System.out.println("✅ File '" + fileName + "' created using Linked Allocation.");
    }

    @Override
    public void deleteFile(String fileName) {
        if (!pointers.containsKey(fileName)) {
            System.out.println("❌ File not found: " + fileName);
            return;
        }
        for (int block : pointers.get(fileName).keySet()) {
            fs.freeBlock(block);
        }
        pointers.remove(fileName);
        fs.removeFile(fileName);
        System.out.println("🗑️ File '" + fileName + "' deleted successfully.");
    }

    @Override
    public void showStatus() {
        int used = 0;
        for (int i = 0; i < fs.getTotalBlocks(); i++) {
            if (!fs.isFree(i)) used++;
        }

        System.out.println("\n---- FILE SYSTEM STATUS ----");
        System.out.println("Total Blocks: " + fs.getTotalBlocks());
        System.out.println("Used Blocks: " + used);
        System.out.println("Free Blocks: " + (fs.getTotalBlocks() - used));
        System.out.println("Linked Files:");

        for (var entry : pointers.entrySet()) {
            System.out.println("- " + entry.getKey() + " → " + entry.getValue());
        }
        fs.showDiskVisual();
    }
}
