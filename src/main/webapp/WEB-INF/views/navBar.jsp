<%@ page import="java.util.Objects" %>
<%@ page import="constants.Routes" %>
<%@ page import="constants.UserLevels" %>
<%--
  Created by IntelliJ IDEA.
  User: Avishka
  Date: 26/04/2022
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>

<nav>
    <ul>
        <li><a href=${Routes.ROUTE_PUBLIC}>Public page</a></li>
        <% if (Objects.equals(String.valueOf(session.getAttribute("level")), String.valueOf(UserLevels.ADMIN_USER_LEVEL))) { %>
        <li><a href=${Routes.ROUTE_USERS}>User management</a></li>
        <li><a href=${Routes.ROUTE_VOTE_MANAGEMENT}>Vote management</a></li>
        <% }%>
        <% if (UserLevels.VOTE_USER_LEVELS.contains(session.getAttribute("level"))) { %>
        <li><a href=${Routes.ROUTE_VOTES}>Votes</a></li>
        <% }%>
        <% if (session.getAttribute("id") == null) { %>
        <li><a href="login">login</a></li>
        <% } else { %>
        <li><a href="login?logout=1">logout</a></li>
        <% } %>
    </ul>
</nav>
