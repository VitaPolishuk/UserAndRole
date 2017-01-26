<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Пользователи</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    <script type = "text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
    <link rel="stylesheet" href="style.css" type="text/css"/>
    <style>
        .Add, .Delete, .Change {
            display: inline-block;
        }

        .Users {
            display: inline-block;
        }

        .linkRoles {
            display: inline-block;
        }
    </style>
</head>
<body onload="loadList()" bgcolor="#FFDEAD">
 <form name="users" action="Users.java" method="post" id = "formx">
     <!--<input type="hidden" name="nevidimka" id="nevidimka" value="">-->
     <div class="mainblok">
        <h1>Пользователи</h1>
        <div class="Users">
            <select name="Users" id = "Users" onchange="selectUser()">
                <option value="users" selected>Выберите пользователя</option>
            </select>
        </div>
        <p>
        <p>
        <div class="Actions"></div>

        <div class="Add"><input type="button" name="Add" value="Добавить нового пользователя" onclick="check(this.form)"></div>
        <div class="Delete"><input type="button" name="Delete" value="Удалить пользователя" onclick="document.getElementById('nevidimka').value = 'Delete'
    document.forms['users'].submit() "></div>
        <div class="Change"><input type="button" name="Change" value="Изменить данные пользователя" onclick="document.getElementById('nevidimka').value = 'Change'
    document.forms['users'].submit() "></div>
    </div>
    <div class="inputData">
        <table id = "table">

            <tr >
                <td>Имя пользователя </td>
                <td>
                    <input name="nameUser" type="text" id = "nameU">
                </td>
            </tr>
            <tr>
                <td>Email</td>
                <td>
                    <input name="email" type="text" id = "email">
                </td>
            </tr>
            <tr>
                <td>ИД роли</td>
                <td>
                    <input name="id_roles" type="text" id = "id_roles">
                </td>
            </tr>
        </table>
    </div>
    <div class="linkRoles"><h3><a href="http://localhost:8001/roles">Роли</a></h3></div>
</form>


<script type="text/javascript">
    function getValuesByValue(index) {
        return index.split(","); // преобразуем строку в массив значений
    }
    function loadList() {

        aCurrValues = getValuesByValue("<%=request.getAttribute("listUser")%>");
        var nCurrValuesCnt = aCurrValues.length;
        var oListL = document.forms["users"].elements["Users"];
        var oListOptionsCnt = oListL.options.length;
        oListL.length = 0; // удаляем все элементы из списка значений
        for (i = 0; i < nCurrValuesCnt; i++) {
            // далее мы добавляем необходимые значения в список
            if (document.createElement) {
                var newListOption = document.createElement("OPTION");
                newListOption.text = aCurrValues[i];
                newListOption.value = aCurrValues[i];
                // тут мы используем для добавления элемента либо метод IE, либо DOM
                (oListL.options.add) ? oListL.options.add(newListOption) : oListL.add(newListOption, null);
            } else {
                // для NN3.x-4.x
                oListL.options[i] = new Option(aCurrValues[i], aCurrValues[i], false, false);
            }
        }

        document.getElementById("nameU").value = <%=request.getAttribute("nameU")%>
            document.getElementById("email").value = <%=request.getAttribute("email")%>
                document.getElementById("id_roles").value = <%=request.getAttribute("id_roles")%>
    }

    function showError(container, errorMessage) {
        container.className = 'error';
        var msgElem = document.createElement('span');
        msgElem.className = "error-message";
        msgElem.innerHTML = errorMessage;
        container.appendChild(msgElem);
    }

    function resetError(container) {
        container.className = '';
        if (container.lastChild.className == "error-message") {
            container.removeChild(container.lastChild);
        }
    }

    function check(form) {
        var elems = form.elements;
        resetError(elems.nameUser.parentNode);
        if (!elems.nameUser.value) {

            showError(elems.nameUser.parentNode, ' Укажите имя пользователя.');
            resetError(elems.email.parentNode);
            if (!elems.email.value) {

                showError(elems.email.parentNode, ' Укажите email пользователя.');

                resetError(elems.id_roles.parentNode);
                if (!elems.id_roles.value) {

                    showError(elems.id_roles.parentNode, ' Укажите ИД роли.');
                }
            }
        }
        else {
            document.getElementById('nevidimka').value = 'Add'
            document.forms['users'].submit();
        }
    }

    function selectUser() {

        $.ajax({
            type: "POST",
            url: "servletDB",
            data: {type: "selectUser", name: document.getElementById("Users").value},
            dataType: "json",

            //if received a response from the server
            success: function( data, textStatus, jqXHR) {


                $('#nameU').val(data.selectUs);
                $('#email').val(data.email);
                $('#id_roles').val(data.id_roles);

            }
        })
    }
</script>
</body>
</html>