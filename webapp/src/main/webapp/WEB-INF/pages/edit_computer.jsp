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
<mylib:header redirection="./edit_computer" id="${computer.getId()}" search="" order="" size="" page=""/>

<spring:message var="holder_name" code="computer.nameSpecial" text="default text" />
<spring:message var="holder_intro" code="computer.introducedSpecial" text="default text" />
<spring:message var="holder_disco" code="computer.discontinuedSpecial" text="default text" />
<c:set var="valueIntroduced" value=""/>
<c:set var="valueDiscontinued" value=""/>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <div class="label label-default pull-right">id:${computer.getId()}</div>
                <h1><spring:message code="edit.full" text="default text" /></h1>
                <form:form action="./edit_computer" method="POST" id="addForm" modelAttribute="formComputer">
                    <form:errors cssClass="text-danger alert alert-danger" element="div"/>
                    <input path="id" type="hidden" value="${computer.getId()}" name="id" id="id"/>
                    <fieldset>
                        <div class="form-group">
                            <form:label path="name" for="name"><spring:message code="computer.name" text="default text"/></form:label>
                            <form:input path="name" type="text" class="form-control" id="name" name="name" value="${computer.getName()}" acceptCharset="true" placeholder="${holder_name}"/>
                            <form:errors path="name" cssClass="text-danger" element="div"/>
                        </div>
                        <div class="form-group">
                            <form:label path="introduced" for="introduced"><spring:message code="computer.introduced" text="default text" /></form:label>
                            <c:if test="${computer.getIntroduced() != null}">
                                <c:set var="valueIntroduced" value="${computer.getIntroduced()}"/>
                            </c:if>
                            <form:input path="introduced" type="text" class="form-control" id="introduced" name="introduced" acceptCharset="true" value="${valueIntroduced}" placeholder="${holder_intro}"/>
                            <form:errors path="introduced" cssClass="text-danger" element="div"/>
                        </div>
                        <div class="form-group">
                            <form:label path="discontinued" for="discontinued"><spring:message code="computer.discontinued" text="default text" /></form:label>
                            <c:if test="${computer.getDiscontinued() != null}">
                                <c:set var="valueDiscontinued" value="${computer.getDiscontinued()}"/>
                            </c:if>
                            <form:input path="discontinued" type="text" class="form-control" id="discontinued" name="discontinued" acceptCharset="true" value="${valueDiscontinued}" placeholder="${holder_disco}"/>
                            <form:errors path="discontinued" cssClass="text-danger" element="div"/>
                        </div>
                        <div class="form-group">
                            <form:label path="companyId" for="companyId"><spring:message code="computer.company" text="default text" /></form:label>
                            <form:select path="companyId" class="form-control" id="companyId" name="companyId">
                                <option value="0" <c:if test="${computer.getCompanyId() == null}">selected</c:if>">--</option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.getId()}" <c:if test="${company.getId().equals(computer.getCompanyId())}">selected</c:if> >${company.getName()}</option>
                                </c:forEach>
                            </form:select>
                            <form:errors path="companyId" cssClass="text-danger"/>
                        </div>
                    </fieldset>
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