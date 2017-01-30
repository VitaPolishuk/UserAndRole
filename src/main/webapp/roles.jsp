<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Роли</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src = "rolesJS.js"></script>
    <link rel="stylesheet" href="styleRole.css" type="text/css"/>
</head>
<body onload="loadList('<%=request.getAttribute("listRoles")%>')">
<form name="roles" action="Roles.java" method="post">
<div class="linkRoles"><h3><a href="http://localhost:8001/r">На главную</a></h3></div>
<div class="main">
    <div class="selectRole">
        <select name="Roles" id="Roles" onchange="selectRole()">
            <option value="roles" selected>Выберите роль</option>
        </select>
        <p>
        <div><input name="name_roles" type="text" id="name_roles"></div>
    </div>
    <div class="actions">
        <div> <input type="button" name="Add" value="Добавить роль" onclick="addRole()"></div>
        <div><input type="button" name="Change" value="Изменить роль" onclick="changeRole()"></div>
        <div>  <input type="button" name="Delete" value="Удалить роль" onclick="deleteRole()"></div>

    </div>
</div>
</form>

</body>
</html>
