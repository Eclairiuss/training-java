<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="css/font-awesome.css" rel="stylesheet" media="screen">
    <link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="js/computerCheck.js"></script>

<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="./"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="row">
            <div class="col-xs-8 col-xs-offset-2 box">
                <h1>Add Computer</h1>
                <form action="./add_computer" method="POST" id="addForm">
                    <fieldset>
                        <div class="form-group">
                            <label for="computerName">Computer name</label>
                            <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name">
                        </div>
                        <div class="form-group">
                            <label>Introduced date</label>
                            <input type="text" class="form-control" id="datepicker1" name="introduced" placeholder="Discontinued date">
                        </div>
                        <div class="form-group">
                            <label>Discontinued date</label>
                            <input type="text" class="form-control" id="datepicker2" name="discontinued" placeholder="Discontinued date">
                        </div>
                        <div class="form-group">
                            <label for="companyId">Company</label>
                            <select class="form-control" id="companyId" name="companyId">
                                <option value="0" selected>--</option>
                                <c:forEach var="company" items="${companies}">
                                    <option value="${company.getId()}">${company.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </fieldset>
                    <div class="actions pull-right">
                        <input type="submit" id="validate" value="Add" class="btn btn-primary">
                        or
                        <a href="./" id="cancel" class="btn btn-default">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
</body>
</html>