<%@ page import="so.pada.dota.youku.Video" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-3-18
  Time: 下午9:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%
        List<Video> videos = (List<Video>) request.getAttribute("videos");
    %>
</head>
<body>
<table>
    <c:forEach var="video" items="${videos}">
        <tr>
            <td>
                <a href="${video.link}" target="_blank">${video.title}</a>
            </td>
            <td>${video.link}</td>
            <td>${video.duration}</td>
            <td>${video.published}</td>
            <td>${video.description}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>