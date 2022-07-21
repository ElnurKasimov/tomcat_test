package Calc;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/api/calc")
public class CalcServlet extends HttpServlet {
    private CalcService calcService;

    @Override
    public void init() throws ServletException {
        calcService = new CalcService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CalcRequest calcRequest = mapToCalcRequest(req);

        CalcResponse calcResponse = calcService.calculate(calcRequest);

        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(calcResponse));
        resp.getWriter().close();
    }

    private CalcRequest mapToCalcRequest(HttpServletRequest request) {
        String body = null;

        try {
            body = request
                    .getReader()
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Gson().fromJson(body, CalcRequest.class);
    }
}
