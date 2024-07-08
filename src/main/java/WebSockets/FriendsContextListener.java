package WebSockets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;


public class FriendsContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized");
        ServletContext sc = sce.getServletContext();
        ServerContainer container = (ServerContainer) sc.getAttribute("javax.websocket.server.ServerContainer");
        ServerEndpointConfig cfg = ServerEndpointConfig.Builder
                .create(FriendsRequestWebSocket.class, "/friendRequestsSocket").build();
        try {
            container.addEndpoint(cfg);
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
