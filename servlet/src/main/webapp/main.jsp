<%@ page import="com.hashem.p1.models.ClassStatistics" %><%--
  Created by IntelliJ IDEA.
  User: mythi
  Date: 8/6/23
  Time: 6:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
    final ClassStatistics defaultStats = new ClassStatistics();
%>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9" crossorigin="anonymous">
    <title>Title</title>
</head>
<body>

<%--@elvariable id="classGradesPairs" type="java.util.List<com.hashem.p1.MainViewModel>"--%>
<%--@elvariable id="grade" type="com.hashem.p1.models.Grade"--%>

<style>
    .container {
        width: 100%;
        height: fit-content;
        display: flex;
        align-items: center;
        justify-content: center;
    }
</style>

<div class="container">
    Grades for ${userEmail}
</div>

<c:forEach items="${classGradesPairs}" var="model">
    <div class="container">
        <table class="table">
            <thead>
            <tr>
                <th colspan="2">Grades for ${model.clazz().name()}</th>
            </tr>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Grade</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${model.grades()}" var="grade">
                <tr>
                    <th scope="row">${grade.id()}</th>
                    <td>${grade.grade()}</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th colspan="2">Class statistics</th>
            </tr>
            <tr>
                <th scope="col">Min</th>
                <th scope="col">Max</th>
                <th scope="col">Avg</th>
                <th scope="col">Std. Div</th>
                <th scope="col">Variance</th>
                <th scope="col">Range</th>
                <th scope="col">Median</th>
                <th scope="col">Mode</th>

            </tr>
            <c:set var="defaultStats" value="<%=defaultStats%>"/>
            <c:if test="${model.statistics() != defaultStats}">
                <tr>
                    <td>${model.statistics().min()}</td>
                    <td>${model.statistics().max()}</td>
                    <td>${model.statistics().average()}</td>
                    <td>${model.statistics().deviation()}</td>
                    <td>${model.statistics().variance()}</td>
                    <td>${model.statistics().range()}</td>
                    <td>${model.statistics().median()}</td>
                    <td>${model.statistics().mode()}</td>
                </tr>
            </c:if>


            </tfoot>
        </table>
    </div>
</c:forEach>

</body>
</html>
