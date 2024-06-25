package DataBaseConnectionPool;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataBaseConnectionPool {
    private final int numberOfConnections;
    private static DataBaseConnectionPool instance;

    private final BlockingQueue<Connection> blockingQueue;

    private DataBaseConnectionPool(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
        blockingQueue = new LinkedBlockingQueue<>(numberOfConnections);
    }

    public int getPoolSize() {
        return numberOfConnections;
    }

    public static DataBaseConnectionPool getInstance() {
        if(instance == null) {
            instance = new DataBaseConnectionPool(100);
            instance.createPool();
        }
        return instance;
    }

    private void createPool() {
        for(int i = 0; i < numberOfConnections; i++) {
            try {
                blockingQueue.put(new DataBaseConnector().getDataBaseConnection());
            } catch (InterruptedException e) {
                System.err.println("Error creating connection pool");
            }
        }
    }

    public Connection getConnection() {
        Connection res = null;
        try {
            res = blockingQueue.take();
        } catch (InterruptedException e) {
            System.err.println("Error getting Connection from pool");
        }
        return res;
    }

    public void closeConnection(Connection c) {
        try {
            if(blockingQueue.contains(c)) return;
            blockingQueue.put(c);
        } catch (InterruptedException e) {
            System.err.println("Error closing Connection");
        }
    }
}
