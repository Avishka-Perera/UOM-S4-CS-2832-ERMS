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
</head>
<body>
<div>
    <%@include file="navBar.jsp" %>
    <div>
        <h1>Vote Management</h1>
        <div>
            <h2>Add new location</h2>
            <form action="add-location" method="post">
                <table>
                    <tbody>
                    <tr>
                        <td><label for="name">Name</label></td>
                        <td><input type="text" id="name" name="name"></td>
                    </tr>
                    <tr>
                        <td><label for="stationUser">Station user</label></td>
                        <td>
                            <select name="stationUser" id="stationUser">
                                <option value="volvo">Volvo</option>
                                <option value="saab">Saab</option>
                                <option value="mercedes">Mercedes</option>
                                <option value="audi">Audi</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><label for="districtCenterUser">District center user</label></td>
                        <td>
                            <select name="districtCenterUser" id="districtCenterUser">
                                <option value="volvo">Volvo</option>
                                <option value="saab">Saab</option>
                                <option value="mercedes">Mercedes</option>
                                <option value="audi">Audi</option>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <input type="submit" value="Add">
            </form>
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
