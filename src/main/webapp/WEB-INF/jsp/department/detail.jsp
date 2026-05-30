<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>${department.name}</title></head>
<body>
<h1>${department.name}</h1>
<p>Patients: ${department.patientCount}</p>

<a href="${pageContext.request.contextPath}/departments/${department.id}/edit">Edit</a>
&nbsp;
<form method="post"
      action="${pageContext.request.contextPath}/departments/${department.id}/delete"
      style="display:inline">
    <button type="submit">Delete</button>
</form>
&nbsp;|&nbsp;
<a href="${pageContext.request.contextPath}/departments">Back to list</a>

<h2>Patients</h2>
<a href="${pageContext.request.contextPath}/patients/new">Add Patient</a>

<table border="1" cellpadding="6">
    <tr>
        <th>Full Name</th>
        <th>Age</th>
        <th>Gender</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="patient" items="${patients}">
        <tr>
            <td>${patient.fullName}</td>
            <td>${patient.age}</td>
            <td>${patient.gender.displayName}</td>
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
