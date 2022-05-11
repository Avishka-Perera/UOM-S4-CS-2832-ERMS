<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Public page</title>
</head>
<body>
<div>
    <%@include file="navBar.jsp" %>
    <h1>Public page</h1>
    <br/>
    This is the public page
    <table>
        <thead>
        <tr>
            <td>ID</td>
            <td>Name</td>
            <td>Votes</td>
            <td>Details</td>
        </tr>
        </thead>
        <tbody id="partiesTBody">
        <c:forEach items="${parties}" var="party">
            <tr>
                <td id="tdIdParty">${party.id}</td>
                <td>${party.name}</td>
                <td>${party.votes}</td>
                <td><button onclick="">BreakDown</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>