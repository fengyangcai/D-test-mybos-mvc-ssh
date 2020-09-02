<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>演示datagrid的行编辑效果</title>
<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="../js/easyui/themes/icon.css">
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="../js/easyui/themes/default/easyui.css">

</head>
<body>

	<!-- 工具条 -->
	<div id="toolbar">
		<a id="beginEditBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">开始编辑</a>  
		<a id="cancelEditBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">取消编辑</a>  
		<a id="endEditBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">结束编辑</a>  
		<a id="addRowBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">添加行</a>  
		<a id="delRowBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除行</a>  
	</div>
	
	<table id="list"></table>

	<script type="text/javascript">
		$(function(){
			$("#list").datagrid({
				url:"product.json",
				columns:[[
					 {
						 field:"id",
						 title:"商品编号",
						 width:100,
						 editor:{
							 //type:代表编辑框类型
							 type:"validatebox",
							 //options: 代表type编辑类型对应的属性
							 options:{
								 required:true
							 }
						 }
					 },
					 {
						 field:"name",
						 title:"商品名称",
						 width:200,
						 editor:{
							 //type:代表编辑框类型
							 type:"validatebox",
							 //options: 代表type编辑类型对应的属性
							 options:{
								 required:true
							 }
						 }
					 },
					 {
						 field:"price",
						 title:"商品价格",
						 width:200,
						 editor:{
							 //type:代表编辑框类型
							 type:"validatebox",
							 //options: 代表type编辑类型对应的属性
							 options:{
								 required:true
							 }
						 }
					 }
				]],
				pagination:true,
				toolbar:"#toolbar",
				//单选
				singleSelect:true,
				//绑定双击事件
				/*
					index:行索引
					row：行对象
				*/
				onDblClickRow:function(index, row){
					$("#list").datagrid("beginEdit",index);
					currentIndex = index;
				}
			});
			
			var currentIndex;
			//开始编辑：
			$("#beginEditBtn").click(function(){
				
				//获取选中行
				var rows = $("#list").datagrid("getSelections");
				if(rows!=null){
					//获取某行索引号
					currentIndex = $("#list").datagrid("getRowIndex",rows[0]);
					//开始编辑选中行
					$("#list").datagrid("beginEdit",currentIndex);
				}
			});
			
			//取消编辑：
			$("#cancelEditBtn").click(function(){
				$("#list").datagrid("cancelEdit",currentIndex);
			});
			
			//结束编辑：
			$("#endEditBtn").click(function(){
				$("#list").datagrid("endEdit",currentIndex);
			});
			
			//添加行
			/* $("#addRowBtn").click(function(){
				 $("#list").datagrid("appendRow",{
					id:4,
					name:"小米手机",
					price:"3000"
				});
				
				//追加一行
				$("#list").datagrid("appendRow",{});
				//可编辑
				//获取datagrid的总行数
				var rows = $("#list").datagrid("getRows");
				
				$("#list").datagrid("beginEdit",rows.length-1);
				
				currentIndex = rows.length-1;
			}); */
			
			$("#addRowBtn").click(function(){
				//插入一行
				$("#list").datagrid("insertRow",{
					//index:需要插入的行索引
					index:0,
					row:{}
				});
				//可编辑
				//获取datagrid的总行数
				var rows = $("#list").datagrid("getRows");
				
				$("#list").datagrid("beginEdit",0);
				
				currentIndex = 0;
			});
			
			//删除行
			$("#delRowBtn").click(function(){
				//删除选中行

				//获取选中行
				var rows = $("#list").datagrid("getSelections");
				if(rows!=null){
					//获取某行索引号
					currentIndex = $("#list").datagrid("getRowIndex",rows[0]);
					//删除行
					$("#list").datagrid("deleteRow",currentIndex);
				}
			});
		});
	</script>
</body>
</html>