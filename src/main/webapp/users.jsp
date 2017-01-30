<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Пользователи</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <script src = "usersJS.js"> </script>
    <link rel="stylesheet" href="style.css" type="text/css"/>
    <style>

    </style>
</head>
<body   onload = "loadListRoles('<%=request.getAttribute("listRole")%>');
        downloadTable('<%=request.getAttribute("listUserAll")%>')">
<form name="users" action="Users.java" method="post" id="formx">
    <input type='hidden' name="nevidimka" id="nevidimka">
  <h3><a href="http://localhost:8001/r">На главную</a></h3>
    <div class="mainblok">
        <div class = "table">
            <table id="tableAllUsers" class = "tableUsers">
                <tr class = "tr1 stroka">
                    <th style='visibility: hidden' width="2" class = "tr1">ИД пользователя</th>
                    <th class = "tr1">Имя пользователя</th>
                    <th  class = "tr1">Email</th>
                    <th class = "tr1">Роль</th>
                    <th  class = "tr1">Изменить/Удалить</th>
                </tr>

            </table>
        </div>

       <div class="inputData">
           <div class = "inpTable">
            <table id="table" class  = "tableInput">
                <tr>
                    <td  class = "td1" > Имя пользователя</td>
                    <td class = "td2" >
                        <input name="nameUser" type="text" id="nameU">
                    </td>
                </tr>
                <tr>
                    <td class = "td3">Email</td>
                    <td class = "td4">
                        <input name="email" type="text" id="email">
                    </td>
                </tr>
                <tr>
                    <td class = "td5">Имя роли</td>
                    <td class = "td6">

                        <select name="name_roles" id="name_roles">
                            <option value="roles" selected>Выберите роль</option>
                        </select>
                    </td>
                </tr>
            </table>
           </div>
           <div class="Actions">

               <div class="Add"><input type="button" name="Add" value="Добавить" onclick="check(this.form)"></div>

               <div class="Save"><input type="button" name="Save" value="Сохранить" onclick="saveChange()"></div>
           </div>
    </div>
    </div>

 </form>




</body>
</html>