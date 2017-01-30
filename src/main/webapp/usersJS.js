function getValuesByValue(index) {
    return index.split(","); // преобразуем строку в массив значений
}
function loadList(str) {

    aCurrValues = getValuesByValue(str);
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
}
function loadListRoles(str) {

    aCurrValues = getValuesByValue(str);
    var nCurrValuesCnt = aCurrValues.length;
    var oListL = document.forms["users"].elements["name_roles"];
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
        }
    }
    else {
        addUserTable();
    }
}
function saveChange() {
    var index = document.getElementById("nevidimka").value;
    var table = document.getElementById("tableAllUsers");
    $.ajax({
        type: "POST",
        url: "servletDB",
        data: {
            type: "ChangeUser", selectUserChange: table.rows[index].cells[0].innerHTML,
            name: document.getElementById("nameU").value,
            email: document.getElementById("email").value,
            name_roles: document.getElementById("name_roles").value
        },
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            table.rows[index].cells[0].innerHTML = data.id_user;
            table.rows[index].cells[1].innerHTML = data.name;
            table.rows[index].cells[2].innerHTML = data.email;
            table.rows[index].cells[3].innerHTML = data.name_roles;
            // $(# table.rows[index].cells[0].innerHTML).val("");
            $('#nameU').val("");
            $('#email').val("");
            //  loadList(data.listUserChange);

        }
    })
}
function changeUser(th) {

    var index = th.parentNode.parentNode.rowIndex;
    document.getElementById("nevidimka").value = index;
    var table = document.getElementById("tableAllUsers");
    document.getElementById("nameU").value = table.rows[index].cells[1].innerHTML;
    document.getElementById("email").value = table.rows[index].cells[2].innerHTML;
    document.getElementById("name_roles").value = table.rows[index].cells[3].innerHTML;


}
function selectUser() {
    $.ajax({
        type: "POST",
        url: "servletDB",
        data: {type: "selectUser", name: document.getElementById("Users").value},
        dataType: "json",

        success: function (data, textStatus, jqXHR) {
            ;
            $('#nameU').val(data.selectUs);
            $('#email').val(data.email);
            $('#name_roles').val(data.name_roles);
        }
    })
}
function downloadTable(stroka) {

    aCurrValues = getValuesByValue(stroka);
    var cells = 5;
    var rows = aCurrValues.length / (cells - 1);
    var cnt = 0;
    var table = document.getElementById("tableAllUsers");

    for (var j = 0; j < rows; j++) {
        // Создать строку
        var row = document.createElement("tr");
        row.setAttribute('class', 'tr1 stroka');
        for (var i = 0; i < cells; i++) {

            if (i == (cells - 1)) {
                var cell = document.createElement("td");
                cell.setAttribute('class', 'tr1')
                //первая кнопочка
                field1 = document.createElement('input');
                field1.type = 'button';
                field1.value = "[/]";

                field1.setAttribute('onclick', 'changeUser(this)');

                cell.appendChild(field1);
                //вторая кнопочка
                field2 = document.createElement('input');
                field2.type = 'button';
                field2.value = "[x]";
                field2.setAttribute('onclick', 'deleteUser(this)');
                cell.appendChild(field2);
                row.appendChild(cell);
            }
            else {
                var cell = document.createElement("td");
                cell.setAttribute('class', 'tr1');
                if (i == 0) {
                    cell.setAttribute('style', 'visibility: hidden');
                }

                // Здесь нужно будет заполнить своими данными ячейку
                var cellText = document.createTextNode(aCurrValues[cnt]);

                cell.appendChild(cellText);
                row.appendChild(cell);
                cnt++;
            }
        }

        // Добавить строку в конец элемента tbody
        table.appendChild(row);
    }
}
function deleteUser(th) {
    var index = th.parentNode.parentNode.rowIndex;
    var table = document.getElementById("tableAllUsers");

    $.ajax({
        type: "POST",
        url: "servletDB",
        data: {type: "DelUser", selectUserDel: table.rows[index].cells[0].innerHTML},
        dataType: "json",

        success: function (data, textStatus, jqXHR) {
        }
    })
    table.deleteRow(index);
}
function addUserTable() {
    //alert("addUserTable");
    $.ajax({
        type: "POST",
        url: "servletDB",
        data: {
            type: "AddUser",
            nameUser: document.getElementById("nameU").value,
            email: document.getElementById("email").value,
            name_roles: document.getElementById("name_roles").value
        },
        dataType: "json",

        success: function (data, textStatus, jqXHR) {
            table = document.getElementById("tableAllUsers");
            aCurrValues = getValuesByValue(data.addTableUser);
            alert(aCurrValues);
            cells = 5;
            cnt = 0;

            var row = document.createElement("tr");
            row.setAttribute('class', 'tr1 stroka');
            for (var i = 0; i < cells; i++) {
                if (i == (cells - 1)) {
                    var cell = document.createElement("td");
                    cell.setAttribute('class', 'tr1')
                    //первая кнопочка
                    field1 = document.createElement('input');
                    field1.type = 'button';
                    field1.value = "[/]";
                    field1.setAttribute('onclick', 'changeUser(this)');
                    cell.appendChild(field1);
                    //вторая кнопочка
                    field2 = document.createElement('input');
                    field2.type = 'button';
                    field2.value = "[x]";
                    field2.setAttribute('onclick', 'deleteUser(this)');
                    cell.appendChild(field2);

                    row.appendChild(cell);
                }

                else {
                    var cell = document.createElement("td");
                    cell.setAttribute('class', 'tr1')
                    if (i == 0) {
                        cell.setAttribute('style', 'visibility: hidden');
                    }

                    var cellText = document.createTextNode(aCurrValues[cnt]);

                    cell.appendChild(cellText);
                    row.appendChild(cell);
                    cnt++;
                }
            }
            //   row.appendChild();
            table.appendChild(row);
            $('#nameU').val("");
            $('#email').val("");
        }

    })
}
