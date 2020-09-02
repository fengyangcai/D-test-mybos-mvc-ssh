<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>定区管理</title>
    <!-- 导入easyui的文件  5个  第一个必须是jquery -->
    <script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
    <script type="text/javascript" src="../../js/crud.js"></script>

</head>
<body>
<!-- 搜索表单 -->
<form id="searchForm" action="">
    定区名称：<input type="text" name="fixedAreaName"/>
    联系电话：<input type="text" name="telephone"/>
    <a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
</form>

<!-- 工具条 -->
<div id="toolbar">
    <a id="addBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">增加</a>
    <a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
    <a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
    <a id="bindCustomerBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">关联客户</a>
</div>

<!-- 列表展示 -->
<table id="list"></table>


<!-- 编辑表单 -->
<div id="editWin" class="easyui-window" data-options="title:'定区添加修改',closed:true"
     style="width:600px;top:20px;left:200px">
    <!-- 按钮区域 -->
    <div class="datagrid-toolbar">
        <a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
    </div>
    <!-- 编辑区域 -->
    <div>
        <form id="editForm" method="post">
            <!--提供隐藏域 装载id -->
            <input type="hidden" name="id"/>
            <table class="table-edit" width="80%" align="center">
                <tr>
                    <td>定区名称</td>
                    <td>
                        <input type="text" name="fixedAreaName" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>负责的快递员</td>
                    <td>
                        <select id="courierId" name="courier.id" class="easyui-combobox"
                                data-options="url:'../../base/courier/list',valueField:'id',textField:'name'"
                                style="width: 200px;"></select>
                    </td>
                </tr>
                <tr>
                    <td>联系电话</td>
                    <td>
                        <input name="telephone" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</div>
<!--关联客户窗口 -->
<div class="easyui-window" title="关联客户窗口" id="customerWin" modal="true"
     collapsible="false" closed="true" minimizable="false" maximizable="false"
     style="top:20px;left:200px;width: 700px;height: 300px;">
    <div style="overflow:auto;padding:5px;" border="false">
        <form id="customerForm" method="post">
            <table class="table-edit" width="80%" align="center">
                <tr class="title">
                    <td colspan="3">关联客户</td>
                </tr>
                <tr>
                    <td>
                        <select id="associationSelect" multiple="multiple" size="10" style="width: 300px;"></select>
                    </td>
                    <td>
                        <input type="button" value="》》" id="toRight">
                        <br/>
                        <input type="button" value="《《" id="toLeft">
                    </td>
                    <td>
                        <select id="noassociationSelect" name="customerIds" multiple="multiple" size="10"
                                style="width: 300px;"></select>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-save'">关联客户</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>


<script type="text/javascript">
    //当前模块的名称空间
    var action = "base/fixedArea";

    //当前模块展示列 shift+alt+A
    //列字段
    var columns = [[{
        field: 'id',
        title: '编号',
        width: 80,
        align: 'center',
        checkbox: true
    }, {
        field: 'fixedAreaName',
        title: '定区名称',
        width: 120,
        align: 'center'
    }, {
        field: 'courier',
        title: '负责人',
        width: 120,
        align: 'center',
        formatter: function (value, row, index) {
            if (value != null) {
                return value.name;
            } else {
                return "";
            }
        }
    }, {
        field: 'telephone',
        title: '联系电话',
        width: 120,
        align: 'center'
    }]];

    //覆盖方法
    function loadEditForm(row) {
        $("#courierId").combobox("setValue", row.courier.id);
    }

    $("#editBtn").click(function () {
        //先判断是否选择的是一行
        var selectRow = $("#list").datagrid("getSelections");
        if (selectRow.length != 1) {
            $.messager.alert("提示", "修改操作只能选择一行", "warning");
            return
        }

        //得先清空表单
        $("#editForm").form('clear');
        console.log(selectRow[0].id);
        //把选择的行的数据渲染到表格
        //selectRow=[{courier: {id: 2, courierNum: "002", standard: {…}, name: "快递员002", telephone: "13750015002", …},fixedAreaName: "番禺区", id: 1, telephone: "137500655150"}]
        $("#editForm").form("load", {
            id: selectRow[0].id,
            fixedAreaName: selectRow[0].fixedAreaName,
            telephone: selectRow[0].telephone,
            courier: selectRow[0].courier.id
        })
        $("#courierId").combobox("setValue", selectRow[0].courier.id);
        $("#editWin").window("open");
    })

    //点击按钮，弹出绑定客户的窗口
    $("#bindCustomerBtn").click(function () {
        //点击按钮，弹出绑定客户的窗口
        var rows = $("#list").datagrid("getSelections");
        if (rows.length != 1) {
            $.messager.alert("提示", "只能选择一个定区", "warning");
            return
        }
        //加载客户列表
        $.post("../../base/fixedArea/findByNoAssociateCustomers", function (data) {
            //清空select
            $("#noassociationSelect").empty();
            //填充select
            $(data).each(function (i) {
                $("#noassociationSelect").append("<option value='" + data[i].id + "'>" + data[i].userName + "(" + data[i].telephone + ")</option>");
            });
        }, "json");
        //获取请求参数
        var fxedAreaId = rows[0].id;
        //获取绑定的客户
        $.post("../../base/fixedArea/findByHasAssociateCustomers", {fixedAreaId: fxedAreaId}, function (data) {
            //先清空数据
            $("#associationSelect").empty();
            for (var i = 0; i < data.length; i++) {
                $("#associationSelect").append("<option value='" + data[i].id + "'>" + data[i].userName + "(" + data[i].telephone + ")</option>");
            }
        }, "json");

        $("#customerWin").window("open");
        //实现左——》右
        $("#toRight").click(function () {

            // $("#associationSelect option").select().appendTo("#noassociationSelect");
            $("#associationSelect option:selected").appendTo("#noassociationSelect");

        })
        //从右往左
        $("#toLeft").click(function () {
            $("#noassociationSelect option:selected").appendTo("#associationSelect");
        })
        //关联客户按钮执行
        $("#associationBtn").click(function () {
            //选择全部的option
            //val():只会获取选中的option
            //或者prop方法都可以的。attr会出现空参数的情况
            $("#associationSelect option").prop("selected","selected");
            var idsArray = $("#associationSelect").val();
            var custIds = idsArray.join(",");
           // console.log(custIds);
            //把fixedAreaId更新到客户的fixedAreaId字段
            $.post("../../base/fixedArea/associateCustomersToFixedArea",{fixedAreaId:fxedAreaId,custIds:custIds},function (data) {
                if(data.code==200){
                    $.messager.alert("提示","定区关联客户成功","info");
                }else{
                    $.messager.alert("提示","定区关联客户失败："+data.msg,"error");
                }
            },"json")
        })
    });

</script>

</body>
</html>