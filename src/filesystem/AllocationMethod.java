
package filesystem;

public interface AllocationMethod {
    void createFile(String fileName, int fileSizeKB);
    void deleteFile(String fileName);
    void showStatus();
}
