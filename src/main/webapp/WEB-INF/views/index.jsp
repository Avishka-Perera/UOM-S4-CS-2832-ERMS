<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Public page</title>
    <script src="<%request.getContextPath();%>js/jquery-3.6.0.min.js"></script>
    <script src="<%request.getContextPath();%>js/public.js"></script>
    <script>
        const locations = {
            <jsp:useBean id="locations" scope="request" type="java.util.List"/>
            <c:forEach items="${locations}" var="location">
            ${location.id}: "${location.name}",
            </c:forEach>
        };
        const parties = {
            <jsp:useBean id="parties" scope="request" type="java.util.List"/>
            <c:forEach items="${parties}" var="party">
                ${party.id}: {
                    name: "${party.name}",
                    votes: [
                        <c:forEach items="${party.votesList}" var="voteObj">
                        {
                            locationId: ${voteObj.locationId},
                            votes: ${voteObj.vote}
                        },
                        </c:forEach>
                    ]
                },
            </c:forEach>
        };
    </script>
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
            <th>Name</th>
            <th>Votes</th>
            <th>Details</th>
        </tr>
        </thead>
        <tbody id="partiesTBody">
        <c:forEach items="${parties}" var="party">
            <tr>
                <td>${party.name}</td>
                <td>${party.votes}</td>
                <td><button onclick="displayBreakDown(${party.id})">BreakDown</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        <h2 id="partyNameHeading"></h2>
        <table>
            <thead>
            <tr>
                <th>Location</th>
                <th>Votes</th>
            </tr>
            </thead>
            <tbody id="breakDownTbody">
            </tbody>
        </table>
        <button>Close</button>
    </div>
</div>
</body>
</html>