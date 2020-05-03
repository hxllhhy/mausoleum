$(function () {
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#usermanage',
            url: '/mausoleum/user/getList',
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                };
            },
            page: true,
            cols: [[
                {field: 'userId', width: '18%', title: '用户编号', align: 'center'},
                {field: 'username', width: '18%', title: '用户名', align: 'center'},
                {field: 'password', width: '18%', title: '用户密码', align: 'center'},
                {field: 'email', width: '18%', title: '用户邮箱', align: 'center'},
                {field: 'role', width: '18%', title: '用户类型', align: 'center',
                    templet: function (d) {
                        return d.role == 0 ? '管理员' : '普通用户'
                    }},
                {width: '10%', title: '操作', toolbar: '#operatingButton', align: 'center'}
            ]]
        });

        table.on('tool(usermanage)', function (obj) {
            var data = obj.data;
            if (obj.event === 'destroy') {
                layer.confirm('确定删除吗？', {
                    icon: 3,
                    title: '提示',
                    closeBtn: 0,
                    btn: ['确定', '取消']
                }, function (index) {
                    var url = '/mausoleum/user/destroy';
                    $.post(url, {
                        userId : data.userId
                    }, function (result) {
                        if (result.code == 0) {
                            layer.close(index);
                            layer.msg('删除成功', {icon: 6});
                            $(".layui-laypage-btn").click();
                        } else {
                            layer.msg('删除失败，请稍后重试', {icon: 5, anim: 6});
                        }
                    });
                });
            }
        });
    });
});
function addAdmin() {
    //页面层
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['430px', '390px'], //宽高
        content: '<div class="layui-form layui-form-pane" style="padding: 20px 0 0 20px">' +
        '                            <form method="post">' +
        '                                <div class="layui-form-item">' +
        '                                    <label class="layui-form-label">用户名</label>' +
        '                                    <div class="layui-input-inline">' +
        '                                        <input type="text" name="username" id="username" required lay-verify="required|username"' +
        '                                               lay-reqtext="用户名是必填项，不能为空" placeholder="请输入4-12位字母或数字" autocomplete="off"' +
        '                                               class="layui-input">' +
        '                                    </div>' +
        '                                </div>' +
        '                                <div class="layui-form-item">' +
        '                                    <label class="layui-form-label">密码</label>' +
        '                                    <div class="layui-input-inline">' +
        '                                        <input type="password" name="password" id="password" required lay-verify="required|password"' +
        '                                               lay-reqtext="密码是必填项，不能为空" placeholder="请输入6-20位字母和数字" autocomplete="off"' +
        '                                               class="layui-input">' +
        '                                    </div>' +
        '                                </div>' +
        '                                <div class="layui-form-item">' +
        '                                    <label class="layui-form-label">确认密码</label>' +
        '                                    <div class="layui-input-inline">' +
        '                                        <input type="password" name="confirmPassword" id="confirmPassword" required lay-verify="required"' +
        '                                               lay-reqtext="确认密码是必填项，不能为空" placeholder="请输入6-20位字母和数字" autocomplete="off"' +
        '                                               class="layui-input">' +
        '                                    </div>' +
        '                                </div>' +
        '                                <div class="layui-form-item">' +
        '                                    <label class="layui-form-label">邮箱</label>' +
        '                                    <div class="layui-input-inline">' +
        '                                        <input type="text" name="email" id="email" required lay-verify="required|email"' +
        '                                               lay-reqtext="邮箱是必填项，不能为空" placeholder="用于找回密码，请谨慎填写" autocomplete="off"' +
        '                                               class="layui-input">' +
        '                                    </div>' +
        '                                </div>' +
        '                                <div class="layui-form-item">' +
        '                                    <label class="layui-form-label">验证码</label>' +
        '                                    <div class="layui-input-inline">' +
        '                                        <input type="text" name="identifyCode" id="identifyCode" lay-verify="required"' +
        '                                               lay-reqtext="验证码是必填项，不能为空" placeholder="输入验证码" autocomplete="off"' +
        '                                               class="layui-input">' +
        '                                    </div>' +
        '                                    <div class="layui-form-mid" style="padding: 0!important;">' +
        '                                        <img id="captchaImage" src="/mausoleum/user/changeCode" style="height: 38px;width: 85px;"/>' +
        '                                    </div>' +
        '                                </div>' +
        '                                <div class="layui-form-item">' +
        '                                    <button class="layui-btn" lay-submit lay-filter="addAdmin_submit">立即提交</button>' +
        '                                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>' +
        '                                </div>' +
        '                            </form>' +
        '                        </div>'
    });
    $("#captchaImage").attr("src", "/mausoleum/user/changeCode?timestamp=" + (new Date()).valueOf());
    $('#captchaImage').click(function () {
        $("#captchaImage").attr("src", "/mausoleum/user/changeCode?timestamp=" + (new Date()).valueOf());
    });
    layui.use('form', function () {
        var form = layui.form, layer = layui.layer;
        form.verify({
            username: [
                /^[a-zA-Z0-9_]{4,12}$/,
                '用户名必须由【4到12位】的【字母或数字】组成，不能出现空格'
            ],
            password: [
                /^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$/,
                '密码必须由【6到20位】的【字母和数字】组成，不能出现空格'
            ]
        });

        //监听提交
        form.on('submit(addAdmin_submit)', function (data) {
            if (data.field.password != data.field.confirmPassword) {
                layer.msg('请再次确认密码是否一致', {icon: 5, anim: 6});
                return false;
            }
            var url = '/mausoleum/user/register';
            $.post(url, {
                username: data.field.username,
                email: data.field.email,
                password: data.field.password,
                identifyCode: data.field.identifyCode,
                role: 0
            }, function (result) {
                if (result.code == 0) {
                    layer.msg('添加成功', {icon: 6});
                } else {
                    layer.msg(result.msg, {icon: 5, anim: 6});
                }
                $("#captchaImage").attr("src", "/mausoleum/user/changeCode?timestamp=" + (new Date()).valueOf());
            });
            return false;
        });
    });
}