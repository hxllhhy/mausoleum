$(function () {
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#feedbackmanage',
            url: '/mausoleum/feedback/getList',
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
                {field: 'feedbackId', width: '15%', title: '编号', align: 'center'},
                {field: 'username', width: '15%', title: '反馈人', align: 'center'},
                {field: 'time', width: '25%', title: '反馈时间', align: 'center'},
                {field: 'status', width: '20%', title: '状态', align: 'center',
                    templet: function (d) {
                        return d.status == 0 ? '未读' : '已读'
                    }
                },
                {width: '25%', title: '操作', toolbar: '#operatingButton', align: 'center'}
            ]]
        });

        table.on('tool(feedbackmanage)', function (obj) {
            var data = obj.data;
            if (obj.event === 'look') {
                layer.open({
                    type: 1,
                    skin: 'layui-layer-rim',
                    area: ['800px', '350px'],
                    content: data.content,
                    success: function (index) {
                        var url = '/mausoleum/feedback/read';
                        $.post(url, {
                            feedbackId: data.feedbackId
                        }, function (result) {
                            $(".layui-laypage-btn").click();
                        });
                    }
                });

            }
        });
    });
});