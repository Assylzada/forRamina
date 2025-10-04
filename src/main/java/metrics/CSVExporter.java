package metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {

    public static void exportToCsv(PerformanceTracker tracker, String filePath) {
        try {
            File file = new File(filePath);

            System.out.println("🔍 Writing to: " + file.getAbsolutePath());

            boolean newFile = file.createNewFile();

            try (FileWriter writer = new FileWriter(file, true)) {
                if (newFile || file.length() == 0) {
                    writer.write("Comparisons,Swaps,ArrayAccesses,MemoryAllocations\n");
                }
                writer.write(tracker.toCsv() + "\n");
            }

            System.out.println("✅ Metrics exported to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("❌ Error writing CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
