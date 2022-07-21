package Calc;

import lombok.Data;

@Data
public class CalcResponse {
    private CalcRequest request;
    private int result;
}
