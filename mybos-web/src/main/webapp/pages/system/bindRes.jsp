<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色绑定资源页面</title>
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
		<a id="bindResToRoleBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">开始绑定</a>
	</div>
	
	<script type="text/javascript">
		var action = "resource";
	
		$("#list").treegrid({
			idField:"id",
			treeField:"name",
			url:"../../resource/listByPageAndBindRes?roleId=${param.roleId}",
			columns:[[
			      {
			    	  field:"name",
			    	  title:"资源名称",
			    	  width:200,
			      },
			      {
			    	  field:"grantKey",
			    	  title:"授权码",
			    	  width:200,
			      },
			      {
			    	  field:"pageUrl",
			    	  title:"菜单访问路径",
			    	  width:200,
			      },
			      {
			    	  field:"resourceType",
			    	  title:"资源类型",
			    	  width:100,
			    	  formatter:function(value,row,index){
			    		  if(value!=null){
			    			return value=="0"?"菜单":"按钮";
			    		  }else{
			    			  return "";
			    		  }
			    	  }
			      }
			]],
			pagination:true,
			toolbar:"#toolbar",
			//设置每页显示的记录数
			pageSize:200,
			//修改允许出现的页面大小
			pageList:[100,200,300,400,500],
			//显示复选框
			checkbox:true,
			//取消级联选择
			cascadeCheck:false
		});
		
		//保存角色和资源的关系
		$("#bindResToRoleBtn").click(function(){
			
			//1.获取角色ID
			var roleId = "${param.roleId}";
			
			var resIds = "";
			//2.获取选中的资源ID（n个）
			//2.1 获取所有的节点
			var resIdArray = new Array();
			var nodes = $("#list").treegrid("getChildren");
			$(nodes).each(function(i){
				if(nodes[i].checked){
					resIdArray.push(nodes[i].id);
				}
			});
			resIds = resIdArray.join(",");

			//请求后台
			$.post("../../role/bindResToRole",{roleId:roleId,resIds:resIds},function(data){
				if(data.code=200){
					//关闭父页面的窗口
					//在子页面操作父页面的元素：window.parent
					window.parent.$("#bindResWin").window("close");
					
					window.parent.$.messager.alert("提示","绑定成功","info");
				}else{
					$.messager.alert("提示","绑定失败："+data.msg,"error");
				}
			},"json");
		});
		
	</script>
</body>
</html>