<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
</head>
<body>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="./dashboard"> Application - Computer Database </a>
    </div>
</header>
<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <div class="label label-default pull-right">
                    id:${computer.getId()}
                </div>
                <h1>Edit Computer</h1>
                <form action="./edit_computer" method="POST" id="addForm" action="#">
                    <input type="hidden" value="${computer.getId()}" name="id" id="id"/>
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName">Computer name</label>
                            <input type="text" class="form-control" id="computerName" name="computerName" value="${computer.getName()}" placeholder="Computer name">
                        </div>
                        <div class="form-group">
                            <label for="introduced">Introduced date</label>
                            <input type="text" class="form-control" id="introduced" name="introduced" <c:if test="${computer.getIntroduced() != null}">value="${computer.getIntroduced()}"</c:if> placeholder="Introduced date">
                        </div>
                        <div class="form-group">
                            <label for="discontinued">Discontinued date</label>
                            <input type="text" class="form-control" id="discontinued" name="discontinued"<c:if test="${computer.getDiscontinued() != null}">value="${computer.getDiscontinued()}"</c:if> placeholder="Discontinued date">
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company</label>
                            <select class="form-control" id="companyId" name="companyId">
                                <option value="0" <c:if test="${computer.getCompanyId() == null}">selected</c:if>">--</option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.getId()}" <c:if test="${company.getId().equals(computer.getCompanyId())}">selected</c:if> >${company.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" id="validate" value="Edit" class="btn btn-primary">
                        or
                        <a href="./dashboard" id="cancel" class="btn btn-default">Cancel</a>
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