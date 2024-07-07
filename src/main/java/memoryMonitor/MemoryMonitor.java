package memoryMonitor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class MemoryMonitor {
    private static final Logger LOGGER = Logger.getLogger(MemoryMonitor.class.getName());
    private final Timer timer = new Timer(true); // Daemon thread

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Runtime runtime = Runtime.getRuntime();
                long maxMemory = runtime.maxMemory();
                long allocatedMemory = runtime.totalMemory();
                long freeMemory = runtime.freeMemory();
                long usedMemory = allocatedMemory - freeMemory;
                long availableMemory = maxMemory - usedMemory;

                LOGGER.info(String.format("Memory usage - Max: %d, Allocated: %d, Used: %d, Free: %d, Available: %d",
                        maxMemory, allocatedMemory, usedMemory, freeMemory, availableMemory));
            }
        }, 0, 10000); // Log every 10 seconds
    }



}