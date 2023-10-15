<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
  <title>Edit meal</title>
</head>
<body>
<h1>Edit meal</h1>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form action="meals" method="post">
  <input type="hidden" name="id" value="${meal.id}">

  <label for="datetime">DateTime:</label>
  <input type="datetime-local" id="datetime" name="datetime" value="${meal.dateTime.format(DateTimeFormatter.ofPattern('yyyy-MM-dd\'T\'HH:mm:ss'))}"><br><br>
  <label for="description">Description:</label>
  <input type="text" id="description" name="description" value="${meal.description}"><br><br>
  <label for="calories">Calories:</label>
  <input type="number" id="calories" name="calories" value="${meal.calories}"><br><br>
  <input type="hidden" name="id" value="${meal.id}">

  <input type="submit" value="Save">
  <input type="button" value="Cancel" onclick="location.href='index.html';">
</form>
</body>
</html>