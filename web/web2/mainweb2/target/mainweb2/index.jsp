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

       <svg width="400" height="300" id="svg">

        <%-- здесь создаются линии и текст к ним --%>
        <svg xmlns="http://www.w3.org/2000/svg">
        <line x1="0" y1="160" x2="320" y2="160" stroke-width="1.6" stroke="black" />
        <text x="330" y="170" font-size="16" fill="black">Y</text>
        <line x1="160" y1="0" x2="160" y2="320" stroke-width="1.6" stroke="black"/>
        <text x="170" y="20" font-size="16" fill="black">X</text>
        <%-- здесь создаются линии и текст к ним --%>


        <line x1="0" y1="160" x2="320" y2="160" stroke-width="1.6"/>
        <line x1="160" y1="0" x2="160" y2="320" stroke-width="1.6"/>

        <polygon id="1" points="320,160 312,156 312,164"/>
        <polygon id="2" points="160,0 156,8 164,8"/>

        <line x1="40" y1="156" x2="40" y2="164" stroke-width="1.6"/>
        <line x1="120" y1="156" x2="120" y2="164" stroke-width="1.6"/>
        <line x1="200" y1="156" x2="200" y2="164" stroke-width="1.6"/>
        <line x1="280" y1="156" x2="280" y2="164" stroke-width="1.6"/>
        <line x1="360" y1="156" x2="360" y2="164" stroke-width="1.6"/>

        <%-- здесь прописывается текст для R --%>
            <text id="capt" x="35" y="152">-R</text>
            <text id="capt" x="115" y="152">-R/2</text>
            <text id="capt" x="200" y="152">R/2</text>
            <text id="capt" x="275" y="152">R</text>

        <line x1="156" y1="40" x2="164" y2="40" stroke-width="1.6"/>
        <line x1="156" y1="120" x2="164" y2="120" stroke-width="1.6"/>
        <line x1="156" y1="200" x2="164" y2="200" stroke-width="1.6"/>
        <line x1="156" y1="280" x2="164" y2="280" stroke-width="1.6"/>
        <line x1="156" y1="360" x2="164" y2="360" stroke-width="1.6"/>




        <%-- здесь создаются фигуры --%>
        <path id="circle" d="M160,120 A40,40 0 0,1 200,160 L160,160 Z" fill="rgb(51,153,255)"
              stroke-width="1.6"/>

            <rect id="rotate"  transform="rotate(360)" width="60" height="120" x="100" y="160" style="fill:rgb(51,153,255);
            stroke-width:1.6"
                  stroke="black" />

            <polygon points="160,160 220,160 160,280" fill="rgb(51,153,255)" stroke="black" stroke-width="1.6" />
            <text x="144" y="44" id="capt">R</text>
            <text x="141" y="124" id="capt">R/2</text>
            <text x="144" y="204" id="capt">-R/2</text>
            <text x="144" y="284" id="capt">-R</text>
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
    <td class="result_cell"><%= point.isRes() ? "прокнуло" : "мимо" %></td>
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