package com.proj.mausoleum.result;


public class PicResult {
    private Boolean success;
    private String msg;
    private String file_path;

    private PicResult(String file_path) {
        this.success = true;
        this.file_path = file_path;
    }

    private PicResult(CodeMsg codeMsg) {
        this.success = false;
        this.msg = codeMsg.getMsg();
    }

    // 成功的时候调用
    public static PicResult success(String file_path) {
        return new PicResult(file_path);
    }

    // 失败的时候调用
    public static PicResult error(CodeMsg codeMsg) {
        return new PicResult(codeMsg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
