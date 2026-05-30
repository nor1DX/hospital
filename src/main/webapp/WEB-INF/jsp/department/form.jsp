<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${not empty department}">Edit Department</c:when>
            <c:otherwise>New Department</c:otherwise>
        </c:choose>
    </title>
</head>
<body>
<h1>
    <c:choose>
        <c:when test="${not empty department}">Edit Department</c:when>
        <c:otherwise>New Department</c:otherwise>
    </c:choose>
</h1>

<form method="post" action="${pageContext.request.contextPath}/departments<c:if test="${not empty department}">/${department.id}</c:if>">
    <div>
        <label>Name: <input type="text" name="name" value="${department.name}" required></label>
    </div>
    <br>
    <button type="submit">Save</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/departments">Back to list</a>
</body>
</html>
