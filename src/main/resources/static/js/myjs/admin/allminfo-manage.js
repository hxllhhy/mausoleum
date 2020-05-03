$(function () {
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#allminfomanage',
            url: '/mausoleum/minfo/getList',
            where: {isHtml : true},
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
                {field: 'mInfoId', width: '10%', title: '陵墓编号', align: 'center'},
                {field: 'name', width: '25%', title: '陵墓名称', align: 'center'},
                {field: 'lastAuditUsername', width: '15%', title: '最终审核人', align: 'center'},
                {field: 'lastAuditTime', width: '25%', title: '最终审核时间', align: 'center'},
                {width: '25%', title: '操作', toolbar: '#operatingButton', align: 'center'}
            ]]
        });

        table.on('tool(allminfomanage)', function (obj) {
            var data = obj.data;
            if (obj.event === 'look') {
                window.location.href = '/mausoleum/html/minfo.html?mInfoId=' + data.mInfoId;
            } else if (obj.event === 'modify') {
                window.location.href = '/mausoleum/html/minfo-modify.html?mInfoId=' + data.mInfoId;
            } else if (obj.event === 'destroy') {
                layer.confirm('确定删除吗？', {
                    icon: 3,
                    title: '提示',
                    closeBtn: 0,
                    btn: ['确定', '取消']
                }, function (index) {
                    var url = '/mausoleum/minfo/destroy';
                    $.post(url, {
                        mInfoId : data.mInfoId
                    }, function (result) {
                        if (result.code == 0) {
                            layer.close(index);
                            layer.msg('删除成功', {icon: 6});
                            $(".layui-laypage-btn").click();
                        } else {
                            layer.msg('操作失败，请稍后重试', {icon: 5, anim: 6});
                        }
                    });
                });
            }
        });
    });
});