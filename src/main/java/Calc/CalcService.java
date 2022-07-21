package Calc;

public class CalcService {

    public CalcResponse calculate(CalcRequest request) {
        int result = 0;
        switch (request.getOperation()) {
            case "+":
                result = request.getParam1() + request.getParam2();
                break;

            case "-":
                result = request.getParam1() - request.getParam2();
                break;

            case "/":
                result = request.getParam1() / request.getParam2();
                break;

            case "*":
                result = request.getParam1() * request.getParam2();
                break;
        }

        CalcResponse response = new CalcResponse();
        response.setRequest(request);
        response.setResult(result);
        return response;
    }
}
