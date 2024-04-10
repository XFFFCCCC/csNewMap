package com.pcl.healthism.web.controller.dto;

public class Response {
    private int code;
    private String message;
    private Object result;

    private Response() {}

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getResult() {
        return result;
    }

    public static Response success(String message, Object data) {
        Response rsp = new Response();
        rsp.code = ResultCode.SUCCESS.code;
        rsp.message = message;
        rsp .result = data;
        return rsp;
    }

    public static Response success(Object data) {
        Response rsp = new Response();
        rsp.code = ResultCode.SUCCESS.code;
        rsp.message = ResultCode.SUCCESS.msg;
        rsp .result = data;
        return rsp;
    }

    public static Response fail(String errorMessage) {
        Response rsp = new Response();
        rsp.code = ResultCode.FAIL.code;
        rsp.message = errorMessage;
        return rsp;
    }

    public enum  ResultCode {
        SUCCESS(0, "success"),
        FAIL(500, "interal server error");

        public int code;
        public String msg;

        ResultCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }
}
