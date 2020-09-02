<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>收派标准管理</title>
    <script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/icon.css">
    <!-- <script type="text/javascript" src="../../easyui/myjs/form.js"></script> -->
    <script type="text/javascript" src="../../js/crud.js"></script>

</head>
<body>
<form id="searchForm" action="">
    <label for="name">收派标准名称：</label> <input id="name" class="easyui-validatebox" type="text" name="name"/>
    <label for="minWeight">最小重量：</label> <input id="minWeight" class="easyui-validatebox" type="text" name="minWeight"/>
    <label for="minLength">最小长度：</label> <input id="minLength" class="easyui-validatebox" type="text" name="minLength"/>
    <a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a>

</form>
<div id="toolbar">
    <%--这个标签是根据后台的授权码是否存在有来显示的--%>
    <shiro:hasPermission name="user_add_btn">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addBtn">添加</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteBtn">删除</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="editBtn">编辑</a>

</div>

<table id="list"></table>


<!--编辑窗口  -->
<div id="editWin" class="easyui-window" title="编辑窗口" style="width:600px;top:50px;left:200px"
     data-options="iconCls:'icon-save',modal:true" ,resizable="true" closed="true">
    <div>
        <form id="editForm" method="post">
            <!--添加一个id隐藏域 -->
            <input type="hidden" name="id"/>
            <table width="80%" align="center" class="table-edit">
                <tr>
                    <!-- <label>收派标准名称</label> -->
                    <td>收派标准名称:</td>
                    <td><input type="text" name="name" class="easyui-validatebox"
                               data-options="required:true,missingMessage:'请输入名称'"/></td>
                </tr>
                <tr>
                    <td>最小重量:</td>
                    <td><input type="text" name="minWeight"
                               class="easyui-numberbox" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>最大重量:</td>
                    <td><input type="text" name="maxWeight"
                               class="easyui-numberbox" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>最小长度:</td>
                    <td><input type="text" name="minLength"
                               class="easyui-numberbox" data-options="required:true"/></td>
                </tr>
                <tr>
                    <td>最大长度:</td>
                    <td><input type="text" name="maxLength"
                               class="easyui-numberbox" data-options="required:true"/></td>
                </tr>
            </table>
        </form>
    </div>
    <!-- 按钮区域 -->
    <div class="datagrid-toolbar" align="center">
        <a id="save" class="easyui-linkbutton" href="#" icon="icon-save"  >保存</a>
    </div>
</div>
<script type="text/javascript">
    var action = "base/standard";
    //当前模块的展示
    var columns = [[{
        field: "id",
        title: "编号",
        width: 100
    }, {
        field: "name",
        title: "收派标准名称",
        width: 200
    }, {
        field: "minWeight",
        title: "最小重量",
        width: 200
    }, {
        field: "maxWeight",
        title: "最大重量",
        width: 200
    }, {
        field: "minLength",
        title: "最小长度",
        width: 200
    }, {
        field: "maxLength",
        title: "最大长度",
        width: 200
    }]];



    $(function () {

        //回显表单的数据
        $("#editBtn").click(function () {
            //判断是否是选择的是否是一行
            var rows = $("#list").datagrid("getSelections");

            if (rows.length != 1) {
                $.messager.alert("提示", "修改操作只能选择一行", "warning");
                return;
            }
            //得先清空表单
            $("#editForm").form('clear');
            //这里可以就重新ajax请求本来也可以直接从有的列表中获取但有一列数据要重新请求获取数据
            //$("#editForm").form('load',rows[0].standard.name);
            $("#editForm").form('load', "../../" + action + "/findById?uuid=" + rows[0].id);
            //这个方法的实现由具体的页面实现
            //$("#standardId").combobox("setValue",rows[0].standard.name);
            //弹出window
            $("#editWin").window("open");

        });

    });

    /*
         $('#list').datagrid({
            url : '../../base/standard/list1',
            columns : columns,
            fitColumns : true,
            autoRowHeight : true,
            toolbar : '#tb',
            rownumbers : true
        });*/
</script>
</body>
</html>