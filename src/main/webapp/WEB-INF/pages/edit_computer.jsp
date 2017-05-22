<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet" media="screen">
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet" media="screen">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/2.3.1/css/flag-icon.min.css" rel="stylesheet"/>
</head>
<body>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<mylib:header redirection="./edit_computer" language="${language}" id="${computer.getId()}" search="" order="" size="" page=""/>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <div class="label label-default pull-right">
                    id:${computer.getId()}
                </div>
                <h1><spring:message code="computer.editComputer" text="default text" /></h1>
                <form action="./edit_computer" method="POST" id="addForm" action="#">
                    <input type="hidden" value="${computer.getId()}" name="id" id="id"/>
                    <input type="hidden" name="language" value="${language}">
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName"><spring:message code="computer.name" text="default text"/></label>
                            <input type="text" class="form-control" id="computerName" name="computerName" value="${computer.getName()}" placeholder="<spring:message code="computer.name" text="default text" />">
                        </div>
                        <div class="form-group">
                            <label for="introduced"><spring:message code="computer.introduced" text="default text" /></label>
                            <input type="text" class="form-control" id="introduced" name="introduced" <c:if test="${computer.getIntroduced() != null}">value="${computer.getIntroduced()}"</c:if> placeholder="<spring:message code="computer.introduced" text="default text" />">
                        </div>
                        <div class="form-group">
                            <label for="discontinued"><spring:message code="computer.discontinued" text="default text" /></label>
                            <input type="text" class="form-control" id="discontinued" name="discontinued"<c:if test="${computer.getDiscontinued() != null}">value="${computer.getDiscontinued()}"</c:if> placeholder="<spring:message code="computer.discontinued" text="default text" />">
                        </div>
                        <div class="form-group">
                            <label for="companyId"><spring:message code="computer.company" text="default text" /></label>
                            <select class="form-control" id="companyId" name="companyId">
                                <option value="0" <c:if test="${computer.getCompanyId() == null}">selected</c:if>">--</option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.getId()}" <c:if test="${company.getId().equals(computer.getCompanyId())}">selected</c:if> >${company.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" id="validate" value="<spring:message code="computer.edit" text="default text" />" class="btn btn-primary">
                        or
                        <a href="./dashboard" id="cancel" class="btn btn-default"><spring:message code="computer.cancel" text="default text" /></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrapValidator.min.js"/>"></script>
<script src="<c:url value="/resources/js/computerCheck.js"/>"></script>
</body>
</html>