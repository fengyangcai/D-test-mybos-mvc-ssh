<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>添加宣传任务</title>
    <script type="text/javascript" src="../../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="../../js/date.js"></script>
    <link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/easyui/locale/easyui-lang-zh_CN.js"></script>

    <!-- 导入KindEditor资源 -->
    <script type="text/javascript" src="../../js/editor/kindeditor-min.js"></script>
    <script type="text/javascript" src="../../js/editor/lang/zh_CN.js"></script>

</head>
<body>
<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
    <div class="datagrid-toolbar">
        <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
        <a id="back" icon="icon-back" href="#" class="easyui-linkbutton" plain="true">返回列表</a>
    </div>
</div>
<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="promotionForm" enctype="multipart/form-data"
          action="../../promotion/savePromotion" method="post">
        <%--隐藏域--%>
        <input type="hidden" name="id" id="id">
        <table class="table-edit" width="95%" align="center">
            <tr class="title">
                <td colspan="4">宣传任务</td>
            </tr>
            <tr>
                <td>宣传概要(标题):</td>
                <td colspan="3">
                    <input type="text" name="title" id="title" class="easyui-validatebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>活动范围:</td>
                <td>
                    <input type="text" name="activeScope" id="activeScope" class="easyui-validatebox"/>
                </td>
                <td>宣传图片:</td>
                <td>
                    <input type="file" name="titleImgFile" id="titleImgFile" class="easyui-validatebox" required="true"/>
                    <img id="imgShow" style="width: 80px;height: 80px;"/>
                </td>
            </tr>
            <tr>
                <td>发布时间:</td>
                <td>
                    <input type="text" name="startDate" id="startDate" class="easyui-datebox" required="true"/>
                </td>
                <td>失效时间:</td>
                <td>
                    <input type="text" name="endDate" id="endDate" class="easyui-datebox" required="true"/>
                </td>
            </tr>
            <tr>
                <td>宣传内容(活动描述信息):</td>
                <td colspan="3">
                    <textarea id="description" name="description" style="width:100%" rows="20"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>

<script type="text/javascript">
    $(function () {
        $("#back").click(function () {
            window.location.href = "promotion.jsp";
        });

        //加载编辑器
        KindEditor.ready(function (K) {
            window.editor = K.create('#description', {
                //修改文件上传的地址
                uploadJson: "../../image/uploadImage",
                //开启图片空间
                allowFileManager: true,
                //图片空间的查询地址
                fileManagerJson: "../../image/manager"
            });
        });
        //保存促销信息
        $("#save").click(function () {
            //alert("保存操作");
           var filename=$("#titleImgFile").val();
           var id=$("#id").val();
           //先判断是否有这个id存在了的。如果有就不再进行判断文件是否存在了
           if (id==null||id==""){
                if (filename==null ||filename==""){
                    $.messager.alert("提示","添加时必须选择图片");
                    return;
                }
           }
           //将编辑的内容同步到textarea
            editor.sync();

           //提交表单
           $("#promotionForm").submit();

           //成功后跳转到promotioon.jsp
            alert("保存成功");

            //这里使用窗口跳转会导致发送失败。
           window.location.href="promotion.jsp";

        });

        //接收id，查询数据，回显表单数据
        var id="${param.id}";
        //alert("参数为："+id);
        if (id!=null&&id!=""){
            //回显表单
            $.post("../../promotion/findById",{uuid:id},function (data) {
                //标题
                $("#title").val(data.title);
                $("#activeScope").val(data.activeScope);
                $("#titleImgFile").val(data.titleImgFile);
                //$("#startDate").val(data.startDate);
                $("#startDate").datebox('setValue',dateFormat(data.startDate));
                $("#endDate").datebox('setValue', dateFormat(data.endDate));
                //回显图片内容
                $("#imgShow").prop("src",data.titleImg)
                //回显内容(给KindEditor的编辑器赋值)
                //$("#description").val(data.description);
                window.editor.html(data.description);
                //填充id的值
                $("#id").val(data.id);
            },"json");
        }

    });

</script>
</body>
</html>