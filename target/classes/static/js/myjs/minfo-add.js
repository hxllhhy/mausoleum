$(function () {
    var details = new Simditor({
        textarea: $('#details'),  //textarea的id
        toolbar : [ 'title', 'bold', 'italic', 'underline', 'strikethrough', 'color', '|', 'ol', 'ul', 'blockquote', 'table', '|', 'image', 'hr', '|', 'indent', 'outdent', 'alignment'],
        toolbarFloat: false,
        defaultImage : './simditor/images/image.png', //编辑器插入图片时使用的默认图片
        upload : {
            url : '/mausoleum/picture/save', //文件上传的接口地址
            params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
            fileKey: 'file', //服务器端获取文件数据的参数名
            connectionCount: 3,
            leaveConfirm: '正在上传图片...你确定要离开这个页面吗？'
        }
    });
    layui.use('form', function () {
        var form = layui.form, layer = layui.layer;
        form.verify({
            name: [
                /^[\S]{1,20}$/,
                '陵墓名称不能超过20字，不能出现空格'
            ],
            details: [
                /^.{100,}$/,
                '陵墓详情不少于100字'
            ]
        });

        //监听提交
        form.on('submit(minfoadd_submit)', function (data) {
            var url = '/mausoleum/audit/save';
            $.post(url, {
                auditType: 1,
                belongId: 0,
                fieldOne: data.field.name,
                fieldTwo: details.getValue()
            }, function (result) {
                if (result.code === 0) {
                    layer.msg('提交成功，请耐心等待审核', {icon: 6});
                    $('#name').val('');
                    details.setValue('');
                } else {
                    layer.msg('提交失败，请稍后重试', {icon: 5, anim: 6});
                }
            });
            return false;
        });
    });
});