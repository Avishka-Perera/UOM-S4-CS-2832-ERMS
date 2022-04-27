<%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 26/04/2022
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
    <div>
        <h1>Login</h1>
        <form action="login" method="post">
            <table>
                <tbody>
                <tr>
                    <td><label for="email">Email</label></td>
                    <td><input type="email" id="email" name="email"></td>
                </tr>
                <tr>
                    <td><label for="password">Password</label></td>
                    <td><input type="password" id="password" name="password"></td>
                </tr>
                </tbody>
            </table>
            <input type="submit" value="Login">
        </form>
    </div>
</body>
</html>
