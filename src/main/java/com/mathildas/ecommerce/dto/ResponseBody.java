package com.mathildas.ecommerce.dto;

import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

@Data
@Builder
public class ResponseBody<T> {
    private String message;
    private T data;
    private String error;
    private int statusCode;

}
