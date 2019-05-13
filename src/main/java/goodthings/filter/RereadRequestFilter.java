package goodthings.filter;
import goodthings.util.http.WrappedHttpServletRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RereadRequestFilter implements Filter {

    public static final String METHOD_POST = "POST";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest r = (HttpServletRequest) request;
            if (METHOD_POST.equals(r.getMethod())) {
                WrappedHttpServletRequest requestWrapper = new WrappedHttpServletRequest((HttpServletRequest) request);
                chain.doFilter(requestWrapper, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
