
package filesystem;

import java.util.*;

public class ContiguousAllocation implements AllocationMethod {
    private final FileSystem fs;

    public ContiguousAllocation(FileSystem fs) {
        this.fs = fs;
    }

    @Override
    public void createFile(String fileName, int fileSizeKB) {
        int blocksNeeded = (int) Math.ceil((double) fileSizeKB / fs.getBlockSizeKB());
        int startIndex = findContiguousSpace(blocksNeeded);

        if (startIndex == -1) {
            System.out.println("❌ Not enough contiguous space for file: " + fileName);
            return;
        }

        List<Integer> allocated = new ArrayList<>();
        for (int i = startIndex; i < startIndex + blocksNeeded; i++) {
            fs.occupyBlock(i);
            allocated.add(i);
        }

        fs.addFile(new FileSystem.FileEntry(fileName, allocated));
        System.out.printf("✅ File '%s' created successfully (blocks %d–%d).\n",
                fileName, startIndex, startIndex + blocksNeeded - 1);
    }

    private int findContiguousSpace(int needed) {
        int freeCount = 0;
        for (int i = 0; i < fs.getTotalBlocks(); i++) {
            if (fs.isFree(i)) {
                freeCount++;
                if (freeCount == needed) {
                    return i - needed + 1;
                }
            } else {
                freeCount = 0;
            }
        }
        return -1;
    }

    @Override
    public void deleteFile(String fileName) {
        FileSystem.FileEntry file = fs.getFiles().get(fileName);
        if (file == null) {
            System.out.println("❌ File not found: " + fileName);
            return;
        }
        for (int block : file.getAllocatedBlocks()) {
            fs.freeBlock(block);
        }
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
        System.out.println("Files:");
        for (FileSystem.FileEntry f : fs.getFiles().values()) {
            System.out.println("- " + f.getFileName() + " → " + f.getAllocatedBlocks());
        }
        fs.showDiskVisual();
    }
}
