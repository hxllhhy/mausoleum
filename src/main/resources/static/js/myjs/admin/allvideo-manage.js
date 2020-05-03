$(function () {
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#allvideomanage',
            url: '/mausoleum/video/getList',
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
                {field: 'videoId', width: '10%', title: '视频编号', align: 'center'},
                {field: 'alt', width: '25%', title: '视频名称', align: 'center'},
                {field: 'auditUsername', width: '15%', title: '审核人', align: 'center'},
                {field: 'auditTime', width: '25%', title: '审核时间', align: 'center'},
                {width: '25%', title: '操作', toolbar: '#operatingButton', align: 'center'}
            ]]
        });

        table.on('tool(allvideomanage)', function (obj) {
            var data = obj.data;
            if (obj.event === 'look') {
                layer.open({
                    type: 2,
                    title: false,
                    area: ['630px', '360px'],
                    shade: 0.8,
                    closeBtn: 0,
                    shadeClose: true,
                    content: data.src
                });
                layer.msg('点击任意处关闭');
            } else if (obj.event === 'destroy') {
                layer.confirm('确定删除吗？', {
                    icon: 3,
                    title: '提示',
                    closeBtn: 0,
                    btn: ['确定', '取消']
                }, function (index) {
                    var url = '/mausoleum/video/destroy';
                    $.post(url, {
                        videoId : data.videoId
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