<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>演示datagrid的回显效果</title>
<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="../js/easyui/themes/default/easyui.css">

</head>
<body>


	<table id="list"></table>

	<script type="text/javascript">
		$(function(){
			$("#list").datagrid({
				url:"product3.json",
				columns:[[
					 {
						 field:"id",
						 title:"商品编号",
						 width:100,
						 checkbox:true
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
				toolbar:"#toolbar",
				//数据加载完成后
				onLoadSuccess:function(data){ //后台返回的数据，json对象
					$(data.rows).each(function(i){
						if(data.rows[i].checked){
							//选中该行
							$("#list").datagrid("selectRow",i);
						}
					});
				}
			});
		});
	</script>
</body>
</html>