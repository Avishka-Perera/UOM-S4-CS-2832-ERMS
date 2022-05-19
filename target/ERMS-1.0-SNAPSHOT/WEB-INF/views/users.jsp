<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 26/04/2022
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>User management</title>
    <script src="<%request.getContextPath();%>js/jquery-3.6.0.min.js"></script>
    <script src="<%request.getContextPath();%>js/utilities.js"></script>
    <script src="<%request.getContextPath();%>js/crudFunctions.js"></script>
    <script src="<%request.getContextPath();%>js/constants.js"></script>
    <script src="<%request.getContextPath();%>js/users.js"></script>

    <%--    Styles  --%>
    <link rel="stylesheet" href="<%request.getContextPath();%>css/bootstrap.min.css">
    <link rel="stylesheet" href="<%request.getContextPath();%>css/common.css">
    <link rel="stylesheet" href="<%request.getContextPath();%>css/navbar.css">
</head>
<body>
<div class="root bg-1">
    <%@include file="navBar.jsp" %>
    <div class="navBar-bottom-padding"></div>
    <div>
        <h1 class="p-3">Manage Users</h1>
        <div id="add-user-modal" class="modalBG hidden">
            <div class="modalContent">
                <h2 id="userModalHeading">Add new user</h2>
                <form id="addUserForm">
                    <table>
                        <tbody>
                        <tr>
                            <td><label for="name">Name</label></td>
                            <td><input id="name" name="name" type="text" required></td>
                        </tr>
                        <tr>
                            <td><label for="email">Email</label></td>
                            <td><input id="email" name="email" type="email" required></td>
                        </tr>
                        <tr>
                            <td><label for="password">Password</label></td>
                            <td><input id="password" name="password" type="password" required></td>
                        </tr>
                        <tr>
                            <td><label for="confirm-password">Confirm password</label></td>
                            <td><input id="confirm-password" type="password" required></td>
                        </tr>
                        <tr>
                            <td><label for="contactNumber">Contact number</label></td>
                            <td><input id="contactNumber" name="contactNumber" type="text" required></td>
                        </tr>
                        <tr>
                            <td><label for="userLevels">User level</label></td>
                            <td id="userLevels">
                            <span>
                                <input type="radio" id="polling-station" name="userLevel" value="0" checked>
                                <label for="polling-station">Polling Station</label><br>
                            </span>
                                <span>
                                <input type="radio" id="district-center" name="userLevel" value="1">
                                <label for="district-center">District Center</label><br>
                            </span>
                                <span>
                                <input type="radio" id="media" name="userLevel" value="2">
                                <label for="media">Media</label>
                            </span>
                                <span>
                                <input type="radio" id="admin" name="userLevel" value="3">
                                <label for="admin">Admin</label>
                            </span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </form>
                <div>
                    <button onclick="addUser()" id="user-action-btn" class="btn primary-contained">Add</button>
                    <button onclick="toggleModal('#add-user-modal')" class="btn secondary-outlined">Close</button>
                </div>
            </div>
        </div>
        <div id="assignRoles">
<%--            <h2>Assign roles</h2>--%>
            <table class="center-table border whiteBg">
                <thead>
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Contact Number</th>
                    <th>Role</th>
                    <th>Location</th>
                    <th>Action <button class="btn primary-outlined x-small-btn ml-2" onclick="openAddUserModal()">Add new</button></th>
                </tr>
                </thead>
                <tbody id="usersTBody">
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td id="trId"><c:out value="${user.id}"/></td>
                        <td id="trName"><c:out value="${user.name}"/></td>
                        <td id="trEmail"><c:out value="${user.email}"/></td>
                        <td id="trContactNumber"><c:out value="${user.contactNumber}"/></td>
                        <td id="trUserLevel">
                            <select name="stationUser" id="userLevel">
                                <option value="0" <c:if test="${user.userLevel==0}">selected</c:if>>Station Officer</option>
                                <option value="1" <c:if test="${user.userLevel==1}">selected</c:if>>District Center Officer</option>
                                <option value="2" <c:if test="${user.userLevel==2}">selected</c:if>>Media</option>
                                <option value="3" <c:if test="${user.userLevel==3}">selected</c:if>>Admin</option>
                            </select>
                        </td>
                        <td>
                            <c:if test="${user.location == null}">Not assigned</c:if>
                            <c:if test="${user.location != null}">${user.location.name}</c:if>
                        </td>
                        <td>
                            <button onclick="updateUserLevel(this.parentNode.parentNode)" class="btn primary-outlined x-small-btn m-1">Update</button>
                            <button onclick="openEditUserModal(this.parentNode.parentNode)" class="btn primary-outlined x-small-btn m-1">Edit</button>
                            <button onclick="safeDeleteUser(this.parentNode.parentNode)" class="btn secondary-outlined x-small-btn m-1">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
