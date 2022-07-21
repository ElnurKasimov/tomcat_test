package testServlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;


@WebServlet (value  =  "/hello")
    public class HelloWorldServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name =  req.getParameter("name");
        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write("<h1>Привіт ${name}! </h1>".replace("${name}", parseName(req)));
        resp.getWriter().write("<br>Parametrs</br>");
        resp.getWriter().write(getAllParameters(req));
        resp.getWriter().write("<br>Date and Time</br>");
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("дата:  dd-MM-yyyy,  время: HH:mm:ss"));
        resp.getWriter().write( currentTime);
        resp.getWriter().write("<br>Headers</br>");
        resp.getWriter().write(getAllHeaders(req));
        resp.setHeader("Refresh", "5");
        resp.getWriter().close();
    }

    private String parseName (HttpServletRequest request) {
        if ( request.getParameterMap().containsKey("name")) {
            return request.getParameter("name");
        }
        return "unnamed";
    }

    private String getAllParameters(HttpServletRequest request) {
        String contentType = request.getHeader("content-type");

        if ("application/json".equals(contentType)) {
            return getAllParametersJson(request);
        } else {
            return getAllParametersUrlEncoded(request);
        }
    }

    private String getAllParametersJson(HttpServletRequest request) {
        try {
            String body = request
                    .getReader()
                    .lines()
                    .collect(Collectors.joining("\n"));

            Map<String, String> params = new Gson().fromJson(
                    body,
                    TypeToken.getParameterized(Map.class, String.class, String.class).getType()
            );

            return params
                    .entrySet()
                    .stream()
                    .map(it -> it.getKey() + " = " + it.getValue())
                    .collect(Collectors.joining("<br>"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return "";
    }

    private String getAllParametersUrlEncoded(HttpServletRequest request) {
        StringJoiner result = new StringJoiner("<br>");

        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValues = Arrays.toString(request.getParameterValues(parameterName));

            result.add(parameterName + " = " + parameterValues);
        }

        return result.toString();
    }

    private String getAllHeaders(HttpServletRequest request) {
        StringJoiner result = new StringJoiner("<br>");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements())  {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            result.add(headerName + " = " + headerValue);
        }
        return result.toString();
    }

}
