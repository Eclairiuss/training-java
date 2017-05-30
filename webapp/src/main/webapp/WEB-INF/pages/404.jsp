<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<c:url value="/static/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet" media="screen">
    <link href="<c:url value="/static/css/main.css"/>" rel="stylesheet" media="screen">
    <link href="css/main.css" rel="stylesheet" media="screen">
</head>


<body>
    <mylib:header redirection="./dashboard" id="0" search="${search}" order="${order}" size="${size}" page="${page}"/>
    <section id="main">
        <div class="container">
            <div class="alert alert-danger">
                Error 404: Page not found. Too bad bitch!
                <br/>
                <c:if test="${not empty message}">${message}</c:if>
                <!-- stacktrace -->
            </div>
        </div>
    </section>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/dashboard.js"></script>
</body>
</html>