package testServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet (value  =  "/hello")
    public class HelloWorldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name =  req.getParameter("name");
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("<h1>Привіт ${name}! </h1>".replace("${name}", parseName(req)));
        resp.getWriter().close();
    }

    private String parseName (HttpServletRequest request) {
        if ( request.getParameterMap().containsKey("name")) {
            return request.getParameter("name");
        }
        return "unnamed";
    }
}
