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
</head>
<body>
<div>
    <%@include file="navBar.jsp" %>
    <div>
        <h1>Manage Users</h1>
        <div>
            <h2>Add new user</h2>
            <form action="users" method="post">
                <table>
                    <tbody>
                    <tr>
                        <td><label for="name">Name</label></td>
                        <td><input id="name" name="name" type="text"></td>
                    </tr>
                    <tr>
                        <td><label for="email">Email</label></td>
                        <td><input id="email" name="email" type="email"></td>
                    </tr>
                    <tr>
                        <td><label for="password">Password</label></td>
                        <td><input id="password" name="password" type="password"></td>
                    </tr>
                    <tr>
                        <td><label for="confirm-password">Confirm password</label></td>
                        <td><input id="confirm-password" type="password"></td>
                    </tr>
                    <tr>
                        <td><label for="contactNumber">Contact number</label></td>
                        <td><input id="contactNumber" name="contactNumber" type="text"></td>
                    </tr>
                    <tr>
                        <td><label>User level</label></td>
                        <td>
                    <span>
                        <input type="radio" id="polling-station" name="userLevel" value="0">
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
                <input type="submit" value="Register">
            </form>
        </div>
        <div>
            <h2>Assign roles</h2>
            <table>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.name}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><c:out value="${user.contactNumber}"/></td>
                        <td><c:out value="${user.userLevel}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
