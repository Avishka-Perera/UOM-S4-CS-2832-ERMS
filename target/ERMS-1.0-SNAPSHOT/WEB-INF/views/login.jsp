<%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 26/04/2022
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Login</title>

    <%--    Styles  --%>
    <link rel="stylesheet" href="<%request.getContextPath();%>css/bootstrap.min.css">
    <link rel="stylesheet" href="<%request.getContextPath();%>css/login.css">
    <link rel="stylesheet" href="<%request.getContextPath();%>css/common.css">
    <link rel="stylesheet" href="<%request.getContextPath();%>css/navbar.css">
</head>
<body>
    <%@include file="navBar.jsp" %>
    <div class="login-root bg-2">
        <div  class="shadow-lg login-container rounded-2 pt-5 pb-5 pl-4 pr-4">
            <h1 class="mb-3">Login</h1>
            <form action="login" method="post">
                <table>
                    <tbody>
                    <tr>
                        <td><label for="email" class="m-2">Email</label></td>
                        <td><input type="email" id="email" name="email" class="m-2"></td>
                    </tr>
                    <tr>
                        <td><label for="password" class="m-2">Password</label></td>
                        <td><input type="password" id="password" name="password" class="m-2"></td>
                    </tr>
                    </tbody>
                </table>
                <input type="submit" value="Login" class="btn primary-contained mt-3">
            </form>
        </div>
    </div>
</body>
</html>
