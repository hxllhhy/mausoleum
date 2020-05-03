package com.proj.mausoleum.result;

public class Result {
    private int code;
    private String msg;
    private Object data;
    private int count;

    private Result(Object data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();

    }

    private Result(Object data, int count) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
        this.count = count;
    }

    // 成功的时候调用
    public static Result success(Object data) {
        return new Result(data);
    }

    // 失败的时候调用
    public static Result error(CodeMsg codeMsg) {
        return new Result(codeMsg);
    }

    // 分页的时候调用
    public static Result page(Object data, int count) {
        return new Result(data, count);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
