<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>Patients</title></head>
<body>
<h1>Patients</h1>
<a href="${pageContext.request.contextPath}/patients/new">Add Patient</a>
&nbsp;|&nbsp;
<a href="${pageContext.request.contextPath}/departments">Departments</a>

<table border="1" cellpadding="6">
    <tr>
        <th>Full Name</th>
        <th>Age</th>
        <th>Gender</th>
        <th>Department</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="patient" items="${patients}">
        <tr>
            <td>${patient.fullName}</td>
            <td>${patient.age}</td>
            <td>${patient.gender.displayName}</td>
            <td>
                <a href="${pageContext.request.contextPath}/departments/${patient.departmentId}">
                    ${patient.departmentName}
                </a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/patients/${patient.id}/edit">Edit</a>
                &nbsp;
                <form method="post"
                      action="${pageContext.request.contextPath}/patients/${patient.id}/delete"
                      style="display:inline">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
