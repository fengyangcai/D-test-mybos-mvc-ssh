<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>分区管理</title>
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
    起始号：<input type="text" name="startNum"/>
    终止号：<input type="text" name="endNum"/>
    分区关键词：<input type="text" name="keyWords"/>
    分区辅助关键词：<input type="text" name="assistKeyWords"/>
    <a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
</form>

<!-- 工具条 -->
<div id="toolbar">
    <a id="addBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">增加</a>
    <a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
    <a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
</div>

<!-- 列表展示 -->
<table id="list"></table>


<!-- 编辑表单 -->
<!-- 编辑表单 -->
<div id="editWin" class="easyui-window" data-options="title:'分区编辑',closed:true" style="width:700px;top:50px;left:200px">
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
                    <td>所属区域</td>
                    <td>
                        <select id="areaId" name="area.id" class="easyui-combobox"
                                data-options="url:'../../base/area/list',valueField:'id',textField:'distrcit'"
                                style="width: 200px;"></select>
                    </td>
                    <td>所属定区</td>
                    <td>
                        <select id="fixedAreaId" name="fixedarea.id" class="easyui-combobox"
                                data-options="url:'../../base/fixedArea/list',valueField:'id',textField:'fixedAreaName'"
                                style="width: 200px;"></select>
                    </td>
                </tr>
                <tr>
                    <td>起始号</td>
                    <td>
                        <input type="text" name="startNum" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>中止号</td>
                    <td>
                        <input type="text" name="endNum" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>关键词</td>
                    <td>
                        <input type="text" name="keyWords" class="easyui-validatebox" required="true"/>
                    </td>
                    <td>辅助关键词</td>
                    <td>
                        <input type="text" name="assitKeyWords" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    //当前模块的名称空间
    var action = "base/subArea";

    //当前模块展示列 shift+alt+A
    //列字段
    var columns = [[{
        field: 'id',
        checkbox: true,
    }, {
        field: 'area.province',
        title: '省',
        width: 120,
        align: 'center',
        formatter: function (value, row, index) {
            if (row.area != null && row.area.province != null) {
                return row.area.province;
            } else {
                return "";
            }
        }

    }, {
        field: 'city',
        title: '市',
        width: 120,
        align: 'center',
        formatter: function (value, row, index) { //row:这时row才有值
            if (row.area != null && row.area.city != null) {
                return row.area.city;
            } else {
                return "";
            }
        }

    }, {
        field: 'area',
        title: '区',
        width: 120,
        align: 'center',
        formatter: function (value, row, index) { //row:这时row才有值
           /* if (row.area != null && row.area.distrcit != null) {
                return row.distrcit;
            } else {*/
            if (value != null) {
                return value.distrcit;
            } else {
                return "没有选项";
            }
        }

    }, {
        field: 'fixedarea',
        title: '所属定区',
        width: 120,
        align: 'center',
        formatter: function (value, row, index) { //row:这时row才有值
            if (value != null) {
                return value.fixedAreaName;
            } else {
                return "";
            }
        }
    }, {
        field: 'startNum',
        title: '起始号',
        width: 100,
        align: 'center'
    }, {
        field: 'endNum',
        title: '终止号',
        width: 100,
        align: 'center'
    }, {
        field: 'keyWords',
        title: '关键字',
        width: 120,
        align: 'center'
    }, {
        field: 'assitKeyWords',
        title: '辅助关键字',
        width: 100,
        align: 'center'
    }]];


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

        $("#editForm").form("load", {
            id: selectRow[0].id,
            startNum: selectRow[0].startNum,
            endNum: selectRow[0].endNum,
            keyWords: selectRow[0].keyWords,
            //fixedarea: selectRow[0].fixedarea,
            assitKeyWords: selectRow[0].assitKeyWords
        })
       // $("#courierId").combobox("setValue", selectRow[0].courier.id);
        $("#editWin").window("open");
        $("#areaId").combobox("setValue",  selectRow[0].area.id);
        $("#fixedAreaId").combobox("setValue",  selectRow[0].fixedarea.id);

    })

</script>

</body>
</html>