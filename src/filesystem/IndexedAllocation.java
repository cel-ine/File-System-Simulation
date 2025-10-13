
package filesystem;

import java.util.*;

public class IndexedAllocation implements AllocationMethod {
    private final FileSystem fs;
    private final Map<String, Integer> indexBlocks = new HashMap<>();

    public IndexedAllocation(FileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void createFile(String fileName, int fileSizeKB) {
        int blocksNeeded = (int) Math.ceil((double) fileSizeKB / fs.getBlockSizeKB());
        List<Integer> dataBlocks = new ArrayList<>();

        // find one free block for index
        int indexBlock = -1;
        for (int i = 0; i < fs.getTotalBlocks(); i++) {
            if (fs.isFree(i)) {
                indexBlock = i;
                fs.occupyBlock(i);
                break;
            }
        }

        if (indexBlock == -1) {
            System.out.println("❌ No space for index block.");
            return;
        }

        // allocate data blocks
        for (int i = 0; i < fs.getTotalBlocks() && dataBlocks.size() < blocksNeeded; i++) {
            if (fs.isFree(i)) {
                fs.occupyBlock(i);
                dataBlocks.add(i);
            }
        }

        if (dataBlocks.size() < blocksNeeded) {
            System.out.println("❌ Not enough space for data blocks.");
            fs.freeBlock(indexBlock);
            return;
        }

        indexBlocks.put(fileName, indexBlock);
        fs.addFile(new FileSystem.FileEntry(fileName, dataBlocks));

        System.out.printf("✅ File '%s' created using Indexed Allocation. Index Block: %d → %s\n",
                fileName, indexBlock, dataBlocks);
    }

    @Override
    public void deleteFile(String fileName) {
        Integer indexBlock = indexBlocks.get(fileName);
        if (indexBlock == null) {
            System.out.println("❌ File not found: " + fileName);
            return;
        }

        fs.freeBlock(indexBlock);
        for (int block : fs.getFiles().get(fileName).getAllocatedBlocks()) {
            fs.freeBlock(block);
        }

        indexBlocks.remove(fileName);
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
        System.out.println("Indexed Files:");
        for (var entry : indexBlocks.entrySet()) {
            System.out.println("- " + entry.getKey() + " → Index Block: " + entry.getValue());
        }
        fs.showDiskVisual();
    }
}
