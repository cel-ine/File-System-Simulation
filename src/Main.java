
import filesystem.*;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean again = true;
        while (again) {
            System.out.println("\n=== FILE SYSTEM SIMULATION ===");
            System.out.println("Select Allocation Method:");
            System.out.println("1. Contiguous");
            System.out.println("2. Linked");
            System.out.println("3. Indexed");
            System.out.print("Enter choice (1-3): ");
            int choice = scanner.nextInt();

            FileSystem fs = setupFileSystem();
            AllocationMethod method = switch (choice) {
                case 1 -> new ContiguousAllocation(fs);
                case 2 -> new LinkedAllocation(fs);
                case 3 -> new IndexedAllocation(fs);
                default -> null;
            };

            if (method == null) continue;
            runMenu(method);

            System.out.print("\nWould you like to try another allocation method? (y/n): ");
            again = scanner.next().equalsIgnoreCase("y");
        }
        System.out.println("Simulation ended.");
    }

    private static FileSystem setupFileSystem() {
        System.out.print("Enter storage device size (MB): ");
        int sizeMB = scanner.nextInt();
        System.out.print("Enter block size (KB): ");
        int blockKB = scanner.nextInt();

        int totalBlocks = (sizeMB * 1024) / blockKB;
        System.out.printf("Storage: %d MB, Block Size: %d KB → Total Blocks: %d\n", sizeMB, blockKB, totalBlocks);
        return new FileSystem(totalBlocks, blockKB);
    }

    private static void runMenu(AllocationMethod method) {
        boolean running = true;
        while (running) {
            System.out.println("\n---- FILE SYSTEM MENU ----");
            System.out.println("1. Create File");
            System.out.println("2. Delete File");
            System.out.println("3. Show File System Status");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int opt = scanner.nextInt();
            scanner.nextLine();

            switch (opt) {
                case 1 -> {
                    System.out.print("Enter file name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter file size (KB): ");
                    int size = scanner.nextInt();
                    method.createFile(name, size);
                }
                case 2 -> {
                    System.out.print("Enter file name to delete: ");
                    String del = scanner.nextLine();
                    method.deleteFile(del);
                }
                case 3 -> method.showStatus();
                case 4 -> running = false;
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
