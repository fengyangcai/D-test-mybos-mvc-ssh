<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理系统 首页</title>
    <link href="favicon.ico" rel="icon" type="image/x-icon"/>
    <script type="text/javascript" src="js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
    <link id="easyuiTheme" rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css">

    <!-- 导入ztree的资源 -->
    <script type="text/javascript" src="js/ztree/js/jquery.ztree.all.min.js"></script>
    <link rel="stylesheet" type="text/css" href="js/ztree/css/metroStyle/metroStyle.css">

    <script type="text/javascript">
        $(function () {

            //构建树状菜单
            //1.设置全局参数
            var setting = {
                //设置树的数据参数
                data: {
                    //simpleData: 设置简单json数据参数
                    simpleData: {
                        //enable: 开启简单json
                        enable: true,
                        //修改pId的名称
                        pIdKey: "_parentId"
                    }
                },
                callback: {
                    //点击事件
                    onClick: treeClick
                }
            };

            //2.准备数据（数组）
            /* var zNodes = [
                 {treeId:1,name:"基础数据模块",open:true},
                 {treeId:11,name:"收派标准",parentId:1,url:"http://www.baidu.com"},
                 {treeId:12,name:"快递员管理",parentId:1,icon:"js/ztree/css/zTreeStyle/img/diy/6.png"},
                 {treeId:2,name:"系统管理"},
                 {treeId:21,name:"用户管理",parentId:2,pageUrl:"http://www.baidu.com"},
                 {treeId:22,name:"角色管理",parentId:2},
            ]; */

            //异步抓取
            //$.post("data/menu.json", function (zNodes) {
            $.post("user/findMyMenus", function (zNodes) {
                //3.初始化树
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            }, "json");


            // 页面加载后 右下角 弹出窗口
            window.setTimeout(function () {
                $.messager.show({
                    title: "消息提示",
                    msg: '欢迎登录，${sessionScope.user.username}！ <a href="javascript:void" onclick="top.showAbout();">联系管理员</a>',
                    timeout: 5000
                });
            }, 3000);

            $("#btnCancel").click(function () {
                $('#editPwdWindow').window('close');
            });

            $("#btnEp").click(function () {
                alert("修改密码");
            });

            // 设置全局变量 保存当前正在右键的tabs 标题
            var currentRightTitle;
        });

        /*******顶部特效 *******/
        /**
         * 更换EasyUI主题的方法
         * @param themeName
         * 主题名称
         */
        changeTheme = function (themeName) {
            var $easyuiTheme = $('#easyuiTheme');
            var url = $easyuiTheme.attr('href');
            var href = url.substring(0, url.indexOf('themes')) + 'themes/'
                + themeName + '/easyui.css';
            $easyuiTheme.attr('href', href);
            var $iframe = $('iframe');
            if ($iframe.length > 0) {
                for (var i = 0; i < $iframe.length; i++) {
                    var ifr = $iframe[i];
                    $(ifr).contents().find('#easyuiTheme').attr('href', href);
                }
            }
        };

        // 退出登录
        function logoutFun() {
            $.messager
                .confirm('系统提示', '您确定要退出本次登录吗?', function (isConfirm) {
                    if (isConfirm) {
                        location.href = 'user/logout';
                    }
                });
        }

        // 修改密码
        function editPassword() {
            $('#editPwdWindow').window('open');
        }

        // 版权信息
        function showAbout() {
            $.messager.alert("bos v2.0综合物流管理平台", "设计: FYC<br/> 管理员邮箱: hhhhhhhh.com <br/>");
        }

        //树节点的点击事件
        /*
        event: 事件对象
        treeId： 树元素的ul元素的id
        treeNode: 当前点击的树节点的对象
        */
        function treeClick(event, treeId, treeNode) {
            //alert("点击了树")
            //alert(treeId);
            //alert(treeNode.treeId+"-"+treeNode.name+"-"+treeNode.parentId);

            /* var url = treeNode.pageUrl;
            window.location.href=url; */

            var name = treeNode.name;
            //获取节点pageUrl属性
            var url = treeNode.pageUrl;

            if (url != null) {
                //判断该面板是否已经存在
                if ($("#tabs").tabs("exists", name)) {
                    //选择面板
                    $("#tabs").tabs("select", name);
                } else {
                    //给选项卡添加面板
                    $("#tabs").tabs("add", {
                        title: name,
                        //content: 直接把内容写在面板上面,注意:可以写任何的html标签（包括iframe）
                        //href: 抓取url把内容写面板上面,注意：只会把url的body里面的内容加载进来
                        content: "<iframe src='" + url + "' style='width:100%;height:100%' frameborder='0'></iframe>",
                        //显示关闭按钮
                        closable: true
                    });
                }
            }
        }
    </script>
    <script type="text/javascript">
        $(function () {
            //获取本地的ip地址的方法
            var url = "http://pv.sohu.com/cityjson?ie=utf-8";
            var city, ip;
            $.ajax({
                url: url,
                dataType: "script",
                success: function(){
                    city=returnCitySN["cname"];
                    ip=returnCitySN["cip"];
                    console.log(city+ip);
                    document.getElementById("myip").innerText=ip;
                }
            });
        })
    </script>
</head>

<body class="easyui-layout">
<!-- 北边：顶部区域 -->
<div data-options="region:'north',border:false" style="height:70px;padding:10px;">
    <div>
        <img src="./images/logo.png" border="0">
    </div>
    <div id="sessionInfoDiv" style="position: absolute;right: 5px;top:10px;">
        [<strong>${sessionScope.user.username}</strong>]，欢迎你！您使用[<strong id="myip"></strong>]IP登录！
    </div>
    <div style="position: absolute; right: 5px; bottom: 10px; ">
        <a href="javascript:void(0);" class="easyui-menubutton"
           data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
        <a href="javascript:void(0);" class="easyui-menubutton"
           data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
    </div>
    <div id="layout_north_pfMenu" style="width: 120px; display: none;">
        <div onclick="changeTheme('default');">default</div>
        <div onclick="changeTheme('gray');">gray</div>
        <div onclick="changeTheme('black');">black</div>
        <div onclick="changeTheme('bootstrap');">bootstrap</div>
        <div onclick="changeTheme('metro');">metro</div>
    </div>
    <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
        <div onclick="editPassword();">修改密码</div>
        <div onclick="showAbout();">联系管理员</div>
        <div class="menu-sep"></div>
        <div onclick="logoutFun();">退出系统</div>
    </div>
</div>

<!-- 西部：左边菜单导航 -->
<div data-options="region:'west',split:true,title:'菜单导航'" style="width:200px">
    <!-- 加载菜单树 -->
    <ul id="treeDemo" class="ztree"></ul>
</div>

<!-- 中间： 编辑区域 -->
<div data-options="region:'center',split:true">
    <div id="tabs" fit="true" class="easyui-tabs" border="false">
        <div title="消息中心" id="subWarp" style="width:100%;height:100%;overflow:hidden">
            <iframe src="message.jsp" style="width:100%;height: 100%" frameborder='0'></iframe>
        </div>
    </div>
</div>

<!-- 南边：底部版权 -->
<div data-options="region:'south',border:false" style="height:50px;padding:10px;">
    <table style="width: 100%;">
        <tbody>
        <tr>
            <td style="width: 400px;">
                <div style="color: #999; font-size: 8pt;">
                    2.0综合管理平台 | Powered by <a href="http://www.baidu.com/">fyc-baidu</a>
                </div>
            </td>
            <td style="width: *;" class="co1"><span id="online"
                                                    style="background: url(./images/online.png) no-repeat left;padding-left:18px;margin-left:3px;font-size:8pt;color:#005590;">在线人数:1</span>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<!--修改密码窗口-->
<div id="editPwdWindow" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" modal="true"
     closed="true" resizable="false" maximizable="false" icon="icon-save" style="width: 300px; height: 160px; padding: 5px;
        background: #fafafa">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
            <table cellpadding=3>
                <tr>
                    <td>新密码：</td>
                    <td>
                        <input id="txtNewPass" type="Password" class="txt01"/>
                    </td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td>
                        <input id="txtRePass" type="Password" class="txt01"/>
                    </td>
                </tr>
            </table>
        </div>
        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
            <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)">确定</a>
            <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
        </div>
    </div>
</div>

<div id="mm" class="easyui-menu" style="width:120px;">
    <div data-options="name:'Close'">关闭当前窗口</div>
    <div data-options="name:'CloseOthers'">关闭其它窗口</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-cancel',name:'CloseAll'">关闭全部窗口</div>
</div>

</body>

</html>