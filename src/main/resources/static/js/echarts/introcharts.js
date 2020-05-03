$(function () {
        var mycharts = echarts.init(document.getElementById('chartOne'));
        var option1 = {
            title: {
                show: true,
                text: '汉唐帝陵分布',
                subtext: '市级'

            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }//,
                    // restore: {
                    //     show: true
                    // },
                    // dataZoom: {
                    //     show: true
                    // },
                    // magicType: {
                    //     show: true
                    // }
                }
            },
            tooltip: {
                show: true,
                trigger: 'axis'
            },
            legend: {
                data: ['年代']
            },
            xAxis: [{
                type: 'category',
                data: ["西安市", "咸阳市", "渭南市"]
            }],
            yAxis: [{
                type: 'value'
            }],
            series: [{
                name: '帝陵数量',
                type: 'bar',
                color: '#009688',
                data: [2, 19, 8]
            }]
        };
        mycharts.setOption(option1);

        var twocharts = echarts.init(document.getElementById('chartTwo'));
        var option2 = {
            title: {
                show: true,
                text: '汉唐帝陵类型',
                subtext: '类型'
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {
                        show: true
                    },
                    saveAsImage: {
                        show: true
                    }//,
                    // restore: {
                    //     show: true
                    // },
                    // dataZoom: {
                    //     show: true
                    // },
                    // magicType: {
                    //     show: true
                    // }
                }
            },
            tooltip: {
                show: true
            },
            legend: {
                data: ['年代']
            },
            xAxis: [{
                type: 'category',
                data: ["依山为陵", "封土为陵",]
            }],
            yAxis: [{
                type: 'value'
            }],
            series: [{
                name: '数量',
                type: 'bar',
                color: '#009688',
                data: [15, 14]
            }]
        };
        twocharts.setOption(option2);
    }
)