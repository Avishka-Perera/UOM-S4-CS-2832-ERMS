<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 27/04/2022
  Time: 08:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Vote Management</title>
    <script src="<%request.getContextPath();%>js/jquery-3.6.0.min.js"></script>
    <script src="<%request.getContextPath();%>js/utilities.js"></script>
    <script src="<%request.getContextPath();%>js/crudFunctions.js"></script>
    <script src="<%request.getContextPath();%>js/constants.js"></script>
    <script src="<%request.getContextPath();%>js/locations.js"></script>
</head>
<body>
<div>
    <%@include file="navBar.jsp" %>
    <div>
        <h1>Vote Management</h1>
        <div>
            <h2>Add new location</h2>
            <form id="addLocationForm">
                <table>
                    <tbody>
                    <tr>
                        <td><label for="name">Name</label></td>
                        <td><input type="text" id="name" name="name"></td>
                    </tr>
                    <tr>
                        <td><label for="userLevels">Type</label></td>
                        <td id="userLevels">
                            <span>
                                <input type="radio" id="polling-station" name="type" value="0" checked>
                                <label for="polling-station">Polling Station</label><br>
                            </span>
                            <span>
                                <input type="radio" id="district-center" name="type" value="1">
                                <label for="district-center">District Center</label><br>
                            </span>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <input type="button" onclick="addLocation(event)" value="Add"/>
            </form>
        </div>
        <div>
            <h2>Available locations</h2>
            <table>
                <thead>
                <tr>
                    <td>ID</td>
                    <td>Name</td>
                    <td>Station User</td>
                    <td>District User</td>
                    <td>Type</td>
                    <td>Action</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${locations}" var="location">
                    <tr>
                        <td id="tdId"><c:out value="${location.id}"/></td>
                        <td><c:out value="${location.name}"/></td>
                        <td><c:out value="${location.stationUserId == null ? 'None' : location.stationUserId}"/></td>
                        <td><c:out value="${location.districtCenterUserId == null ? 'None' : location.districtCenterUserId}"/></td>
                        <td>
                            <select name="type">
                                <option value="0" <c:if test="${location.type==0}">selected</c:if>>Polling Station</option>
                                <option value="1" <c:if test="${location.type==1}">selected</c:if>>District Center</option>
                            </select>
                        </td>
                        <td><button onclick="updateLocation(this.parentNode.parentNode)">Update</button><button onclick="deleteLocation(this.parentNode.parentNode)">Delete</button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <h2>Add new party</h2>
            <form action="add-location" method="post">
                <table>
                    <tbody>
                    <tr>
                        <td><label for="partyName">Name</label></td>
                        <td><input type="text" id="partyName" name="partyName"></td>
                    </tr>
                    </tbody>
                </table>
                <input type="submit" value="Add">
            </form>
        </div>
    </div>
</div>
</body>
</html>
