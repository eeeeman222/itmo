<%@ page import="java.awt.*" %>
<%@ page import="point.PointsArr" %>
<%@ page import="point.PointManager" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="pointsArr" class="point.PointsArr" scope="session" />
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Веб лаба 2</title>
    <link rel="stylesheet" href=styles.css>
    <script src=script.js defer></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<header>
    <H1>Лабораторная работа часть 2 в(д)еб</H1>
    <a>Еманов Илья Сергеевич</a><br>
    <a>Вариант 5739</a>
</header>

<table>
    <tr>
        <td><label>Выберите X:</label></td>
        <td>
            <select id="x" name="x" required>
                <option value="-2">-2</option>
                <option value="-1.5">-1.5</option>
                <option value="-1">-1</option>
                <option value="-0.5">-0.5</option>
                <option value="0">0</option>
                <option value="0.5">0.5</option>
                <option value="1">1</option>
                <option value="1.5">1.5</option>
                <option value="2">2</option>
            </select>
        </td>
    </tr>
    <tr>
        <td><label>Выберите Y (От -5 до 3):</label></td>
        <td>
            <input type="text" id="yInput">
            <div id="yError" class="error"></div>
        </td>
    </tr>
    <tr>
        <td><label>Выберите R:</label></td>
        <td>
            <input type="radio" name="rValue" value="1" onchange="draw()">1
            <input type="radio" name="rValue" value="1.5" onchange="draw()">1.5
            <input type="radio" name="rValue" value="2" onchange="draw()">2
            <input type="radio" name="rValue" value="2.5" onchange="draw()">2.5
            <input type="radio" name="rValue" value="3" onchange="draw()">3
            <div id="rError" class="error"></div>
        </td>
    </tr>
    <tr>
        <td><button onclick="drawGraph()">Проверить точку</button></td>
    </tr>
    <tr>
        <td><canvas id="graphCanvas" width="400" height="400"></canvas></td>

    </tr>

</table>
<tfoot>
<tr>
    <td colspan="5" id="outputContainer">
        <% PointsArr points = (PointsArr) request.getSession().getAttribute("points");
            if (points == null) {
        %>
        <h4>
            <span id="notifications" class="outputStub notification">Нет результатов</span>
        </h4>
        <table id="outputTable">
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Точка входит в ОДЗ</th>
            </tr>
        </table>
        <% } else { %>
        <h4>
            <span class="notification"></span>
        </h4>
        <table id="outputTable">
            <tr>
                <th>X</th>
                <th>Y</th>
                <th>R</th>
                <th>Точка входит в ОДЗ</th>
            </tr>
            <% for (PointManager point : points.getPoints()) { %>
            <tr>
                <td>
                    <%= point.getX() %>
                </td>
                <td>
                    <%= point.getY() %>
                </td>
                <td>
                    <%= point.getR() %>
                </td>
                <td>
                    <%= point.isInArea() ? "<span class=\"success\">изи</span>" : "<span class=\"fail\">не изи(</span>" %>
                </td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </td>
</tr>
</tfoot>

<button class="button" onclick="window.location.href='result.jsp'">Перейти к результатам</button>

<script src="script.js"></script>


</body>
</html>