<%@ page import="com.example.mainweb2.Point" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% List<Point> resultList = (List<Point>) session.getAttribute("resultList"); %>
<%
    if (resultList == null) {
        resultList = new ArrayList<>();
        session.setAttribute("resultList", resultList);
    }
%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<table id="layout" >
<h1> <p> <%= "Армут Алина Юсуфовна " +
        "Вариант: 2467 "  +
        "Группа: 3214" %> </p>
</h1>
<br/>
    <form action="${pageContext.request.contextPath}/ControllerServlet" id="form" method="get">

    <div class="coordinates-container">

    <label for="x">Введите координату X:</label>
    <div id="x_select">
        <select name="x" id="x"required >

            <option value="-4">-4</option>
            <option value="-3">-3</option>
            <option value="-2">-2</option>
            <option value="-1">-1</option>
            <option value="0">0</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
        </select>
    </div>

    <div class="input_values">
        <label for="y">Введите координату Y:</label> <br>
        <br>
        <input style="margin-bottom: 10px" type="text" name="y" class="y" id="y"
               placeholder="[-3..3]" autocomplete="off" maxlength="6" required><br>
<%--        <div class="input_values">--%>
            <label for="r">Введите координату R:</label> <br>
            <br>
            <input style="margin-bottom: 10px" type="text" name="r" class="r" id="r"
                   placeholder="[1..4]" autocomplete="off" maxlength="6" required><br>

<%--        </div>--%>

    </div>
        <input type="submit" class="button-check"  id="button-check" value="Нажми и проверь!">
</div>
    </form>

    <svg width="300" height="300" xmlns="http://www.w3.org/2000/svg" id="svg">
        <line stroke="#000720" x1="0" x2="300" y1="150" y2="150"></line>
        <line stroke="#000720" x1="150" x2="150" y1="0" y2="300"></line>
        <polygon points="150,0 144,15 156,15" fill="#97caff" stroke="#c282ff"></polygon>
        <polygon points="300,150 285,156 285,144" fill="#97caff" stroke="#c282ff"></polygon>

        <line stroke="#000720" x1="200" x2="200" y1="155" y2="145"></line>
        <line stroke="#000720" x1="250" x2="250" y1="155" y2="145"></line>

        <line stroke="#000720" x1="50" x2="50" y1="155" y2="145"></line>
        <line stroke="#000720" x1="100" x2="100" y1="155" y2="145"></line>

        <line stroke="#000720" x1="145" x2="155" y1="100" y2="100"></line>
        <line stroke="#000720" x1="145" x2="155" y1="50" y2="50"></line>

        <line stroke="#000720" x1="145" x2="155" y1="200" y2="200"></line>
        <line stroke="#000720" x1="145" x2="155" y1="250" y2="250"></line>


        <text x="195" y="140">R/2</text>
        <text x="248" y="140">R</text>

        <text x="40" y="140">-R</text>
        <text x="90" y="140">-R/2</text>

        <text x="160" y="105">R/2</text>
        <text x="160" y="55">R</text>

        <text x="160" y="205">-R/2</text>
        <text x="160" y="255">-R</text>

        <text x="160" y="10">Y</text>
        <text x="290" y="140">X</text>

        <polygon fill="#97caff"
                 stroke="#c282ff"
                 fill-opacity="0.5"
                 points="150,150 150,250 100,250 100,150"></polygon>


        <polygon fill="#97caff"
                 stroke="#c282ff"
                 fill-opacity="0.5"
                 points="150,150 200,150 150,250"></polygon>


        <path d="M 150 100 A 50 50, 90, 0, 1, 200 150 L 150 150 Z"
              fill-opacity="0.5"
              fill="#97caff"
              stroke="#c282ff"></path>
    </svg>

<table class="result-table" id="result-table" border="2" >
  <thead>
  <tr>
      <th>X</th>
      <th>Y</th>
      <th>R</th>
      <th>Результаты</th>
      <th>Время</th>
      <th>Время выполнения</th>
  </tr>
  </thead>
  <tbody>


<%


    if (resultList != null && !resultList.isEmpty()) {
        for (Point point : resultList) {
%>
<tr>
    <td class="x_cell"><%= point.getX() %></td>
    <td class="y_cell"><%= point.getY() %></td>
    <td class="r_cell"><%= point.getR() %></td>
    <td class="result_cell" >
        <%= point.isError() ? "ошибка" :
                point.isRes() ? "прокнуло" : "мимо" %>
    </td>
    <td class="time_cell"><%= point.getCurrentTime() %></td>
    <td class="execution_time_cell"><%= point.getExecutionTime() %> ns</td>
</tr>
<%
        }
    }
    
%>
  </tbody>
</table>
</table>
<script src="validator.js"></script>
<script src="interactiveSvg.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script> const ctx="${pageContext.request.contextPath}";</script>



</body>
</html>