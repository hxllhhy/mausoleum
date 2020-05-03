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

    var mInfoId = getQueryString('mInfoId');

    $.getJSON('/mausoleum/minfo/get',{
        mInfoId: mInfoId,
        isHtml: true
    }, function (result) {
        if (result.code === 0) {
            var mInfo = result.data;
            $('#name').html(mInfo.name);
            details.setValue(mInfo.details);
        }
    });

    layui.use('form', function () {
        var form = layui.form, layer = layui.layer;
        //监听提交
        form.on('submit(minfomodify_submit)', function (data) {
            $.post('/mausoleum/audit/save', {
                auditType: 2,
                belongId: mInfoId,
                fieldOne: $('#name').text(),
                fieldTwo: details.getValue()
            }, function (result) {
                if (result.code === 0) {
                    layer.msg('提交成功，请耐心等待审核', {icon: 6});
                    window.location.href = '/mausoleum/html/minfo.html?mInfoId=' + mInfoId;
                } else {
                    layer.msg('提交失败，请稍后重试', {icon: 5, anim: 6});
                }
            });
            return false;
        });
    });
});