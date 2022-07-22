package Auth;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(value = "/api/*")
public class AuthFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse res,
                            FilterChain chain) throws IOException, ServletException {

        String authHeaderValue = req.getHeader("Authorization");
        if ("111".equals(authHeaderValue)) {
            chain.doFilter(req, res);
        } else {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"Error\": \"Not authorized\"}");
            res.getWriter().close();
        }
    }
}
