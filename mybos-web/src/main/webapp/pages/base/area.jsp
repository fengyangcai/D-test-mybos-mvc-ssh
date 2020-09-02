<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>区域管理</title>
    <script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/icon.css">
    <script type="text/javascript" src="../../js/crud.js"></script>
    <script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
    <!-- <script type="text/javascript" src="../../easyui/myjs/form.js"></script> -->


</head>
<body>
<!-- 搜索表单 -->
<form id="searchForm" action="../../base/area/excelExport" method="post">
    省份：<input type="text" name="province"/>
    城市：<input type="text" name="city"/>
    区（县）：<input type="text" name="district"/>
    <a id="searchBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
</form>
<div id="toolbar">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="addBtn">增加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="deleteBtn">删除</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="editBtn">修改</a>
    <a id="excelImportBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">Excel导入</a>
    <a id="excelExportBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">Excel导出</a>

</div>

<table id="list"></table>


<!--编辑窗口  -->
<div id="editWin" class="easyui-window" title="区域添加修改" style="width:700px;top:50px;left:200px"
     data-options="iconCls:'icon-save',modal:true" ,resizable="true" closed="true">
    <div>
        <form id="editForm" method="post">
            <!--添加一个id隐藏域 -->
            <input type="hidden" name="id"/>
            <table class="table-edit" width="80%" align="center">
                <tr>
                    <td>省</td>
                    <td>
                        <input type="text" name="province" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>市</td>
                    <td>
                        <input type="text" name="city" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>区</td>
                    <td>
                        <input type="text" name="distrcit" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>邮编</td>
                    <td>
                        <input type="text" name="postcode" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>简码</td>
                    <td>
                        <input type="text" name="shortcode" class="easyui-validatebox" required="true"/>
                    </td>
                </tr>
                <tr>
                    <td>城市编码</td>
                    <td>
                        <input type="text" name="citycode" class="easyui-validatebox" required="true"/>
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
<!-- 批量导入窗口 -->
<div id="importWin" class="easyui-window " data-options="title:'区域信息导入',closed:true"
     style="width: 400px; top: 50px; left: 200px">
    <!--进度条-->
    <div id="jindutiao" style="width:400px;"></div>
    <table class="table-edit" width="80%" align="center">

        <tr class="title">
            <td colspan="2">区域信息导入</td>
        </tr>
        <tr>
            <td>选择文件</td>
            <td><input id="fileID" type="file" name="areaFile" class="easyui-validatebox" required="true"/></td>
        </tr>
        <tr>
            <td colspan="2"><a id="startImport" href="#" class="easyui-linkbutton">开始导入</a></td>

        </tr>
    </table>
</div>

<script type="text/javascript">
    var action = "base/area";
    //当前模块的展示
    var columns = [[{
        field: 'id',
        checkbox: true,
    }, {
        field: 'province',
        title: '省',
        width: 120,
        align: 'center'
    }, {
        field: 'city',
        title: '市',
        width: 120,
        align: 'center'
    }, {
        field: 'distrcit',
        title: '区',
        width: 120,
        align: 'center'
    }, {
        field: 'postcode',
        title: '邮编',
        width: 120,
        align: 'center'
    }, {
        field: 'shortcode',
        title: '简码',
        width: 120,
        align: 'center'
    }, {
        field: 'citycode',
        title: '城市编码',
        width: 200,
        align: 'center'
    }]];


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
            //loadEditForm(rows[0]);
            //$("#standardId").combobox("setValue",rows[0].standard.name);
            //弹出window
            $("#editWin").window("open");

        });

        $("#excelImportBtn").click(function () {
            $("#importWin").window("open");
            //传送文件
            $("#startImport").click(function () {
                //先判断文件的格式是否正确
                var fileName = $("#fileID").val();
                var reg = /^.+\.(xls|xlsx)$/;
                if (!reg.test(fileName)) {
                    $.messager.alert("提示", "文件格式有误", "warning");
                    return
                }
                ;
                $('#jindutiao').progressbar({
                    value: 0
                });
                //使用异步文件上传
                $.ajaxFileUpload({
                    //url: 上传的url地址
                    url: "../../base/area/excelImport",
                    fileElementId: "fileID",
                    //dataType:指定服务器返回的数据类型
                    dataType: "json",
                    //success: 服务器成功返回的回调函数  200
                    success: function (data) {
                        //data:返回的数据，如果dataType:"json"，那么data是json对象类型
                        if (data.code == 200) {
                            $('#jindutiao').progressbar({value: 100});
                            //涮新页面
                            $("#list").datagrid("reload");
                            $.messager.alert("提示", "excel导入成功", "info");
                        } else {
                            $.messager.alert("提示", "excel导入失败：" + data.msg, "error");
                        }
                    },
                    //error:服务器返回失败    404 500
                    error: function (e) {
                        $.messager.alert("提示", e, "error");
                    }
                });


            })
        });

        //到处excel导出
        $("#excelExportBtn").click(function () {
            console.log("daochu");
            //提交搜索表单
            $("#searchForm").submit();
        })

    });


</script>


</body>
</html>
