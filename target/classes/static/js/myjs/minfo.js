$(function () {
    var mInfoId = getQueryString('mInfoId');

    var url = '/mausoleum/minfo/get';

    $.getJSON(url, {
        mInfoId : mInfoId,
        isHtml : true
    },function (result) {
        if (result.code == 0) {
            var mInfo = result.data;
            var name = mInfo.name;
            var details = mInfo.details;
            $('#name').html(name);
            $('#details').html(details);
            $('#modifyminfo').html('<a href="/mausoleum/html/minfo-modify.html?mInfoId=' + mInfoId +'">我要修改</a>');
        }
    });
});