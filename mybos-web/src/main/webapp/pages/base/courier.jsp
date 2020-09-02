<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>快递员管理</title>
    <script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/icon.css">
    <script type="text/javascript" src="../../js/crud.js"></script>



</head>
<body>
<form id="searchForm" action="">
    工号：<input type="text" name="courierNum"/>
    姓名：<input type="text" name="name"/>
    电话：<input type="text" name="telephone"/>
    取派标准：<select name="standard.id" class="easyui-combobox"
                  data-options="url:'../../base/standard/list',valueField:'id',textField:'name'"
                  style="width: 200px;"></select>
    <a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>


</form>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addBtn"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteBtn"></a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="editBtn"></a>

</div>

<table id="list"></table>


<!--编辑窗口  -->
<div id="editWin" class="easyui-window" title="快递员编辑" style="width:700px;top:50px;left:200px"
     data-options="iconCls:'icon-save',modal:true" ,resizable="true" closed="true">
    <div>
        <form id="editForm" method="post">
            <!--添加一个id隐藏域 -->
            <input type="hidden" name="id"/>
            <table class="table-edit" width="80%" align="center">
                <tr>
                    <td>快递员工号</td>
                    <td>
                        <input type="text" name="courierNum" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>姓名</td>
                    <td>
                        <input type="text" name="name" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>手机</td>
                    <td>
                        <input type="text" name="telephone" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>所属单位</td>
                    <td>
                        <input type="text" name="company" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>查台密码</td>
                    <td>
                        <input type="text" name="checkPwd" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>PDA号码</td>
                    <td>
                        <input type="text" name="pda" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>取派标准</td>
                    <td>
                        <select id="standardId" name="standard.id" class="easyui-combobox"
                                data-options="url:'../../base/standard/list',valueField:'id',textField:'name'"
                                style="width: 200px;"></select>
                    </td>
                </tr>
            </table>

        </form>
    </div>
    <!-- 按钮区域 -->
    <div class="datagrid-toolbar" align="center">
        <a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
    </div>
</div>

<script type="text/javascript">
    var action = "base/courier";
    //当前模块的展示
    var columns = [[{
        field: 'id',
        checkbox: true,
    }, {
        field: 'courierNum',
        title: '工号',
        width: 80,
        align: 'center'
    }, {
        field: 'name',
        title: '姓名',
        width: 80,
        align: 'center'
    }, {
        field: 'telephone',
        title: '手机号',
        width: 120,
        align: 'center'
    }, {
        field: 'checkPwd',
        title: '查台密码',
        width: 120,
        align: 'center'
    }, {
        field: 'pda',
        title: 'PDA号',
        width: 120,
        align: 'center'
    }, {
        field: 'standard',
        title: '取派标准',
        width: 120,
        align: 'center',
        //格式化列
        /*
          value: 当前单元格的值
          row：当前行对象
          index：当前行索引。从0开始
        */
        formatter: function (value, row, index) {
            if (value != null) {
                return value.name;
            } else {
                return "";
            }
        }
    }, {
        field: 'company',
        title: '所属单位',
        width: 200,
        align: 'center'

    }]];

    function loadEditForm(row){
        //回显快递员的收派标准的值
        //值：可以填写valueField或者textField的值，建议使用valueField
        $("#standardId").combobox("setValue",row.standard.id);
    }
    /*
         $('#list').datagrid({
            url : '../../base/courier/list1',
            columns : columns,
            fitColumns : true,
            autoRowHeight : true,
            toolbar : '#tb',
            rownumbers : true
        });*/

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
            loadEditForm(rows[0]);
            //$("#standardId").combobox("setValue",rows[0].standard.name);
            //弹出window
            $("#editWin").window("open");

        });

    });




</script>


</body>
</html>