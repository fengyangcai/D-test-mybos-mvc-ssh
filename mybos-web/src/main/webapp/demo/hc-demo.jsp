<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>演示HighCharts</title>
<script type="text/javascript" src="../js/highcharts/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../js/highcharts/highcharts.js"></script>
<script type="text/javascript">
			$(function () {
			    $('#container').highcharts({
			    	//标题
			        title: {
			            text: 'Java就业率变化图',
			            x: -20 //center
			        },
			        //子标题
			        subtitle: {
			            text: '来源: http:/www.fyc.com',
			            x: -20
			        },
			        //横坐标名称
			        xAxis: {
			            categories: ['1月', '2月', 'Mar', 'Apr', 'May', 'Jun',
			                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
			        },
			        //纵坐标的名称
			        yAxis: {
			            title: {
			                text: '就业率（%）'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        //纵坐标的值
			        series: [{
			            name: 'Tokyo',
			            data: [20, 30, 40, 45.2, 50,50, 50, 60, 70, 80, 90, 100]
			        }, {
			            name: 'New York',
			            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
			        }, {
			            name: 'Berlin',
			            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
			        }, {
			            name: 'London',
			            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
			        }]
			    });
			});
		</script>
</head>
<body>


<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

</body>
</html>