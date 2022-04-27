<%@ page import="java.util.Objects" %><%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 26/04/2022
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
    <ul>
        <li><a href="login?logout=1">logout</a></li>
        <% if (Objects.equals(String.valueOf(session.getAttribute("level")), "3")) { %>
        <li><a href="user-management">User managment</a></li>
        <li><a href="vote-management">Vote management</a></li>
        <% }%>
    </ul>
</nav>
