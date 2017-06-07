<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet" media="screen">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet" media="screen">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>


<body>
<mylib:header redirection="./edit_computer" id="${formComputer.getId()}" search="" order="" size="" page=""/>

<spring:message var="holder_name" code="computer.nameSpecial" text="default text" />
<spring:message var="holder_intro" code="computer.introducedSpecial" text="default text" />
<spring:message var="holder_disco" code="computer.discontinuedSpecial" text="default text" />
<c:set var="valueIntroduced" value=""/>
<c:set var="valueDiscontinued" value=""/>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <div class="label label-default pull-right">id:${formComputer.getId()}</div>
                <h1><spring:message code="edit.full" text="default text" /></h1>
                <form:form action="./edit_computer" method="POST" id="addForm" modelAttribute="formComputer">
                    <input path="id" type="hidden" value="${computer.getId()}" name="id" id="id"/>
                    <%@ include file="computer_form.jsp" %>
                    <div class="actions pull-right">
                        <input type="submit" id="validate" value="<spring:message code="edit.simple" text="default text" />" class="btn btn-primary">
                        <spring:message code="or"/>
                        <a href="./dashboard" id="cancel" class="btn btn-default"><spring:message code="cancel" text="default text" /></a>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</section>
<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap-datepicker.js"/>"></script>
<script src="<c:url value="/static/js/bootstrapValidator.min.js"/>"></script>
<script src="<c:url value="/static/js/computerCheck.js"/>"></script>
</body>
</html>