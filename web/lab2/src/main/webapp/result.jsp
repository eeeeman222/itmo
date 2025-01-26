<%@ page import="point.PointsArr" %>
<%@ page import="point.PointManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href=styles.css>
<head>
    <title>Результаты</title>
</head>
<body>
<h1>Результаты проверки</h1>

<%
    PointsArr points = (PointsArr) session.getAttribute("points");
    if (points != null && !points.getPoints().isEmpty()) {
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Таблица результатов</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 20px;
        }

        table {
            width: 80%;
            margin: 0 auto;
            border-collapse: collapse;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
        }

        th, td {
            padding: 12px 15px;
            text-align: center;
            border: 1px solid #dddddd;
        }

        thead {
            background-color: #d000ff;
            color: #ffffff;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #e8f0fe;
        }

        th {
            font-size: 18px;
            font-weight: bold;
        }

        td {
            font-size: 16px;
        }
    </style>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Результат</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (PointManager point : points.getPoints()) {
    %>
    <tr>
        <td><%= point.getX() %></td>
        <td><%= point.getY() %></td>
        <td><%= point.getR() %></td>
        <td><%= point.isInArea() ? "изи" : "неизи(((" %></td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>

<button class="button" onclick="window.location.href='index.jsp'">Вернуться на главную страницу</button>
<%
} else {
%>
<p>Нет данных для отображения.

</p>
<button class="button" onclick="window.location.href='index.jsp'">Вернуться на главную страницу</button>
<%
    }
%>

</body>
</html>