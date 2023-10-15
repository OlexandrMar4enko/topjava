<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html lang="ru">

<style>

    table {
        border-collapse: collapse;
    }

    th, td {
        padding: 0.5rem;
        text-align: left;
        border: 1px solid #ddd;
    }

    th {
        background-color: #f5f5f5;
        font-weight: bold;
        text-align: center;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    .highlight-true {
        color: green;
    }

    .highlight-false {
        color: red;
    }

</style>


<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h4><a href="meals?action=create">Add Meal</a></h4>

<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <thead>
    <tbody>
    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.isExcess() ? 'highlight-true' : 'highlight-false'}">
            <td>${meal.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>

</table>
</body>
</html>