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
</head>
<body>
<div>
    <%@include file="navBar.jsp" %>
    <div>
        <h1>Manage Users</h1>
        <div>
            <h2>Add new user</h2>
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
                                <input type="radio" id="secretariat-office" name="userLevel" value="2">
                                <label for="secretariat-office">Secretariat Office</label>
                            </span>
                            <span>
                                <input type="radio" id="admin" name="userLevel" value="3">
                                <label for="admin">Admin</label>
                            </span>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <input type="button" value="Register" onclick="addUser()">
            </form>
        </div>
        <div id="assignRoles">
            <h2>Assign roles</h2>
            <table>
                <thead>
                <tr>
                    <td>User ID</td>
                    <td>Name</td>
                    <td>Email</td>
                    <td>Contact Number</td>
                    <td>Role</td>
                    <td>Action</td>
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
                                <option value="2" <c:if test="${user.userLevel==2}">selected</c:if>>Secretariat Office</option>
                                <option value="3" <c:if test="${user.userLevel==3}">selected</c:if>>Admin</option>
                            </select>
                        </td>
                        <td><button onclick="updateUser(this.parentNode.parentNode)">Update</button><button onclick="deleteUser(this.parentNode.parentNode)">Delete</button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
