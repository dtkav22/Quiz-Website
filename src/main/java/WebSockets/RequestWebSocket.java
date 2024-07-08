package WebSockets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/your-websocket-endpoint", configurator = HttpSessionConfigurator.class)
public class RequestWebSocket extends Endpoint {
    private static final Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<>());
    private static final Gson gson = new Gson();

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("WebSocket opened: " + session.getId() + " Map Size: " + sessions.size());
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        if (httpSession != null) {
            String userId = (String) httpSession.getAttribute("userId");
            if (userId != null) {
                if (sessions.containsKey(userId)) {
                    try {
                        sessions.get(userId).close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "New session opened"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                sessions.put(userId, session);
                System.out.println("Associated user " + userId + " with session " + session.getId());
            }
        }
    }

    @Override
    public void onClose(Session session, CloseReason reason) {
        System.out.println("WebSocket closed: " + session.getId() + ". Reason: " + reason);
        removeSession(session);
    }

    @Override
    public void onError(Session session, Throwable thr) {
        System.err.println("WebSocket error on session " + session.getId() + ": " + thr.getMessage());
        thr.printStackTrace();
        removeSession(session);
    }

    private void removeSession(Session session) {
        sessions.values().remove(session);
    }

    public static void sendFriendRequest(String friendId, String myId) throws IOException {
        Session session = sessions.get(friendId);
        if (session != null && session.isOpen()) {
            JsonObject message = new JsonObject();
            message.addProperty("type", "friendRequest");
            message.addProperty("from", myId);
            session.getBasicRemote().sendText(gson.toJson(message));
        } else {
            System.out.println("Session is not open for user: " + friendId);
        }
    }

    public static void sendChallengeRequest(String friendId) throws IOException {
        Session session = sessions.get(friendId);
        if (session != null && session.isOpen()) {
            JsonObject message = new JsonObject();
            message.addProperty("type", "challengeRequest");
            session.getBasicRemote().sendText(gson.toJson(message));
        } else {
            System.out.println("Session is not open for user: " + friendId);
        }
    }
}
