<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
function change(lg){
	document.cookie="language="+lg;
	location.href='change?language='+lg
}

function ajax_click() {
	$.get("ajax_result", function(data) {
		$("#htmls").html(data);
	});
}


function cors(){
    $.ajax({
        url: 'http://localhost:8090/user/login',
        type: 'POST',
        data: {'name':'admin','password':'123'},
        dataType:'json',
        //headers: {'X-Requested-With':'xml'},
        success: function(cb){
            console.log(cb)
        }
        //dataType: dataType
    });
}


function postJson(){
    $.ajax({
        url: 'postdata',
        type: 'POST',
        data: {'data1':'adm+in','data2':'123','data3':'ddd'},
        dataType:'json',
        success: function(cb){
            console.log(cb)
        }
        //dataType: dataType
    });
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
    
    
    <br/>
    <!-- 测试ajax非response Body的形式返回 -->
    <a onclick="ajax_click()" href="javascript:void(0);">ajax调用</a>
    <div id="htmls"></div>

    <a href="javascript:void(0);" id="cros-btn" onclick="cors()">测试跨域</a>

    <a href="javascript:void(0);" id="cros-btn" onclick="postJson()">测试参数编码</a>
</body>
</html>
