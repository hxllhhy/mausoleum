$(function () {
    layui.use(['flow'], function() {
        var flow = layui.flow;

        flow.load({
            elem: '#showvideo' //指定列表容器
            , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/mausoleum/video/getList?page=' + page + '&limit=4', function (res) {
                    //假设你的列表返回在data集合中
                    layui.each(res.data, function (index, item) {
                        lis.push('<div class="layui-col-md6">' +
                            '<div style="width: 100%; padding-bottom: 60%; position: relative; background-color: #0C0C0C">' +
                            '<video src="' + item.src + '" controls="controls" style="width: 100%; height: 100%; position: absolute"></video>' +
                            '</div>'+
                            '<div class="my-line-limit-length-head" style="text-align: center; font: 13px sans-serif">' + item.alt +
                            '</div></div>');
                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < Math.ceil(res.count / 4));
                });
            }
        });
    });

});
function uploadvideo() {
    //页面层
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['380px', '300px'], //宽高
        content: '<div class="layui-form layui-form-pane" style="padding: 20px;"> ' +
            '<div class="layui-form-item"> ' +
                '<button type="button" class="layui-btn layui-btn-normal" id="test8">选择视频</button> ' +
            '</div> ' +
            '<div class="layui-form-item">' +
                '<button type="button" class="layui-btn" id="test9">上传视频</button> ' +
        '<div class="layui-progress layui-progress" lay-filter="demo" lay-showPercent="true"> ' +
                '<div class="layui-progress-bar layui-bg-orange" lay-percent="0%"></div>' +
            '</div>' +
        '</div>' +
        '<form method="post"> ' +
            '<div class="layui-form-item"> ' +
                '<label class="layui-form-label">视频名称</label> ' +
                '<div class="layui-input-block"> ' +
                    '<input type="text" name="alt" required lay-verify="required|alt" lay-reqtext="视频名称必填项，不能为空" placeholder="不能超过20字" autocomplete="off" class="layui-input">' +
                 '</div> ' +
            '</div> ' +
            '<div class="layui-form-item"> ' +
                '<button class="layui-btn layui-btn-fluid" lay-submit lay-filter="uploadvideo_submit">立即提交</button> ' +
            '</div>' +
        '</form> ' +

        '</div>'
    });

    var flag = 0;
    var src;

    layui.use(['upload','element'], function () {
        var upload = layui.upload;
        var element = layui.element;
        element.render('progress');
        upload.render({
            elem: '#test8'
            , url: '/mausoleum/video/save' //改成您自己的上传接口
            , accept: 'video'
            , acceptMime: 'video/mp4'
            , exts: 'mp4'
            , auto: false
            , bindAction: '#test9'
            , size: 102400
            , progress: function(n, elem) {
                var percent = n + '%'; //获取进度百分比
                element.progress('demo', percent); //可配合 layui 进度条元素使用
            }
            ,done: function (res) {
                if(res.code === 0) {
                    src = res.data;
                    flag = 1;
                } else {
                    layer.msg(res.msg, {icon: 5, anim: 6});
                }
            }, error: function () {
                layer.msg('上传失败，请稍后重试', {icon: 5, anim: 6});
            }
        });
    });


    layui.use('form', function () {
        var form = layui.form, layer = layui.layer;
        form.verify({
            alt: [
                /^[\S]{1,20}$/,
                '视频名称不能超过20字，不能出现空格'
            ]
        });

        //监听提交
        form.on('submit(uploadvideo_submit)', function (data) {
            if (flag == 1) {
                var url = '/mausoleum/audit/save';
                $.post(url, {
                    auditType: 3,
                    belongId: 0,
                    fieldOne: data.field.alt,
                    fieldTwo: src
                }, function (result) {
                    if (result.code === 0) {
                        layer.closeAll();
                        layer.msg('提交成功，请耐心等待审核', {icon: 6});
                    } else {
                        layer.msg(result.msg, {icon: 5, anim: 6});
                    }
                });
            } else {
                layer.msg('还没有上传视频哦', {icon: 5, anim: 6});
            }
            return false;
        });
    });
}