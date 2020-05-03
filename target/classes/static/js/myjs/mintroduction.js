$(function () {

    layui.use(['flow','form'], function() {
        var flow = layui.flow;

        flow.load({
            elem: '#introCard' //指定列表容器
            , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                var lis = [];
                //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                $.get('/mausoleum/minfo/getList?page=' + page + '&limit=9', {isHtml : false}, function (res) {
                    //假设你的列表返回在data集合中
                    layui.each(res.data, function (index, item) {
                        lis.push('<div class="layui-col-md4">' +
                            '<div class="layui-card" onclick="window.location.href=\'/mausoleum/html/minfo.html?mInfoId=' + item.mInfoId + '\'" style="cursor: pointer">' +
                            '<div class="layui-card-header my-line-limit-length-head">' + item.name + '</div>' +
                            '<div class="layui-card-body" style="height: 230px">' +
                            '<div style="width: 100%; padding-bottom: 60%; position: relative;">' +
                            '<img src="' + item.cover + '" style="width: 100%; height: 100%; position: absolute;">' +
                            '</div>' +
                            '<div class="my-line-limit-length-body">' +
                            '<a class="layui-badge-rim" style="border-color:#5fb878; color: #5fb878;">简介</a>' +
                            '<span style="font-size: 13px">&nbsp;' + item.details + '</span>' +
                            '</div></div></div></div>');
                    });

                    //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                    //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                    next(lis.join(''), page < Math.ceil(res.count / 9));
                });
            }
        });
        $('#search').click(function () {
            document.getElementById("introCard").innerHTML = '';
            var name = $('#name').val();
            flow.load({
                elem: '#introCard' //指定列表容器
                , done: function (page, next) { //到达临界点（默认滚动触发），触发下一页
                    var lis = [];
                    //以jQuery的Ajax请求为例，请求下一页数据（注意：page是从2开始返回）
                    $.get('/mausoleum/minfo/getList?page=' + page + '&limit=9', {
                        name : name,
                        isHtml : false
                    }, function (res) {
                        //假设你的列表返回在data集合中
                        layui.each(res.data, function (index, item) {
                            lis.push('<div class="layui-col-md4">' +
                                '<div class="layui-card" onclick="window.location.href=\'/mausoleum/html/minfo.html?mInfoId=' + item.mInfoId + '\'" style="cursor: pointer">' +
                                '<div class="layui-card-header my-line-limit-length-head">' + item.name + '</div>' +
                                '<div class="layui-card-body" style="height: 230px">' +
                                '<div style="width: 100%; padding-bottom: 60%; position: relative;">' +
                                '<img src="' + item.cover + '" style="width: 100%; height: 100%; position: absolute;">' +
                                '</div>' +
                                '<div class="my-line-limit-length-body">' +
                                '<a class="layui-badge-rim" style="border-color:#5fb878; color: #5fb878;">简介</a>' +
                                '<span style="font-size: 13px">&nbsp;' + item.details + '</span>' +
                                '</div></div></div></div>');
                        });

                        //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
                        //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
                        next(lis.join(''), page < Math.ceil(res.count / 9));
                    });
                }
            });
        })
    });
});
