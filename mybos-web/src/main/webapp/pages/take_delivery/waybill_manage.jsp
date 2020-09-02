<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>运单管理</title>
<script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../../css/default.css">
<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<%--<script type="text/javascript" src="../../js/form.js"></script>--%>
<script type="text/javascript" src="../../js/crud.js"></script>
</head>
<body>
	<!-- 搜索框 -->
	<div class="datagrid-toolbar" style="height: 40px;">
		<form id="searchForm" method="post">
		    运单号:<input name="wayBillNum" style="line-height:26px;border:1px solid #ccc">
		    发货地:<input name="sendAddress" style="line-height:26px;border:1px solid #ccc" >
		 收货地:<input name="recAddress" style="line-height:26px;border:1px solid #ccc" >
		<select class="easyui-combobox" style="width:150px" name="sendProNum">
					<option value="">请选择快递产品类型</option>
					<option value="速运当日">速运当日</option>
					<option value="速运次日">速运次日</option>
					<option value="速运隔日">速运隔日</option>
		</select>			
		<select class="easyui-combobox" style="width:150px" name="signStatus">
					<option value="0">请选择运单状态</option>
					<option value="1">待发货</option>
					<option value="2">派送中</option>
					<option value="3">已签收</option>
					<option value="4">异常</option>
		</select>
		</form>
	</div>
	
	<!-- 列表 -->
	<table id="list"></table>
	<!-- 按钮列表 -->
	<div id="toolbar">
		<a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">修改</a>
		<a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">作废</a>
		<a id="delBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">打印查询结果</a>
	</div>

	<script type="text/javascript">
		//action访问名称
		var action="wayBill";
		
		//列字段
		var columns = [ [ {
				field : 'id',
				checkbox : true,
			},{
				field : 'wayBillNum',
				title : '运单编号',
				width : 80,
				align : 'center'
			},{
				field : 'sendName',
				title : '寄件人',
				width : 80,
				align : 'center'
			}, {
				field : 'sendMobile',
				title : '寄件人电话',
				width : 120,
				align : 'center'
			}, {
				field : 'sendCompany',
				title : '寄件人公司',
				width : 120,
				align : 'center'
			}, {
				field : 'sendAddress',
				title : '寄件人详细地址',
				width : 120,
				align : 'center'
			}, {
				field : 'recName',
				title : '收件人',
				width : 120,
				align : 'center'
			}, {
				field : 'recMobile',
				title : '收件人电话',
				width : 120,
				align : 'center'
			}, {
				field : 'recAddress',
				title : '收件人详细地址',
				width : 200,
				align : 'center'
			}, {
				field : 'sendProNum',
				title : '产品类型',
				width : 200,
				align : 'center'
			},{
				field : 'recAddress',
				title : '收件人详细地址',
				width : 200,
				align : 'center'
			}, {
				field : 'payTypeNum',
				title : '支付类型',
				width : 200,
				align : 'center'
			}, {
				field : 'weight',
				title : '重量',
				width : 200,
				align : 'center'
			}, {
				field : 'num',
				title : '原件数',
				width : 200,
				align : 'center'
			},  {
				field : 'feeitemnum',
				title : '实际件数',
				width : 200,
				align : 'center'
			},  {
				field : 'actlweit',
				title : '实际重量',
				width : 200,
				align : 'center'
			},  {
				field : 'vol',
				title : '体积',
				width : 200,
				align : 'center'
			}, {
				field : 'floadreqr',
				title : '配载要求',
				width : 200,
				align : 'center'
			}, {
				field : 'wayBillType',
				title : '运单类型',
				width : 80,
				align : 'center',
			}] ];
		
	</script>
</body>
</html>