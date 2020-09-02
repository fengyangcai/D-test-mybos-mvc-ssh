//一加载就执行的代码
$(function () {


    //从后台获取列表
    $("#list").datagrid({
        // url:抓取数据的url
        url: "../../" + action + "/listByPage",
        //请求额外参数
        // queryParams:
        columns: columns,
        //开启分页
        pagination: true,
        fitColums: true,
        autoRowHeight: true,
        toolbar: "#toolbar",
        rownumbers: true
    });
    //搜索功能
    $("#searchBtn").click(function () {
        //一次性获取到表单的所有参数
        //serialize():　序列化表单的参数为字符串。格式：name=10&minWeight=10&minLength=20
        //alert(decodeURIComponent($("#searchForm").serialize()))
        //alert($("#searchForm").serialize())
        //把表单的参数转换为json对象格式，调用load方法
        $("#list").datagrid("load", getFormData("searchForm"));

        /*$("#list").datagrid({
            loader:function(
                getFormData("#searchForm"),success(data){
                this.columns=data;
            },error(){
                alert("查询失败");
            })
        })*/
    });
    //弹出录入窗口
    $("#addBtn").click(function () {
        //清空表单数据
        $("#editForm").form("clear");

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
                return $("#editForm").form("validate");
            },
            //提交成功后的回调函数
            success: function (data) { //data:后台返回的数据  注意：data：是一个字符串类型
                //成功：{success:true}
                //失败：{success:false,msg:'错误信息'}
                //alert(data);
                //把data字符串转换为json对象     var 对象  = eval(字符串)这个方法不安全不建议使用
                //data = eval("("+data+")");
                data = JSON.parse(data);
                if (data.code == 200) {
                    //成功
                    //刷新datagrid
                    $("#list").datagrid("reload");
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

  /*  //回显表单数据
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

    });*/
    //删除
    $("#deleteBtn").click(function () {
        //判断是否有选择到行
        var rows = $("#list").datagrid("getSelections");
        if (rows.length == 0) {
            $.messager.alert("提示", "删除操作至少选择一行", "warning");
            return
        }
        //出现友好提示是否删除啊
        $.messager.confirm("提示", "确认删除记录吗？一旦删除不能恢复啦!", function (value) {
            if (value) {
                //点击确定了
                var ids = "";
                var idArray = new Array();
                $(rows).each(function (i) {
                    idArray.push(rows[i].id);
                });
                ids = idArray.join(",");

                $.post("../../" + action + "/delete", {ids: ids}, function (data) {
                    if (data.code == 200) {
                        $("#list").datagrid("reload");
                        $.messager.alert('提示', '删除成功！', 'info');
                    } else {
                        $.messager.alert('提示', '删除失败！' + data.msg, 'error');
                    }
                }, "json");
            }
        })

    });
/*
    function loadEditForm(row) {

    }*/

    //把传统的参数格式 转换成json格式
    function conveterParamsToJson(paramsAndValues) {
        //name=xx&minWeight=xx&minLength=xx
        //1.创建json对象
        var jsonObj = {};
        //切割每个字符
        var arr = paramsAndValues.split("&");
        console.log(arr);
        //遍历每个数组的对象
        for (var i = 0; arr != null && i < arr.length; i++) {
            //再次切割key-value对
            var para = arr[i].split("=");
            //para[0]:属性名称
            //para[1]:属性值
            //把这些值赋予给json对象
            jsonObj[para[0]] = para[1];
        }
        return jsonObj;
    }

    /* 将表单数据封装为json
 * @param form
 * @returns*/
    function getFormData(form) {
        var formValues = $("#" + form).serialize();
        // decodeURIComponent(formValues);
        //name=133-40斤&minWeight=2&minLength=33

        return conveterParamsToJson(decodeURIComponent(formValues));
    }
})

