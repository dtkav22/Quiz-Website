package Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CacheControlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
