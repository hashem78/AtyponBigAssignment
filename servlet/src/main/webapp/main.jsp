<%--
  Created by IntelliJ IDEA.
  User: mythi
  Date: 8/6/23
  Time: 6:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Grades for ${userEmail}
<ul class="list-group">
    <%--@elvariable id="classGradesPairs" type="java.util.List<com.hashem.p1.ClassGradesPair>"--%>
    <%--@elvariable id="grade" type="com.hashem.p1.models.Grade"--%>
    <li>
        <c:forEach var="pair" items="${classGradesPairs}">
            ${pair.clazz().name()}
            <c:forEach var="grade" items="${pair.grades()}">
                <ol>
                        ${grade.grade()}
                </ol>
            </c:forEach>
        </c:forEach>
    </li>
</ul>
</body>
</html>
