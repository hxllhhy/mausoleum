$(function () {
    layui.use(['flow'], function() {
        var flow = layui.flow;

        flow.load({
            elem: '#showpicture' //指定列表容器
            , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/mausoleum/picture/getList?page=' + page + '&limit=12', function (res) {
                    //假设你的列表返回在data集合中
                    layui.each(res.data, function (index, item) {
                        lis.push('<div class="layui-col-md3">' +
                            '<div style="width: 100%; padding-bottom: 75%; position: relative;">' +
                            '<img layer-src="' + item.src + '" src="' + item.src + '"' +
                            'alt="' + item.alt + '" onclick="lookpicture()" style="cursor:pointer; width: 100%; height: 100%; position: absolute;">' +
                            '</div>' +
                            '<div class="my-line-limit-length-head" style="text-align: center; font: 13px sans-serif">' + item.alt +
                            '</div></div>');
                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < Math.ceil(res.count / 12));
                });
            }
        });
    });

});
function lookpicture() {
    layer.photos({
        photos: '#showpicture', shift: 5});
}