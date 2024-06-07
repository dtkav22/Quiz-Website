package DataBaseConnectionPool;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

public class TestDataBaseConnectionPool extends TestCase{
    private DataBaseConnectionPool pool;

    public void setUp() {
        pool = DataBaseConnectionPool.getInstance();
    }

    public void testConnection() throws InterruptedException {
        int numberOfClients = 10 * pool.getPoolSize() + 1;
        CountDownLatch countDownLatch = new CountDownLatch(numberOfClients);
        for(int i = 0; i < numberOfClients; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Connection c = pool.getConnection();
                    for(int i = 0; i < 10; i++) {
                        i++;
                    }
                    for(int i = 0; i < 10; i++) {
                        pool.closeConnection(c);
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
    }
}
