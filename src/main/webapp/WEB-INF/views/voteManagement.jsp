<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 27/04/2022
  Time: 08:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Vote Management</title>
    <script src="<%request.getContextPath();%>js/jquery-3.6.0.min.js"></script>
    <script src="<%request.getContextPath();%>js/utilities.js"></script>
    <script src="<%request.getContextPath();%>js/crudFunctions.js"></script>
    <script src="<%request.getContextPath();%>js/constants.js"></script>
    <script src="<%request.getContextPath();%>js/locations.js"></script>
    <script src="<%request.getContextPath();%>js/parties.js"></script>
    <script>
        const districtUsers = [
            <c:forEach var="user" items="${districtUsers}">
                    {id: ${user.id},name: "${user.name}"},
            </c:forEach>
        ];
        const stationUsers = [
            <c:forEach var="user" items="${stationUsers}">
                    {id: ${user.id},name: "${user.name}"},
            </c:forEach>
        ];
    </script>
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
                <input type="button" onclick="addLocation()" value="Add"/>
            </form>
        </div>
        <div id="availableLocations">
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
                <tbody id="locationsTBody">
                <c:forEach items="${locations}" var="location">
                    <tr>
                        <td id="tdId"><c:out value="${location.id}"/></td>
                        <td><c:out value="${location.name}"/></td>
                        <td id="stationUserId">
                            <select name="stationUser">
                                <option <c:if test="${location.stationUserId == null}">selected</c:if>>None</option>
                                <c:forEach var="user" items="${stationUsers}">
                                    <option value="${user.id}" <c:if test="${location.stationUserId == user.id}">selected</c:if>>${user.id} ${user.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td id="districtUserId">
                            <select name="districtUser">
                                <option <c:if test="${location.districtCenterUserId == null}">selected</c:if>>None</option>
                                <c:forEach var="user" items="${districtUsers}">
                                    <option value="${user.id}" <c:if test="${location.districtCenterUserId == user.id}">selected</c:if>>${user.id} ${user.name}</option>
                                </c:forEach>
                            </select>
                        <td id="type">
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
            <form id="addPartyForm">
                <table>
                    <tbody>
                    <tr>
                        <td><label for="partyName">Name</label></td>
                        <td><input type="text" id="partyName" name="partyName"></td>
                    </tr>
                    </tbody>
                </table>
                <input type="button" onclick="addParty()" value="Add"/>
            </form>
        </div>
        <div id="availableParties">
            <h2>Available parties</h2>
            <table>
                <thead>
                <tr>
                    <td>ID</td>
                    <td>Name</td>
                    <td>Votes</td>
                    <td>Delete</td>
                </tr>
                </thead>
                <tbody id="partiesTBody">
                    <c:forEach items="${parties}" var="party">
                        <tr>
                            <td id="tdIdParty">${party.id}</td>
                            <td>${party.name}</td>
                            <td>${party.votes}</td>
                            <td><button onclick="deleteParty(this.parentNode.parentNode)">Delete</button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
