<%--
  Created by IntelliJ IDEA.
  User: 68
  Date: 2020/7/24
  Time: 4:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="../../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <script type="text/javascript"
            src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <%--<script type="text/javascript" src="../../js/form.js"></script>--%>
    <script type="text/javascript" src="../../js/crud.js"></script>
    <script type="text/javascript" src="../../js/date.js"></script>
    <title>运单</title>

</head>
<body>
<!-- 搜索框 -->
<div class="datagrid-toolbar" style="height: 40px;">
    <form id="searchForm" method="post">
        标题:<input type="text" name="title; "/>
        <a id="search" class="easyui-linkbutton" href="#" icon="icon-search">查询</a>
    </form>
</div>

<!-- 列表 -->
<table id="list"></table>
<!-- 按钮列表 -->
<div id="toolbar">
    <a id="saveBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">添加</a>
    <a id="editBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
    <a id="deleteBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
    <script type="text/javascript">
        $(function(){
            $("#list").datagrid({
                url:"product.json",
                columns:[[
                    {
                        field:"id",
                        title:"商品编号",
                        width:100,
                        editor:{
                            //type:代表编辑框类型
                            type:"validatebox",
                            //options: 代表type编辑类型对应的属性
                            options:{
                                required:true
                            }
                        }

                    },
                    {
                        field:"name",
                        title:"商品名称",
                        width:200
                    },
                    {
                        field:"price",
                        title:"商品价格",
                        width:200
                    }
                ]],
                pagination:true
            });

        });
    </script>


</div>
</body>
</html>
