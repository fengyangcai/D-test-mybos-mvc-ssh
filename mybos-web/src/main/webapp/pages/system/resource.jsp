<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>资源管理</title>
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
    <a id="addBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加</a>
    <a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
    <a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
</div>

<!-- 编辑表单 -->
<div id="editWin" class="easyui-window" data-options="title:'资源编辑',closed:true" style="width:600px;top:50px;left:200px">
    <!-- 按钮区域 -->
    <div class="datagrid-toolbar">
        <a id="save" class="easyui-linkbutton" href="#" icon="icon-save">保存</a>
    </div>
    <!-- 编辑区域 -->
    <div>
        <form id="editForm" method="post">
            <!-- 提供隐藏域ID -->
            <input type="hidden" name="id"/>
            <table width="80%" align="center" class="table-edit">
                <tr>
                    <td>资源名称</td>
                    <td>
                        <input type="text" name="name"
                               class="easyui-validatebox" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <td>资源类型</td>
                    <td>
                        <select name="resourceType" class="easyui-combobox" data-options="editable:false,width:150">
                            <option value="0">菜单</option>
                            <option value="1">按钮</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>菜单页面路径</td>
                    <td>
                        <input type="text" name="pageUrl"
                               class="easyui-validatebox" data-options="required:false"/>
                    </td>
                </tr>
                <tr>
                    <td>菜单图标</td>
                    <td>
                        <input type="text" name="icon"/>
                    </td>
                </tr>
                <tr>
                    <td>排序</td>
                    <td>
                        <input name="seq" value="0" class="easyui-numberspinner" required="required"
                               data-options="editable:false">
                    </td>
                </tr>
                <tr>
                    <td>是否展示</td>
                    <td>
                        <input type="radio" name="open" value="1"/>展开
                        <input type="radio" name="open" value="0"/>不展开
                    </td>
                </tr>
                <tr>
                    <td>上级资源</td>
                    <td>
                        <select id="parentResId" name="pid" style="width: 200px;"></select>
                    </td>
                </tr>
              <%--  <tr>
                    <td>备注</td>
                    <td>
                        <textarea rows="5" cols="25" name="description"></textarea>
                    </td>
                </tr>--%>

            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    var action = "resource";

    $("#list").treegrid({
        idField: "id",
        treeField: "name",
        url: "../../resource/listByPage",
        columns: [[
            {
                field: "id",
                title: "编号",
                width: 100,
                checkbox: true
            },
            {
                field: "name",
                title: "资源名称",
                width: 200,
            },
            {
                field: "grantKey",
                title: "授权码",
                width: 200,
            },
            {
                field: "pageUrl",
                title: "菜单访问路径",
                width: 200,
            },
            {
                field: "seq",
                title: "排序",
                width: 100,
            },
            {
                field: "resourceType",
                title: "资源类型",
                width: 80,
                formatter: function (value, row, index) {
                    if (value != null) {
                        return value == "0" ? "菜单" : "按钮";
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'open',
                title: '是否展开',
                width: 100,
                formatter: function (value, row, index) {
                    return value == 1? "展开" : "不展开";
                }
            },
            {
                field : '_parentId', title : '父资源ID', width : 120, hidden:true // 隐藏
            }



        ]],
        pagination: true,
        toolbar: "#toolbar",
        //设置每页显示的记录数
        pageSize: 200,
        //修改允许出现的页面大小
        pageList: [100, 200, 300, 400, 500]
    });


    //弹出录入窗口
    $("#addBtn").click(function () {
        //清空表单数据
        $("#editForm").form("clear");

        //加载父资源列表
        $("#parentResId").combobox({
            url: "../../resource/list",
            valueField: "id",
            textField: "name"
        });

        //弹出window
        $("#editWin").window("open");
    });

    //保存表单数据
    $("#save").click(function () {
        //表单的提交
        $("#editForm").form("submit", {
            //提交后台的url
            url: "../../" + action + "/save",
            //提交之前的回调函数  该函数需要返回布尔值，true：提交表单  false:不提交表单
            onSubmit: function () {
                //判断表单如果全部验证通过，则提交表单；否则，则不提交
                let isture = $("#editForm").form("validate");
                if (!isture){
                    alert("参数不正确请从新输入");
                    return;
                }
                return isture;
            },
            //提交成功后的回调函数
            success: function (data) { //data:后台返回的数据  注意：data：是一个字符串类型
                //成功：{success:true}
                //失败：{success:false,msg:'错误信息'}
                //alert(data);
                //把data字符串转换为json对象     var 对象  = eval(字符串)
                data = eval("(" + data + ")");

                if (data.code==200) {
                    //成功
                    //刷新datagrid
                    $("#list").treegrid("reload");
                    //关闭window
                    $("#editWin").window("close");

                    $.messager.alert('提示', '保存成功！', 'info');
                } else {
                    //失败
                    $.messager.alert('提示', '保存失败！' + data.msg, 'error');
                }
            }
        });
    });

    //回显表单数据
    $("#editBtn").click(function () {
        //判断只能选择一行数据
        var rows = $("#list").treegrid("getSelections");
        if (rows.length != 1) {
            $.messager.alert("提示", "修改操作只能选择一行", "warning");
            return;
        }

        //先清空表单
        $("#editForm").form("clear");

        //动态数据填充   url必须返回一个json格式的数据 {name:xxx,minWeight:xx....}
        $("#editForm").form("load", "../../" + action + "/findById?uuid=" + rows[0].id);

        //回显父资源列表
        //加载父资源列表
        $("#parentResId").combobox({
            url: "../../resource/list",
            valueField: "id",
            textField: "name"
        });
        $("#parentResId").combobox("setValue", rows[0]._parentId);


        //弹出window
        $("#editWin").window("open");
    });

    //批量删除
    $("#deleteBtn").click(function () {
        //判断至少选择一行
        var rows = $("#list").treegrid("getSelections");
        if (rows.length == 0) {
            $.messager.alert("提示", "删除操作至少选择一行", "warning");
            return;
        }

        //提示用户确认是否删除
        $.messager.confirm("提示", "确认删除记录吗？一旦删除不能恢复啦!", function (value) {
            if (value) {
                //点击确定
                var ids = ""; // 格式： 1,2,3
                //获取选中的行的id
                //遍历rows   $(rows) : rows变量是js对象，each()是jQuery对象的方法      $(rows) ：把js对象转换为jQuery对象
                var idArray = new Array();
                $(rows).each(function (i) {
                    idArray.push(rows[i].id);
                });
                //idArray: [1 2 3]
                //join():把数组里面的每个元素逐个取出，以某个字符进行拼接，然后最终一个返回字符串
                ids = idArray.join(",");
                //提交到后台去处理
                $.post("../../" + action + "/delete", {ids: ids}, function (data) {

                    if (data.code==200) {
                        //刷新datagrid
                        $("#list").treegrid("reload");

                        $.messager.alert('提示', '删除成功！', 'info');
                    } else {
                        $.messager.alert('提示', '删除失败！' + data.msg, 'error');
                    }

                }, "json");
            }
        });

    });


</script>
</body>
</html>