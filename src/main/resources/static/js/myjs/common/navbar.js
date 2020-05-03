$(function () {
    layui.use(['element', 'layer'], function () {
        var element = layui.element, layer = layui.layer;
    });

    var url = '/mausoleum/user/getSession';
    var loginUserName;
    $.getJSON(url, function (result) {
        if (result.code == 0) {
            loginUserName = result.data.username;
            loginUserRole = result.data.role;
            $("#login0").hide();
            $("#login1").show();
            $("#me").append(loginUserName);
            if (loginUserRole == 0) {
                $("#manage").show();
            }
            $('#modifyminfo').show();
            $("#video").show();
            $("#coordinate").show();
            $("#user").val(loginUserName);
        } else {
            $("#login1").hide();
            $("#login0").show();
            $("#manage").hide();
            $('#modifyminfo').hide();
            $("#video").hide();
            $("#coordinate").hide();
        }
    });
});

function deleteSession() {
    layer.confirm('确定退出吗？', {
            icon: 3,
            title: '提示',
            closeBtn: 0,
            btn: ['确定', '取消'],
            btn2: function () {
                $("#deleteSession").removeAttr("class");
            }
        }, function (index) {
            layer.close(index);
            var url = '/mausoleum/user/logout';
            $.get(url);
            window.location.href = "/mausoleum/html/login.html";
        }
    );
}

function giveFeedback() {
    layer.prompt({
            formType: 2,
            title: '意见反馈',
            value: '写下你的意见和反馈，字数不得超过1000字',
            area: ['800px', '350px'],
            closeBtn: 0,
            btn: ['提交', '取消'],
            btn2: function () {
                $('#giveFeedback').removeAttr('class');
            }
        }, function (value, index) {
            var content = $.trim(value);
            if (content == "" || content.length == 0) {
                layer.msg('输入不能为空', {icon: 5, anim: 6});
            }
            var url = '/mausoleum/feedback/save';
            $.post(url, {
                content: content
            }, function (result) {
                if (result.code == 0) {
                    layer.close(index);
                    layer.msg('提交成功', {icon: 6});
                } else {
                    layer.msg(result.msg, {icon: 5, anim: 6});
                }
            });
            $('#giveFeedback').removeAttr('class');
        }
    );
}

function getQueryString(queryName) {
    var reg = new RegExp("(^|&)" + queryName + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if ( r != null ){
        return decodeURI(r[2]);
    }else{
        return null;
    }
}

function modifyPassword() {
    if($('#user').val() != "") {
        //页面层
        layer.open({
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['440px', '340px'], //宽高
            content: '<div class="layui-form layui-form-pane" style="padding: 20px 0 0 20px">' +
            '                            <form method="post">' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">邮箱</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="text" name="email_forget" id="email_forget" required lay-verify="required|email"' +
            '                                               lay-reqtext="邮箱是必填项，不能为空" placeholder="请输入注册邮箱" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">设置新密码</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="password" name="password_forget" id="password_forget" required lay-verify="required|password_forget"' +
            '                                               lay-reqtext="密码是必填项，不能为空" placeholder="请输入6-20位字母和数字" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">确认密码</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="password" name="confirmPassword_forget" id="confirmPassword_forget" required lay-verify="required"' +
            '                                               lay-reqtext="确认密码是必填项，不能为空" placeholder="再次输入新密码" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">验证码</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="text" name="identifyCode_forget" id="identifyCode_forget" lay-verify="required"' +
            '                                               lay-reqtext="验证码是必填项，不能为空" placeholder="请输入验证码" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                    <div class="layui-form-mid" style="padding: 0!important;">' +
            '                                        <img id="captchaImage_forget" src="/mausoleum/user/changeCode" style="height: 38px;width: 85px;"/>' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <button class="layui-btn" lay-submit lay-filter="forget_submit">立即提交</button>' +
            '                                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>' +
            '                                </div>' +
            '                            </form>' +
            '                        </div>',
            cancel: function () {
                $('#modifyPassword').removeAttr('class');
            }
        });
    } else {
        //页面层
        layer.open({
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['440px', '390px'], //宽高
            content: '<div class="layui-form layui-form-pane" style="padding: 20px 0 0 20px">' +
            '                            <form method="post">' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">用户名</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="text" name="username_forget" id="username_forget" required lay-verify="required|username_forget"' +
            '                                               lay-reqtext="用户名是必填项，不能为空" placeholder="请输入4-12位字母和数字" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">邮箱</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="text" name="email_forget" id="email_forget" required lay-verify="required|email"' +
            '                                               lay-reqtext="邮箱是必填项，不能为空" placeholder="请输入注册邮箱" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">设置新密码</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="password" name="password_forget" id="password_forget" required lay-verify="required|password_forget"' +
            '                                               lay-reqtext="密码是必填项，不能为空" placeholder="请输入6-20位字母和数字" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">确认密码</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="password" name="confirmPassword_forget" id="confirmPassword_forget" required lay-verify="required"' +
            '                                               lay-reqtext="确认密码是必填项，不能为空" placeholder="再次输入新密码" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <label class="layui-form-label">验证码</label>' +
            '                                    <div class="layui-input-inline">' +
            '                                        <input type="text" name="identifyCode_forget" id="identifyCode_forget" lay-verify="required"' +
            '                                               lay-reqtext="验证码是必填项，不能为空" placeholder="请输入验证码" autocomplete="off"' +
            '                                               class="layui-input">' +
            '                                    </div>' +
            '                                    <div class="layui-form-mid" style="padding: 0!important;">' +
            '                                        <img id="captchaImage_forget" src="/mausoleum/user/changeCode" style="height: 38px;width: 85px;"/>' +
            '                                    </div>' +
            '                                </div>' +
            '                                <div class="layui-form-item">' +
            '                                    <button class="layui-btn" lay-submit lay-filter="forget_submit">立即提交</button>' +
            '                                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>' +
            '                                </div>' +
            '                            </form>' +
            '                        </div>'
        });
    }
    $("#captchaImage_forget").attr("src", "/mausoleum/user/changeCode?timestamp=" + (new Date()).valueOf());
    $('#captchaImage_forget').click(function () {
        $("#captchaImage_forget").attr("src", "/mausoleum/user/changeCode?timestamp=" + (new Date()).valueOf());
    });
    layui.use('form', function () {
        var form = layui.form, layer = layui.layer;
        form.verify({
            username_forget: [
                /^[a-zA-Z0-9_]{4,12}$/,
                '用户名必须由【4到12位】的【字母或数字】组成，不能出现空格'
            ],
            password_forget: [
                /^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}$/,
                '密码必须由【6到20位】的【字母和数字】组成，不能出现空格'
            ]
        });

        //监听提交
        form.on('submit(forget_submit)', function (data) {
            if (data.field.password_forget != data.field.confirmPassword_forget) {
                layer.msg('请再次确认密码是否一致', {icon: 5, anim: 6});
                return false;
            }
            var url = '/mausoleum/user/modify';
            $.post(url, {
                username: data.field.username_forget,
                email: data.field.email_forget,
                password: data.field.password_forget,
                identifyCode: data.field.identifyCode_forget
            }, function (result) {
                if (result.code == 0) {
                    layer.closeAll();
                    if ($('#user').val() != "") {
                        layer.confirm('修改成功，需要重新登录', {
                                icon: 6,
                                title: '提示',
                                closeBtn: 0,
                                btn: ['好的']
                            }, function (index) {
                                layer.close(index);
                                var url = '/mausoleum/user/logout';
                                $.get(url);
                                window.location.href = "/mausoleum/html/login.html";
                            }
                        );
                    } else {
                        layer.msg('修改成功', {icon: 6});
                    }
                } else {
                    layer.msg(result.msg, {icon: 5, anim: 6});
                }
                $("#captchaImage_forget").attr("src", "/mausoleum/user/changeCode?timestamp=" + (new Date()).valueOf());
            });
            return false;
        });
    });
}