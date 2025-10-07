// What must exist in allocation algorithm
public interface AllocationMethod {
    void createFile(String name, int size, boolean[] freeBlocks);
    void deleteFile(String name, boolean[] freeBlocks);
    void showStatus();
}
