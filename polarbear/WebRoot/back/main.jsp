<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../css/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../css/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../css/easyui/custom.css">
    <script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui/jquery.portal.js"></script>
    <script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/biz/main.portal.js"></script>
    <script type="text/javascript" src="../js/biz/main.tab.js"></script>
     <script type="text/javascript" src="../js/fastjson.js"></script>
    <title>管理控制台主界面</title>
    <style type="text/css">
        .t-list{
            padding:5px;
        }
    </style>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:60px;background:#777;padding:10px">north region</div>
    <div data-options="region:'west',split:true" title="菜单" style="width:200px;">
        <ul>
            <li class="t-list"><a href="javascript:void(0)" onclick="showcontent('用户管理')">用户管理</a></li>
            <li class="t-list"><a href="javascript:void(0)" onclick="showcontent('商品管理')">商品管理</a></li>
            <li class="t-list"><a href="javascript:void(0)" onclick="showcontent('订单管理')">订单管理</a></li>
        </ul>
    </div>
    <div data-options="region:'south',border:false" style="text-align:center;height:50px;background:#777;padding:10px;">south region</div>
    <div id="content" data-options="region:'center',title:''">
        <div id="tt" class="easyui-tabs" style="width:500px;height:250px;" fit="true">
            <div title="首页" data-options="iconCls:'icon-reload'">
                <div id="pp" style="position:relative">
                    <div style="width:30%;">
                        <div title="Clock" style="text-align:center;background:#f3eeaf;height:150px;padding:5px;">
                            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="100" height="100">
                              <param name="movie" value="http://www.respectsoft.com/onlineclock/analog.swf">
                              <param name=quality value=high>
                              <param name="wmode" value="transparent">
                              <embed src="http://www.respectsoft.com/onlineclock/analog.swf" width="100" height="100" quality=high pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash" type="application/x-shockwave-flash" wmode="transparent"></embed>
                            </object>
                        </div>
                        <div title="Tutorials" collapsible="true" closable="true" style="height:200px;padding:5px;">
                            <div class="t-list"><a href="http://www.jeasyui.com/tutorial/datagrid/datagrid1.php">Build border layout for Web Pages</a></div>
                            <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/panel.php">Complex layout on Panel</a></div>
                            <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/accordion.php">Create Accordion</a></div>
                            <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/tabs.php">Create Tabs</a></div>
                            <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/tabs2.php">Dynamically add tabs</a></div>
                            <div class="t-list"><a href="http://www.jeasyui.com/tutorial/layout/panel2.php">Create XP style left panel</a></div>
                        </div>
                    </div>
                    <div style="width:40%;">
                        <div id="pgrid" title="DataGrid" closable="true" style="height:200px;">
                            <table class="easyui-datagrid" style="width:650px;height:auto"
                                    fit="true" border="false"
                                    singleSelect="true"
                                    idField="itemid" url="datagrid_data1.json">
                                <thead>
                                    <tr>
                                        <th field="itemid" width="60">Item ID</th>
                                        <th field="productid" width="60">Product ID</th>
                                        <th field="listprice" width="80" align="right">List Price</th>
                                        <th field="unitcost" width="80" align="right">Unit Cost</th>
                                        <th field="attr1" width="120">Attribute</th>
                                        <th field="status" width="50" align="center">Status</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <div style="width:30%;">
                        <div title="Searching" iconCls="icon-search" closable="true" style="height:80px;padding:10px;">
                            <input class="easyui-searchbox">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>    
</body>
<script type="text/javascript">
    $(function(){
        initTabs ();
        initPortal ();
    });
    function showcontent(title) {
        // $('#content').html('Introduction to ' + title + ' language');
        if ($('#tt').tabs('exists',title)) {
            $('#tt').tabs('select',title);
            return;
        }
        addTab(title);
    }
</script>
</html>