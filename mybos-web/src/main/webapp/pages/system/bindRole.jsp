<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户绑定角色页面</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../js/form.js"></script>
</head>
<body>
	<!-- 列表显示 -->
	<table id="list"></table>
	
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="bindRoleToUserBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">用户绑定角色</a>
	</div>
	
	<script type="text/javascript">
		//当前模块的action
		var action = "role";
		
		//表格的列数据
		var columns = [ [ {
			field : "id",
			checkbox : true
		}, {
			field : "name",
			title : "角色名",
			width : 120,
			align : "center"
		}, {
			field : "keyword",
			title : "角色关键字",
			width : 120,
			align : "center"
		}, {
			field : "description",
			title : "备注",
			width : 120,
			align : "center"
		} ] ];
		
		//保存用户和角色关系
		$("#bindRoleToUserBtn").click(function(){
			//1.用户ID
			var userId = "${param.userId}";
			//2.选中的角色ID
			var rows = $("#list").datagrid("getSelections");
			var roleIdArray = new Array();
			$(rows).each(function(i){
				roleIdArray.push(rows[i].id);
			});
			var roleIds = roleIdArray.join(",");
			
			$.post("../../user/bindRoleToUser",{userId:userId,roleIds:roleIds},function(data){
				if(data.code==200){
					//关闭父页面的窗口
					//在子页面操作父页面的元素：window.parent
					window.parent.$("#bindRoleWin").window("close");
					
					window.parent.$.messager.alert("提示","绑定成功","info");
				}else{
					$.messager.alert("提示","绑定失败："+data.message,"error");
				}
			},"json");
		});
		
		//列表展示
		$("#list").datagrid({
			//url:抓取数据的url
			url:"../../"+action+"/listByPageAndRole?userId=${param.userId}",
			//columns: 填充所有列
			   //field: 该列找哪个字段名称填充
			columns:columns,
			//开启分页
			pagination:true,
			//绑定工具条
			toolbar:"#toolbar",
			onLoadSuccess:function(data){
				$(data.rows).each(function(i){
					if(data.rows[i].checked){
						//选中行
						$("#list").datagrid("selectRow",i);
					}
				});
			}
		});	
		
	</script>
</body>
</html>