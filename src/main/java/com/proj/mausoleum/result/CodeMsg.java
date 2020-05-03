package com.proj.mausoleum.result;

public class CodeMsg {
    private int code;
    private String msg;

    // 通用
    public static final CodeMsg SERVER_ERROR = new CodeMsg(50100, "服务器异常");
    public static final CodeMsg PARAMETER_NULL = new CodeMsg(50101, "参数为空");

    // 用户
    public static final CodeMsg UNPW_ERROR = new CodeMsg(50201, "请检查用户名和密码，再次尝试登录哦");
    public static final CodeMsg UNEM_ERROR = new CodeMsg(50202, "请检查用户名和邮箱，再次尝试登录哦");
    public static final CodeMsg SESSION_NOTEXISTS = new CodeMsg(50203, "账号异常");
    public static final CodeMsg CODE_ERROR = new CodeMsg(50204, "验证码错误");
    public static final CodeMsg USERNAME_EXISTS = new CodeMsg(50205, "用户名已存在");
    public static final CodeMsg REGISTER_FAIL = new CodeMsg(50206, "注册失败");

    // 反馈
    public static final CodeMsg FBSAVE_FAIL = new CodeMsg(50301, "您的反馈提交失败，请稍后再试");
    public static final CodeMsg FBREAD_FAIL = new CodeMsg(50302, "反馈浏览失败");

    // 图片
    public static final CodeMsg PICSUFFIX_ERROR = new CodeMsg(50401, "图片格式不允许");
    public static final CodeMsg PICSAVE_FAIL = new CodeMsg(50402, "图片保存失败");
    public static final CodeMsg PICMODIFY_FAIL = new CodeMsg(50403, "图片修改失败");
    public static final CodeMsg PICDESTROY_FAIL = new CodeMsg(50404, "图片删除失败");

    // 陵墓信息
    public static final CodeMsg MINFOSAVE_FAIL = new CodeMsg(50501, "陵墓信息保存失败");
    public static final CodeMsg MINFO_NOTEXISTS = new CodeMsg(50502, "陵墓信息不存在");
    public static final CodeMsg MINFOMODIFY_FAIL = new CodeMsg(50503, "陵墓信息修改失败");
    public static final CodeMsg MINFODESTROY_FAIL = new CodeMsg(50504, "陵墓信息删除失败");
    public static final CodeMsg AUDITSAVE_FAIL = new CodeMsg(50505, "审核陵墓信息保存失败");
    public static final CodeMsg AUDITMODIFY_FAIL = new CodeMsg(50506, "审核陵墓信息修改失败");
    public static final CodeMsg AUDITDESTROY_FAIL = new CodeMsg(50507, "审核陵墓信息删除失败");

    // 视频
    public static final CodeMsg VIDEOSAVE_FAIL = new CodeMsg(50601, "视频保存失败");
    public static final CodeMsg VIDEODESTROY_FAIL = new CodeMsg(50602, "视频删除失败");
    public static final CodeMsg VIDEOSUFFIX_ERROR = new CodeMsg(50603, "视频格式不允许");

    // 坐标
    public static final CodeMsg COORDSAVE_FAIL = new CodeMsg(50701, "视频保存失败");
    public static final CodeMsg COORD_NOTEXISTS = new CodeMsg(50702, "陵墓信息不存在");
    public static final CodeMsg COORDDESTROY_FAIL = new CodeMsg(50703, "视频删除失败");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
