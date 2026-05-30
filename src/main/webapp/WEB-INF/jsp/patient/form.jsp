<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <c:choose>
            <c:when test="${not empty patient}">Edit Patient</c:when>
            <c:otherwise>New Patient</c:otherwise>
        </c:choose>
    </title>
</head>
<body>
<h1>
    <c:choose>
        <c:when test="${not empty patient}">Edit Patient</c:when>
        <c:otherwise>New Patient</c:otherwise>
    </c:choose>
</h1>

<form method="post" action="${pageContext.request.contextPath}/patients<c:if test="${not empty patient}">/${patient.id}</c:if>">
    <div>
        <label>Full Name: <input type="text" name="fullName" value="${patient.fullName}" required></label>
    </div>
    <div>
        <label>Age: <input type="number" name="age" value="${patient.age}" min="0" required></label>
    </div>
    <div>
        <label>Gender:
            <select name="gender">
                <c:forEach var="g" items="${genders}">
                    <option value="${g}" ${patient.gender == g ? 'selected' : ''}>${g.displayName}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <div>
        <label>Department:
            <select name="departmentId">
                <c:forEach var="dept" items="${departments}">
                    <option value="${dept.id}" ${patient.departmentId == dept.id ? 'selected' : ''}>${dept.name}</option>
                </c:forEach>
            </select>
        </label>
    </div>
    <br>
    <button type="submit">Save</button>
</form>
<br>
<a href="${pageContext.request.contextPath}/patients">Back to list</a>
</body>
</html>
