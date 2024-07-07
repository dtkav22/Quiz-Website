package memoryMonitor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    private MemoryMonitor memoryMonitor;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        memoryMonitor = new MemoryMonitor();
        memoryMonitor.start();
        System.out.println("Memory monitor started");
        printCurrentMemoryUsage();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application is stopping, memory monitor will be shutdown");
        printCurrentMemoryUsage();
    }

    private void printCurrentMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = allocatedMemory - freeMemory;
        long availableMemory = maxMemory - usedMemory;

        System.out.println(String.format("Current memory usage - Max: %d, Allocated: %d, Used: %d, Free: %d, Available: %d",
                maxMemory, allocatedMemory, usedMemory, freeMemory, availableMemory));
    }
}