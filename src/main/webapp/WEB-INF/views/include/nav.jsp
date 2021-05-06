<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#nav{
		
		list-style:none;
		 margin:0;
    	padding:0;
	}
	#nav li{
	block: inline-block;
    margin: 0 0 0 15px;
    padding: 0 0 0 10px;
    border : 0;
    float: left;
    }
 	#nav li a:hover{
 		
 		color:black;
 		text-decoration:none;
 	}
 	#nav li a{
 		color:black;
 	}
}
</style>
<script>
</script>
</head>
<body>
	<ul id="nav">
		<li><a href="#"><i class="fa fa-list" aria-hidden="true"></i></a></li> &nbsp;
		<li><a href="#">Best 10</a></li>
		<li><a href="#">NEW 10%</a></li>
		<li><a href="#">1 + 1</a></li>
		<li><a href="#">TOP</a></li>
		<li><a href="#">OUTER</a></li>
		<li><a href="#">PANTS</a></li>
	</ul>
</body>
</html>