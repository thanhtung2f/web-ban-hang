<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset= UTF-8" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Index</title>
        <%String path = request.getContextPath();%>
        <link href="<%=path%>/css/style.css" rel="stylesheet"/>
    </head>
    <body>
        <div id="container">
            <div id="header">

                <div id="logo">
                    <a href="<c:url value="/home"><c:param name="req" value="home"/></c:url>"><img src="img/logo.png"/></a>
                    </div>
                    <div id="banner">
                    <c:out value="${adminAccount}"/>
                    <c:out value="${AccSession}"/>
                    <ul>
                        <c:if test="${not empty adminAccount}">
                            <li><a href="<c:url value="/Admin"><c:param name="req" value="listProduct"/></c:url>">Admin Manager</a></li>
                            <li><a href="<c:url value="/home"><c:param name="req" value="remove"/></c:url>">SignOut</a></li>
                        </c:if>
                        <c:if test="${empty adminAccount}">
                                <li>
                                    <a href="<c:url value="/Order"><c:param name="order" value="cart"/></c:url>">Cart: 
                                        <c:if test="${empty numberCart}"><c:out value="0"/></c:if>
                                        <c:if test="${not empty numberCart}"><c:out value="${numberCart}"/></c:if>
                                    </a>
                                </li>
                                
                            <c:if test="${empty AccSession}">
                                <li><a href="<%=path%>/SignIn">SignIn</a></li>
                                <li><a href="<%=path%>/SignUp">SignUp</a></li>
                            </c:if>
                                
                            <c:if test="${not empty AccSession}">
                                <li><a href="<c:url value="/home"><c:param name="req" value="profile"/></c:url>">Profile</a></li>
                                <li><a href="<c:url value="/home"><c:param name="req" value="remove"/></c:url>">SignOut</a></li>
                            </c:if>
                        </c:if>    
                    </ul>
                </div>
            </div>
            <div id="content">
