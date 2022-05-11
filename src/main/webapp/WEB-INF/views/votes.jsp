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
    <script src="<%request.getContextPath();%>js/jquery-3.6.0.min.js"></script>
    <script src="<%request.getContextPath();%>js/utilities.js"></script>
    <script src="<%request.getContextPath();%>js/crudFunctions.js"></script>
    <script src="<%request.getContextPath();%>js/constants.js"></script>
    <script src="<%request.getContextPath();%>js/votes.js"></script>
</head>
<body>
<%@include file="navBar.jsp" %>
<div>
    <h1>Votes</h1>
    <div>
        <table>
            <tbody>
            <tr>
                <td>Officer</td>
                <td>${votesData.user.name}</td>
            </tr>
            <c:if test="${votesData.location != null}">
                <tr>
                    <td>Location name</td>
                    <td>${votesData.location.name}</td>
                </tr>
                <tr>
                    <td>Location type</td>
                    <td>
                        <c:if test="${votesData.location.type == 0}">Polling Station</c:if>
                        <c:if test="${votesData.location.type == 1}">District Center</c:if>
                    </td>
                </tr>
            </c:if>
            <c:if test="${votesData.location == null}">
                <tr>
                    <td>Location</td>
                    <td>No location assigned</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
    <div>
        <c:if test="${votesData.location != null}">
            <table>
                <thead>
                <tr>
                    <th>Party id</th>
                    <th>Party name</th>
                    <th>Votes</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="party" items="${votesData.parties}">
                    <tr>
                        <td>${party.id}</td>
                        <td>${party.name}</td>
                        <td><input type="number" value="${party.votes}" id="votes-amount"></td>
                        <td><input type="button" value="Update" onclick="updateVote(this.parentNode.parentNode, ${party.id}, ${votesData.location.id})"></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${votesData.location == null}">
            <p>Please request the admin to assign you a location</p>
        </c:if>
    </div>
</div>
</body>
</html>
