<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet" >
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet" >
    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet" >
</head>

<body>
<mylib:header redirection="./login" page="" size="" order="" search="" id=""/>

<section id="main">
    <div class="container">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code="login.login"/></h3>
                </div>
                <div class="panel-body">
                    <form method="POST" action="./login" name="f">
                        <fieldset>
                            <div class="form-group">
                                <label for="username"><spring:message code="login.username" text="default text"/></label>
                                <input type="text" name="username" class="form-control" id="username">
                            </div>
                            <div class="form-group">
                                <label for="password"><spring:message code="login.password" text="default text"/></label>
                                <input type="password" name="password" class="form-control" id="password">
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        </fieldset>
                        <div class="actions pull-right">
                            <input id="submit" type="submit" value="<spring:message code="login.login" text="default text" />"
                                   class="btn btn-sm btn-success">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<script src="<c:url value="/js/jquery.min.js" />"></script>
<script src="<c:url value="/js/bootstrap.min.js" />"></script>
</body>
</html>