
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Роли</title>
</head>
<body onload = "loadList()">
<form name="roles" action="Roles.java" method="post">
<select name = "Roles">
    <option   value="roles" selected>Выберите роль</option>

</select>
<div class = "actions">
    <p>Выберите действие</p>
    <p> <input  type="submit" name = "Add" value="Добавить" onclick="">
    <p> <input  type="submit" name = "Delete" value="Удалить" onclick="">
    <p> <input  type="submit" name = "Change" value="Изменить" onclick="">
</div>
</form>

<script type="text/javascript">
    function getValuesByValue(index){

        return index.split(","); // преобразуем строку в массив значений
    }
    function loadList() {

        aCurrValues =   getValuesByValue("<%=request.getAttribute("listRoles")%>");
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
</script>
</body>
</html>
