$(function () {
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#allcoordinatemanage',
            url: '/mausoleum/coordinate/getList',
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
                {field: 'coordinateId', width: '9%', title: '坐标编号', align: 'center'},
                {field: 'coordinateType', width: '15%', title: '坐标所属', align: 'center',
                    templet: function (d) {
                        return d.coordinateType == 4 ? '用户自定义' : d.remark
                    }
                },
                {field: 'x', width: '15%', title: '经度', align: 'center'},
                {field: 'y', width: '15%', title: '纬度', align: 'center'},
                {field: 'remark', width: '10%', title: '备注', align: 'center',
                    templet: function (d) {
                        return d.coordinateType == 4 ? (d.remark == '' ? '无' : d.remark) : '无'
                    }
                },
                {field: 'auditUsername', width: '9%', title: '审核人', align: 'center'},
                {field: 'auditTime', width: '17%', title: '审核时间', align: 'center'},
                {width: '10%', title: '操作', toolbar: '#operatingButton', align: 'center'}
            ]]
        });

        table.on('tool(allcoordinatemanage)', function (obj) {
            var data = obj.data;
            if (obj.event === 'destroy') {
                layer.confirm('确定删除吗？', {
                    icon: 3,
                    title: '提示',
                    closeBtn: 0,
                    btn: ['确定', '取消']
                }, function (index) {
                    var url = '/mausoleum/coordinate/destroy';
                    $.post(url, {
                        coordinateId : data.coordinateId
                    }, function (result) {
                        if (result.code === 0) {
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