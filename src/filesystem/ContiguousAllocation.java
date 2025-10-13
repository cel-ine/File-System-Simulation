package filesystem;

import java.util.*;

/**
 * Implements the Contiguous File Allocation strategy.
 * Finds a sequence of consecutive free blocks to store the file.
 */
public class ContiguousAllocation implements AllocationMethod {

    @Override
    public List<Integer> createFile(String name, int blocksNeeded, boolean[] freeBlocks) {
        int totalBlocks = freeBlocks.length;
        int count = 0, start = -1;

        System.out.println("\nSearching for " + blocksNeeded + " contiguous free blocks...");

        for (int i = 0; i < totalBlocks; i++) {
            if (freeBlocks[i]) {
                if (start == -1) start = i;
                count++;

                if (count == blocksNeeded) {
                    //Found a valid contiguous segment
                    List<Integer> allocated = new ArrayList<>();
                    for (int j = start; j < start + blocksNeeded; j++) {
                        freeBlocks[j] = false;
                        allocated.add(j);
                    }
                    System.out.println("Found space at blocks [" + start + " - " + (start + blocksNeeded - 1) + "]");
                    return allocated;
                }
            } else {
                //Reset when a used block is encountered
                count = 0;
                start = -1;
            }
        }

        System.out.println("Insufficient contiguous space.");
        return null;
    }

    @Override
    public void deleteFile(String name, boolean[] freeBlocks, List<Integer> blocks) {
        if (blocks == null || blocks.isEmpty()) {
            System.out.println("Cannot delete file '" + name + "': no blocks found.");
            return;
        }

        for (int index : blocks) {
            freeBlocks[index] = true;
        }

        System.out.println("Deleted file '" + name + "' and freed blocks [" + blocks.get(0) + " - " + blocks.get(blocks.size() - 1) + "]");
    }

    @Override
    public String getMethodName() {
        return "Contiguous Allocation";
    }
}
