<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>运单快速录入</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>

</head>
<body class="easyui-layout">
		<h1>哈哈哈</h1>
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="addRowBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加一行</a>
		<a id="cancelBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">取消编辑</a>
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">保存</a>
	</div>
	
	<div region="center" border="false">
			<table id="list"></table>
	</div>
	
	<script type="text/javascript">
		$(function(){
			$("#list").datagrid({
				iconCls : 'icon-forward',
				fit : true,
				border : true,
				striped : true,
				pagination : true,
				toolbar : "#toolbar",
				url :  "",
				idField : 'id',
				columns : columns,
				//绑定结束编辑的事件
				//row：对应于完成编辑的行的记录 Json格式数据
				onAfterEdit:function(index, row, changes){
					//把row方式到后台保存数据
					$.post("../../wayBill/saveWayBill",row,function(data){
						if(data.code=200){
							$.messager.alert("提示","快速录入成功","info");
						}else{
							$.messager.alert("提示","快速录入失败："+data.msg,"error");
						}
					},"json");
				}
			});
			
			
		});
		
		// 定义列
		var columns = [ [ {
			field : 'wayBillNum',
			title : '运单编号',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'arriveCity',
			title : '到达地',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		},{
			field : 'goodsType',
			title : '产品',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'num',
			title : '件数',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'weight',
			title : '重量',
			width : 120,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}, {
			field : 'floadreqr',
			title : '配载要求',
			width : 220,
			align : 'center',
			editor:{
				type:"validatebox",
				options:{
					required:true
				}
			}
		}] ];
	
		var currentIndex;
		//添加一行
		$("#addRowBtn").click(function(){
			if(currentIndex!=undefined){
				return;
			}
			
			//插入一行
			$("#list").datagrid("insertRow",{
				index:0,
				row:{}
			});
			
			//让该行可编辑
			$("#list").datagrid("beginEdit",0);
			
			currentIndex = 0;
		});
		
		
		//取消编辑
		$("#cancelBtn").click(function(){
			$("#list").datagrid("cancelEdit",currentIndex);
			
			//删除该行
			$("#list").datagrid("deleteRow",currentIndex);
			
			//还原初始值
			currentIndex = undefined;
		});
		
		//保存编辑
		$("#saveBtn").click(function(){
			//结束编辑
			$("#list").datagrid("endEdit",currentIndex);
			//还原初始值
			currentIndex = undefined;
		});
	</script>
</body>
</html>