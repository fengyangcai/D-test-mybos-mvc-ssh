<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>演示portal的效果</title>
<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="../js/easyui/themes/default/easyui.css">
	
<!-- 导入portal的资源 -->
<script type="text/javascript"
	src="../js/easyui/ext/jquery.portal.js"></script>
<link rel="stylesheet" type="text/css" href="../js/easyui/ext/portal.css">
</head>
<body>

	<!-- 1.设计一个div放门户 -->
	<div id="pp" style="width:800px;height:500px;">   
	    <div style="width:50%"></div>   
	    <div style="width:50%"></div>   
	</div> 
	
	<script type="text/javascript">
		$(function(){
			$("#pp").portal();
			
			//创建一个面板    
			var p = $('<div></div>').appendTo('body');    
			p.panel({    
			    title: '公共栏',    
			    height:150,    
			    closable: true,    
			    collapsible: true   
			});    
			     
			//把面板追加到portal    
			$('#pp').portal('add', {    
			    panel: p,   
			    //columnIndex: 追加的列索引  0：代表第1列
			    columnIndex: 0    
			});  
			
			
			var p2 = $('<div></div>').appendTo('body');    
			p2.panel({    
			    title: '系统问题反馈',    
			    height:150,    
			    closable: true,    
			    collapsible: true   
			});    
			     
			//把面板追加到portal    
			$('#pp').portal('add', {    
			    panel: p2,   
			    //columnIndex: 追加的列索引  0：代表第1列 （索引不能超出列数量）
			    columnIndex:1    
			});  
		});
	</script> 


</body>
</html>