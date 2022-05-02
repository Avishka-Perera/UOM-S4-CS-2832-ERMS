<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 28/04/2022
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Votes</title>
</head>
<body>
<%@include file="navBar.jsp" %>
<div>
    <h1>Votes</h1>
    <div>
        <table>
            <tbody>
            <tr>
                <td>Location name</td>
                <td>${votesData.location.name}</td>
            </tr>
            <tr>
                <td>Location type</td>
                <td>${votesData.location.type}</td>
            </tr>
            <tr>
                <td>Officer</td>
                <td>${votesData.user.name}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div>
        <table>
            <thead>
            <tr>
                <th>Party name</th>
                <th>Votes</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="party" items="${votesData.parties}">
                    <tr>
                        <td>${party.name}</td>
                        <td><input type="number" value="${party.votes}"></td>
                        <td><input type="button" value="Update" onclick=""></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
