$(function () {
    layui.use('table', function () {
        var table = layui.table;

        table.render({
            elem: '#videoaudit',
            url: '/mausoleum/audit/getList',
            where: {type:'3'},
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
                {field: 'auditId', width: '8%', title: '编号', align: 'center'},
                {field: 'fieldOne', width: '18%', title: '视频名称', align: 'center'},
                {field: 'submitUsername', width: '9%', title: '提交人', align: 'center'},
                {field: 'submitTime', width: '18%', title: '提交时间', align: 'center'},
                {field: 'auditUsername', width: '9%', title: '审核人', align: 'center',
                    templet: function (d) {
                        return d.auditUsername == null ? '待审核' : d.auditUsername
                    }
                },
                {field: 'auditTime', width: '18%', title: '审核时间', align: 'center',
                    templet: function (d) {
                        return d.auditTime == null ? '待审核' : d.auditTime
                    }
                },
                {width: '20%', title: '操作', toolbar: '#operatingButton', align: 'center'}
            ]]

        });


        table.on('tool(videoaudit)', function (obj) {
            var data = obj.data;
            if (obj.event === 'look') {
                layer.open({
                    type: 2,
                    title: false,
                    area: ['630px', '360px'],
                    shade: 0.8,
                    closeBtn: 0,
                    shadeClose: true,
                    content: data.fieldTwo
                });
                layer.msg('点击任意处关闭');
            } else if (obj.event === 'agree') {
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
                         fieldTwo : data.fieldTwo
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
                        if (result.code == 0) {
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