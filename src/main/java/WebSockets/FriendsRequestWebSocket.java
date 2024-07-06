package WebSockets;

import javax.servlet.http.HttpSession;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class FriendsRequestWebSocket extends Endpoint {
    public static Session curSession = null;
    private static final Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void sendId(String userId) throws InterruptedException {
        latch.await();
        System.out.println("opened");
        sessions.put(userId, curSession);
        if (curSession == null) System.out.println("not open");
        else System.out.println(curSession.getId() + "cool");
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        System.out.println("socket opened");
        curSession = session;
        if (curSession != null) {
            System.out.println("success");
        }
        latch.countDown();
    }

    public static void sendFriendRequest(String userId) throws IOException {
        System.out.println("sent");
        Session session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText("update");
        }
    }
}
