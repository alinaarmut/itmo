<%@ page import="java.util.List" %>
<%@ page import="com.example.mainweb2.Point" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result</title>
    <link rel="stylesheet" href="result.css">
</head>
<body>


<table border="2" id="response-table" class="response-table" align="center"  cellpadding ="20">

    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Результаты</th>
        <th>Время</th>
        <th>Время выполнения</th>
    </tr>
        <%
    List<Point> resultList = (List<Point>) session.getAttribute("resultList");

    if (resultList != null && !resultList.isEmpty()) {
        Point lastPoint = resultList.get(resultList.size() - 1);

%>
    <tr>
        <td class="x_cell"><%= lastPoint.getX() %></td>
        <td class="y_cell"><%= lastPoint.getY() %></td>
        <td class="r_cell"><%= lastPoint.getR() %></td>
        <td class="result_cell"><%=lastPoint.isError() ? "ошибка" :
                lastPoint.isRes() ? "прокнуло" : "мимо" %></td>
        <td class="time_cell"><%= lastPoint.getCurrentTime() %></td>
        <td class="execution_time_cell"><%= lastPoint.getExecutionTime()%> ns</td>
    </tr>
        <%

    }
%>
</table>

<input  type="button" onclick="window.location.href='index.jsp'" value="Камбекнуть"/>


</body>
</html>
