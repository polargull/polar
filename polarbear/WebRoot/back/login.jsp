<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>polarbear login</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/easyui/themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>/js/easyui/jquery.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/easyui/jquery.easyui.min.js"></script>
	</head>
	<body>
    <h2>Validate Form on Submit</h2>
    <p>The form does not perform validation before being submitted.</p>
    <div style="margin:20px 0;"></div>
    <div class="easyui-panel" title="New Topic" style="width:400px">
        <div style="padding:10px 60px 20px 60px">
        <form id="ff" class="easyui-form" method="post" data-options="novalidate:true">
            <table cellpadding="5">
                <tr>
                    <td>Name:</td>
                    <td><input class="easyui-textbox" type="text" name="name" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input class="easyui-textbox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
                </tr>
                <tr>
                    <td>Subject:</td>
                    <td><input class="easyui-textbox" type="text" name="subject" data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>Message:</td>
                    <td><input class="easyui-textbox" name="message" data-options="multiline:true" style="height:60px"></input></td>
                </tr>
                <tr>
                    <td>Language:</td>
                    <td>
                        <select class="easyui-combobox" name="language"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
                    </td>
                </tr>
            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Submit</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Clear</a>
        </div>
        </div>
    </div>
    <script>
        function submitForm(){
            $('#ff').form('submit',{
                onSubmit:function(){
                    return $(this).form('enableValidation').form('validate');
                }
            });
        }
        function clearForm(){
            $('#ff').form('clear');
        }
    </script>
</body>
</html>
