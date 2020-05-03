$(function () {
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#coordinateaudit',
            url: '/mausoleum/audit/getList',
            where: {type:'45'},
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
                {field: 'auditId', width: '6%', title: '编号', align: 'center'},
                {field: 'auditType', width: '14%', title: '坐标所属', align: 'center',
                    templet: function (d) {
                        return d.auditType == 4 ? '用户'+d.submitUsername+'自定义' : d.fieldThree
                    }
                },
                {field: 'fieldOne', width: '12%', title: '经度', align: 'center'},
                {field: 'fieldTwo', width: '12%', title: '纬度', align: 'center'},
                {field: 'fieldThree', width: '8%', title: '备注', align: 'center',
                    templet: function (d) {
                        return d.auditType == 4 ? (d.fieldThree == '' ? '无' : d.fieldThree) : '无'
                    }
                },
                {field: 'submitUsername', width: '8%', title: '提交人', align: 'center'},
                {field: 'submitTime', width: '17%', title: '提交时间', align: 'center'},
                {field: 'auditUsername', width: '8%', title: '审核人', align: 'center',
                    templet: function (d) {
                        return d.auditUsername == null ? '待审核' : d.auditUsername
                    }
                },
                {field: 'auditTime', width: '17%', title: '审核时间', align: 'center',
                    templet: function (d) {
                        return d.auditTime == null ? '待审核' : d.auditTime
                    }
                },
                {fixed: 'right', width: '13%', title: '操作', toolbar: '#operatingButton', align: 'center'}
            ]]

        });


        table.on('tool(coordinateaudit)', function (obj) {
            var data = obj.data;
            if (obj.event === 'agree') {
                layer.confirm('确定通过吗？', {
                    icon: 3,
                    title: '提示',
                    closeBtn: 0,
                    btn: ['确定', '取消']
                }, function (index) {
                    var url = '/mausoleum/audit/agree';
                    $.post(url, {
                        auditId : data.auditId,
                        auditType: data.auditType,
                        belongId : data.belongId,
                        fieldOne : data.fieldOne,
                        fieldTwo : data.fieldTwo,
                        fieldThree : data.fieldThree
                    }, function (result) {
                        if (result.code === 0) {
                            layer.close(index);
                            layer.msg('审核已通过', {icon: 6});
                            $(".layui-laypage-btn").click();
                        } else {
                            layer.msg('操作失败，请稍后重试', {icon: 5, anim: 6});
                        }
                    });
                });
            } else if (obj.event === 'reject') {
                layer.confirm('确定拒绝吗？', {
                    icon: 3,
                    title: '提示',
                    closeBtn: 0,
                    btn: ['确定', '取消']
                }, function (index) {
                    var url = '/mausoleum/audit/reject';
                    $.post(url, {
                        auditId : data.auditId
                    }, function (result) {
                        if (result.code === 0) {
                            layer.close(index);
                            layer.msg('审核已拒绝', {icon: 6});
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