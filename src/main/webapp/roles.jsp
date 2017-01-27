
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Роли</title>
    <script type = "text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
</head>
<body onload = "loadList('<%=request.getAttribute("listRoles")%>')" bgcolor="#DEB887">
<form name="roles" action="Roles.java" method="post">
    <div class="linkRoles"><h3><a href="http://localhost:8001/">Пользователи</a></h3></div>
    <p>Выберите роль</p>
<select name = "Roles" id = "Roles" onchange="selectRole()">
    <option   value="roles" selected>Выберите роль</option>
</select>
    <p>
    <div> <input name="name_roles" type="text" id = "name_roles"></div>
<div class = "actions">
    <p>Выберите действие</p>
    <p> <input  type="button" name = "Add" value = "Добавить" onclick="addRole()">
    <p> <input  type="button" name = "Delete" value = "Удалить" onclick="deleteRole()">
    <p> <input  type="button" name = "Change" value = "Изменить" onclick="changeRole()">
</div>
</form>

<script type="text/javascript">
    function getValuesByValue(index){

        return index.split(","); // преобразуем строку в массив значений
    }
    function loadList(str) {

        aCurrValues =   getValuesByValue(str);
        var nCurrValuesCnt = aCurrValues.length;
        var oListL = document.forms["roles"].elements["Roles"];

        var oListOptionsCnt = oListL.options.length;
        oListL.length = 0; // удаляем все элементы из списка значений
        for (i = 0; i < nCurrValuesCnt; i++){
            // далее мы добавляем необходимые значения в список
            if (document.createElement){
                var newListOption = document.createElement("OPTION");
                newListOption.text = aCurrValues[i];
                newListOption.value = aCurrValues[i];
                // тут мы используем для добавления элемента либо метод IE, либо DOM
                (oListL.options.add) ? oListL.options.add(newListOption) : oListL.add(newListOption, null);
            }else{
                // для NN3.x-4.x
                oListL.options[i] = new Option(aCurrValues[i], aCurrValues[i], false, false);
            }
        }}
    function selectRole() {
        ///document.getElementById("name_roles").value = document.getElementById("Roles").value
        $.ajax({
            type: "POST",
            url: "servletDB",
            data: {type: "selectRole", name : document.getElementById("Roles").value },
            dataType: "json",

            success: function( data, textStatus, jqXHR) {

                $('#name_roles').val(data.name_roles);

            }
        })
    }
    function changeRole(){
        $.ajax({
            type: "POST",
            url: "servletDB",
            data: {type: "ChangeRole", selectRoleChange: document.getElementById("Roles").value,
                name: document.getElementById("name_roles").value,},

            dataType: "json",
            success: function( data, textStatus, jqXHR) {
                $('#name_roles').val("");
                loadList(data.listRoleChange);

            }
        })

    }
    function addRole(){
        $.ajax({
            type: "POST",
            url: "servletDB",
            data: {type: "AddRole", nameRole: document.getElementById("name_roles").value},
            dataType: "json",
            success: function( data, textStatus, jqXHR) {

                $('#name_roles').val("");
                loadList(data.listRoleUpdate);
            }
        })

    }
    function  deleteRole(){
        $.ajax({
            type: "POST",
            url: "servletDB",
            data: {type: "DelRole", selectRoleDel: document.getElementById("Roles").value},

            dataType: "json",
            success: function( data, textStatus, jqXHR) {
                $('#name_roles').val("");
                loadList(data.listRoleDel);

            }
        })

    }
</script>
</body>
</html>
