<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>演示treegrid的效果</title>
<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="../js/easyui/themes/default/easyui.css">

</head>
<body>
	<div><button id="ddd">点击返回option</button></div><br/>

	<table id="list"></table>

	<script type="text/javascript">
		$(function(){
			$("#list").treegrid({
				//idField： 指定_parentId找到哪个属性名称的值
				idField:"id",
				//treeField: 指定节点显示的文本
				treeField:"name",
				url:"product2.json",
				columns:[[
					 {
						 field:"id",
						 title:"商品编号",
						 width:100
					 },
					 {
						 field:"name",
						 title:"商品名称",
						 width:200
					 },
					 {
						 field:"price",
						 title:"商品价格",
						 width:200
					 }
				]],
				pagination:true,
				//是否显示复习框
				checkbox:true,
				//是否级联检查
				cascadeCheck:false,
				//末级节点才显示复选框
				onlyLeafCheck:true,

			});
		});
	</script>
<script type="text/javascript">
$("#ddd").click(function () {
	$("#list").treegrid('options'

	)
})
</script>
</body>
</html>