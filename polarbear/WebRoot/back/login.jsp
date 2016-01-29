<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>polarbear login</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/css/easyui/themes/icon.css">
		<script type="text/javascript"
			src="<%=basePath%>/js/easyui/jquery.min.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/easyui/jquery.easyui.min.js"></script>
		<style>
			.center_div {
				position: absolute;
				top: 40%;
				left: 50%;
				width: 500px;
				height: 300px;
				margin-top: -150px;
				margin-left: -250px;
			}
		</style>
	</head>
	<body style="margin: 0; padding: 0;">
		<div class="center_div">
			<div class="easyui-panel" title="登录控制台"
				style="width: 400px; padding: 30px 70px 20px 70px">
				<div style="margin-bottom: 10px">
					<input class="easyui-textbox"
						style="width: 100%; height: 40px; padding: 12px"
						data-options="prompt:'用户名',iconCls:'icon-man',iconWidth:38">
				</div>
				<div style="margin-bottom: 20px">
					<input class="easyui-textbox" type="password"
						style="width: 100%; height: 40px; padding: 12px"
						data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38">
				</div>
				<div style="margin-bottom: 20px">
					<input type="checkbox" checked="checked">
					<span>记住我</span>
				</div>
				<div>
					<a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-ok'"
						style="padding: 5px 0px; width: 100%;"> <span
						style="font-size: 14px;">登录</span> </a>
				</div>
			</div>
		</div>
		<script>
			function submitForm() {
				$('#ff').form('submit');
			}
			function clearForm() {
				$('#ff').form('clear');
			}
		</script>
	</body>
</html>
