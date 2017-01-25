<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Пользователи</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">

    <link rel="stylesheet"  href="style.css" type="text/css" />
    <style>


        .Add, .Delete, .Change{
            display: inline-block;


        }
        .Users{
            display: inline-block;


        }
        .linkRoles{
            display: inline-block;
        }
         </style>
</head>
<body onload = "loadList()">

<form name="users" action="Users.java" method="post">
    <div class = "mainblok">
        <h1>Пользователи</h1>
        <div class = "Users" >

            <select name = "Users">
                <option   value="users" selected>Выберите пользователя</option>

            </select>

        </div>
        <p><p>
        <div class = "Actions" > </div>
        <input type = "hidden"  name = "nevidimka" id = "nevidimka" value="">
    <div class = "Add"> <input  type="button" name = "Add" value="Добавить нового пользователя" onclick = "document.getElementById('nevidimka').value = 'Add'
    document.forms['users'].submit() "> </div>
    <div class = "Delete">   <input  type="button" name = "Delete" value="Удалить пользователя" onclick = "document.getElementById('nevidimka').value = 'Delete'
    document.forms['users'].submit() "> </div>
    <div class = "Change">   <input  type="button" name = "Change" value="Изменить данные пользователя" onclick = "document.getElementById('nevidimka').value = 'Change'
    document.forms['users'].submit() "> </div>



    </div>


<div class = "inputData">
        <table>
            <tr>
                <td>ИД пользователя</td>
                <td>
                    <input name="id_user" type="text">
                </td>
            </tr>
            <tr>
                <td>Имя пользователя</td>
                <td>
                    <input name="nameUser" type="text">
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td>
                    <input name="email" type="text">
                </td>
            </tr>
            <tr>
                <td>ИД роли</td>
                <td>
                    <input name="id_roles" type="text">
                </td>
            </tr>
        </table>
</div>
    <div class = "linkRoles"><h3><a href= "http://localhost:8001/roles">Роли</a></h3></div>
</form>





<script type="text/javascript">
    function getValuesByValue(index){

        return index.split(","); // преобразуем строку в массив значений
    }
    function loadList() {

    aCurrValues =   getValuesByValue("<%=request.getAttribute("listUser")%>");
    var nCurrValuesCnt = aCurrValues.length;
    var oListL = document.forms["users"].elements["Users"];

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
</script>
</body>
</html>