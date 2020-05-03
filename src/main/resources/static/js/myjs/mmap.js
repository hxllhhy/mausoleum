$(function () {
    map.on('click', function (e) {
        var coord = map.getCoordinateFromPixel(e.pixel).toString().split(',');
        $('#x').val(coord[0]);
        $('#y').val(coord[1]);
    });

    var container = document.getElementById("popup");
    var content = document.getElementById("popup-content");
    var popupCloser = document.getElementById("popup-closer");

    /**
     * 为map添加点击事件监听，渲染弹出popup
     */
    map.on('click', function (evt) {
        //判断当前单击处是否有要素，捕获到要素时弹出popup
        var feature = map.forEachFeatureAtPixel(evt.pixel, function (feature, layer) { return feature; });
        if (feature) {
            $('#popup').show();
            var overlay = new ol.Overlay({
                //设置弹出框的容器
                element: container,
                //是否自动平移，即假如标记在屏幕边缘，弹出时自动平移地图使弹出框完全可见
                autoPan: true,
                //Popup放置的位置
                positioning: 'bottom-center',
                //是否应该停止事件传播到地图窗口
                stopEvent: true,
                autoPanAnimation: {
                    //当Popup超出地图边界时，为了Popup全部可见，地图移动的速度
                    duration: 250
                }

            });
            map.addOverlay(overlay);

            if (feature.get('type') == 5) {
                var id = feature.get('id');
                var minfo;
                $.getJSON('/mausoleum/minfo/get', {
                    mInfoId : id,
                    isHtml : false
                }, function (result) {
                    minfo = result.data;
                    //清空popup的内容容器
                    content.innerHTML = '';
                    //在popup中加载当前要素的具体信息
                    content.innerHTML = '<div class="layui-row">' +
                        '<div class="layui-col-md9 my-line-limit-length-head">' +
                        '<strong><span style="font-size: 18px">' + minfo.name + '</span></strong>' +
                        '</div>' +
                        '<div class="layui-col-md3">' +
                        '<a href="/mausoleum/html/minfo.html?mInfoId=' + minfo.mInfoId + '" style="text-decoration: underline; text-align: right; color: #009688">查看更多</a>' +
                        '</div>' +
                        '<div class="layui-col-md5">' +
                        '<div style="width: 100%; padding-bottom: 62%; position: relative;">' +
                        '<img src="' + minfo.cover + '" style="width: 95%; height: 100%; position: absolute;">' +
                        '</div>' +
                        '</div>' +
                        '<div class="layui-col-md7 my-line-limit-length-body">' +
                        '<span style="font-size: 13px">简介：' + minfo.details + '</span>' +
                        '</div>' +
                        '<div class="layui-col-md12 my-line-limit-length-head">' +
                        '<span style="font-size: 13px">' + feature.getGeometry().B + '</span>' +
                        '</div>' +
                        '</div>';
                    if (overlay.getPosition() == undefined) {
                        //设置popup的位置
                        overlay.setPosition(feature.getGeometry().B);
                    }
                });
            } else {
                var remark = feature.get('remark');
                //清空popup的内容容器
                content.innerHTML = '';
                //在popup中加载当前要素的具体信息
                content.innerHTML = '<div class="layui-row">' +
                    '<div class="layui-col-md12">' +
                    '<span>' + remark + '</span>' +
                    '</div>' +
                    '<div class="layui-col-md12 my-line-limit-length-head">' +
                    '<span style="font-size: 13px">' + feature.getGeometry().B + '</span>' +
                    '</div>' +
                    '</div>';
                if (overlay.getPosition() == undefined) {
                    //设置popup的位置
                    overlay.setPosition(feature.getGeometry().B);
                }
            }
            /**
             * 添加关闭按钮的单击事件（隐藏popup）
             */
            popupCloser.onclick = function () {
                //未定义popup位置
                overlay.setPosition(undefined);
                //失去焦点
                popupCloser.blur();
                return false;
            };
        }
    });
});
function showclickmcoord(minfo) {
    if (minfo.coordinateId == 0) {
        layer.msg('该陵墓没有坐标', {icon: 5, anim: 6});
    } else {
        $.getJSON('/mausoleum/coordinate/get', {
            coordinateId: minfo.coordinateId
        }, function (result) {
            var data = result.data;
            var view = map.getView();
            var currentPoint = [data.x, data.y];
            view.setCenter(currentPoint);
            view.setZoom(15);
        });
    }
}

function showmycoord() {
    $.getJSON('/mausoleum/coordinate/getSelfList',function (result) {
        data = result.data;
        if (data == null) {
            layui.layer.msg('你没有添加坐标点', {icon: 5, anim: 6});
        } else {
            var vectorSource2 = new ol.source.Vector({});
            var createLabelStyle2 = function (feature) {
                return new ol.style.Style({
                    image: new ol.style.Icon({
                        anchor: [0.5, 40],
                        anchorOrigin: 'top-right',
                        anchorXUnits: 'fraction',
                        anchorYUnits: 'pixels',
                        offsetOrigin: 'top-right',
                        opacity: 0.75,  //透明度
                        src: '../icon/icon2.png'
                    }),
                    text: new ol.style.Text({
                        textAlign: 'center', //位置
                        textBaseline: 'middle', //基准线
                        font: '14px',  //文字样式
                        text: feature.get('name'),  //文本内容
                        fill: new ol.style.Fill({color: '#aa3300'}), //文本填充样式（即文字颜色）
                        stroke: new ol.style.Stroke({color: '#ffcc33', width: 2})
                    })
                });
            };
            var minX = data[0].x;
            var maxX = data[0].x;
            var minY = data[0].y;
            var maxY = data[0].y;
            for (var i = 0; i < data.length; i++) {
                var x = data[i].x;
                var y = data[i].y;
                var j = i + 1;
                if (x < minX) minX = x;
                if (x > maxX) maxX = x;
                if (y < minY) minY = y;
                if (y > maxY) maxY = y;

                var iconFeature2 = new ol.Feature({
                    geometry: new ol.geom.Point([x, y], 'XY'),
                    name: '我的坐标' + j,
                    remark: data[i].remark,
                    type: 4
                });
                iconFeature2.setStyle(createLabelStyle2(iconFeature2));
                //矢量标注的数据源
                vectorSource2.addFeature(iconFeature2);
                console.log(iconFeature2.getGeometry());
            }
            //矢量标注图层
            var vectorLayer2 = new ol.layer.Vector({
                title: "我的坐标",
                source: vectorSource2
            });
            map.addLayer(vectorLayer2);
            if (data.length == 1) {
                var view = map.getView();
                view.setCenter([data[0].x, data[0].y]);
                view.setZoom(15);
            } else {
                map.getView().fit([minX, minY, maxX, maxY], map.getSize());
            }
        }
    });
}
function searchCoord() {
    var name = $("#name").val();
    if (name == '') {
        return undefined;
    }
    $.getJSON('/mausoleum/coordinate/search',{
        name : name
    }, function (result) {
        var data = result.data;
        if (data == null) {
            layui.layer.msg('该地点不存在', {icon: 5, anim: 6});
        }
        var minX = data[0].x;
        var maxX = data[0].x;
        var minY = data[0].y;
        var maxY = data[0].y;
        for (var i = 0; i < data.length; i++) {
            var x = data[i].x;
            var y = data[i].y;
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }
        if (data.length == 1) {
            var view = map.getView();
            view.setCenter([data[0].x, data[0].y]);
            view.setZoom(15);
        }
        else {
            map.getView().fit([minX, minY, maxX, maxY], map.getSize());
        }
    });
}