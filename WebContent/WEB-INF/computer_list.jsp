<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: ebiz
  Date: 18/04/17
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="computer" items="${computers}">
    <h1>${computer.id}</h1>
    <h2>${computer.name}</h2>
    <h3>${computer.introduced}</h3>
    <h4>${computer.discontinued}</h4>
    <h5>${computer.company}</h5>
</c:forEach>
</body>
</html>
