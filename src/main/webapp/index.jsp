<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
function change(lg){
	document.cookie="language="+lg;
	location.href='change?language='+lg
}

</script>
</head>
<body>

<spring:message code="index.user.name" text="Nothing"></spring:message>
<spring:message code="index.user.desc"></spring:message>
<br/>
    <a onclick="change('zh_CN')" href="javascript:void(0);">中文</a>
    <a onclick="change('en')" href="javascript:void(0);">英文</a>
    <br>
    
    <br>
    master 主干创建    

    v2017.03.11 新git命令行创建分并提交～～～～～
</body>
</html>
