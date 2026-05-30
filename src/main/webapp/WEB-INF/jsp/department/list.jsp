<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Departments</title></head>
<body>
<h1>Departments</h1>
<a href="${pageContext.request.contextPath}/departments/new">Add Department</a>
&nbsp;|&nbsp;
<a href="${pageContext.request.contextPath}/patients">All Patients</a>

<table border="1" cellpadding="6">
    <tr>
        <th>Name</th>
        <th>Patients</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="dept" items="${departments}">
        <tr>
            <td><a href="${pageContext.request.contextPath}/departments/${dept.id}">${dept.name}</a></td>
            <td>${dept.patientCount}</td>
            <td>
                <a href="${pageContext.request.contextPath}/departments/${dept.id}/edit">Edit</a>
                &nbsp;
                <form method="post"
                      action="${pageContext.request.contextPath}/departments/${dept.id}/delete"
                      style="display:inline">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
