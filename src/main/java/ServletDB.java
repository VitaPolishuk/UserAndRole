import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/servletDB")
public class ServletDB extends HttpServlet {
    private static final String  username = "root";
    private static final String  password = "root";
    private static final String CONTENT_TYPE =
            "text/html; charset=utf-8";
    Connection conn;
    public void init() throws ServletException  {
        try {
            this.conn = new DBConnection().getConnection(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            {
                try {
                    doPostGet(request, response);
                } catch (ServletException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    public void doPostGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        response.setContentType(CONTENT_TYPE);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        String str = request.getParameter("type");

       // System.out.println(str+ "   "+selectUs);
        switch(str){
            case "selectUser": {
                String selectUs = request.getParameter("name");
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                //String selectUser = request.getParameter("Users");
                String data = "select * from usersandroles.users where name_user = " + "\""+selectUs+"\"";

                Statement statement = conn.createStatement();
                //загрузка данных о выбранном пользователе
                ResultSet resSet = statement.executeQuery(data);
                resSet.next();
                String email = resSet.getString("email");
                int id_roles = resSet.getInt("id_roles");

                PreparedStatement statement1 = conn.prepareStatement("select name_roles from usersandroles.roles where id_roles = "+id_roles);

                ResultSet resSet1 = statement1.executeQuery();
                resSet1.next();
                String name_rol = resSet1.getString("name_roles");
                System.out.println(selectUs+"   "+email+"   "+name_rol);
                try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();

                    jsonEnt.put("selectUs", selectUs );
                    jsonEnt.put("email", email);
                    jsonEnt.put("name_roles",""+name_rol);


                    out.print(jsonEnt.toString());

                break;
            }}
            case "selectRole": {
                String selectRole = request.getParameter("name");

                try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();

                    jsonEnt.put("name_roles", selectRole );

                    out.print(jsonEnt.toString());

                    break;
                }}
            case "AddUser":{

                String nameUser = request.getParameter("nameUser");
                System.out.println(nameUser + " name user");
                String email = request.getParameter("email");
                System.out.println(email + " email");
                String name_roles = request.getParameter("name_roles");
                System.out.println(name_roles + "   name roles");
                PreparedStatement statement = conn.prepareStatement("select id_roles from usersandroles.roles where name_roles = "+"\""+name_roles+"\"");

                ResultSet resSet = statement.executeQuery();
                resSet.next();
                int id_rol = resSet.getInt("id_roles");
                PreparedStatement statement1 = conn.prepareStatement("insert into usersandroles.users (name_user,email,id_roles) values(?,?,?)");

                statement1.setString(1,nameUser);
                statement1.setString(2,email);
                statement1.setInt(3,id_rol);

                statement1.executeUpdate();

                PreparedStatement statement2 = conn.prepareStatement("select id_user from usersandroles.users where name_user = "+"\""+nameUser+"\" AND  email = "+"\""+email+"\" ");

                ResultSet resSet2 = statement2.executeQuery();
                resSet2.next();
                int id_user = resSet2.getInt("id_user");
               String name = id_user+","+ nameUser+","+email+","+name_roles;

            try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();

                    jsonEnt.put("addTableUser", name );



                    out.print(jsonEnt.toString());
               // doGet(request,response);
                break;
            }}
            case "AddRole":{

                String nameRole = request.getParameter("nameRole");

                PreparedStatement statement = conn.prepareStatement("insert into usersandroles.roles (name_roles) values(?)");

                statement.setString(1,nameRole);

                statement.executeUpdate();
                String name =  returnAllRoles();

                try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();

                    jsonEnt.put("listRoleUpdate", name );


                    out.print(jsonEnt.toString());
                    // doGet(request,response);
                    break;
                }}
            case "ChangeUser":{

                String selectUserChange = request.getParameter("selectUserChange");
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String name_roles = request.getParameter("name_roles");
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PreparedStatement statement = conn.prepareStatement("select id_roles from usersandroles.roles where name_roles = "+"\""+name_roles+"\"");
                ResultSet resSet = statement.executeQuery();
                resSet.next();
                int id_rol = resSet.getInt("id_roles");
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE  usersandroles.users  set name_user = ?, email = ?, id_roles = ? where id_user =?");
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,email);
                preparedStatement.setInt(3,id_rol);
                preparedStatement.setString(4,selectUserChange);
                preparedStatement.executeUpdate();
                try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();
                    jsonEnt.put("id_user", selectUserChange );
                    jsonEnt.put("name", name );
                    jsonEnt.put("email", email );
                    jsonEnt.put("name_roles", name_roles );
                    out.print(jsonEnt.toString());
                   // doGet(request,response);
                    break;
                }}
            case "ChangeRole":{

                String selectRoleChange = request.getParameter("selectRoleChange");
                String name = request.getParameter("name");
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE  usersandroles.roles  set name_roles = ? where name_roles =?");
                preparedStatement.setString(1,name);
                preparedStatement.setString(2,selectRoleChange);
                preparedStatement.executeUpdate();
                try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();
                    String nam =  returnAllRoles();
                    jsonEnt.put("listRoleChange", nam );
                    out.print(jsonEnt.toString());
                    // doGet(request,response);
                    break;
                }}
            case "DelUser":{
                String selectUserDel = request.getParameter("selectUserDel");
                PreparedStatement preparedStatement = conn.prepareStatement("Delete from usersandroles.users where id_user = ?");
                preparedStatement.setString(1,selectUserDel);
                preparedStatement.executeUpdate();

            }
            case "DelRole":{
                String selectUserDel = request.getParameter("selectRoleDel");
                PreparedStatement preparedStatement = conn.prepareStatement("Delete from usersandroles.roles where name_roles = ?");
                preparedStatement.setString(1,selectUserDel);
                preparedStatement.executeUpdate();
                try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();
                    String nam =  returnAllRoles();
                    jsonEnt.put("listRoleDel", nam );
                    out.print(jsonEnt.toString());
                    //doGet(request,response);
                    break;
                }
            }
        }
    }
    public String returnAllUsers() throws SQLException {

        Statement statement = conn.createStatement();
        String query = "select name_user from usersandroles.users";
        ResultSet resSet = statement.executeQuery(query);
        String nam = "";
        while(resSet.next()){
            if(nam.equals("")){nam = resSet.getString("name_user"); }
            else{nam = nam +"," +resSet.getString("name_user");}
        }

       return nam;
    }
    public String returnAllRoles() throws SQLException {

        Statement statement = conn.createStatement();
        String query = "select name_roles from usersandroles.roles";
        ResultSet resSet = statement.executeQuery(query);
        String nam = "";
        while(resSet.next()){
            if(nam.equals("")){nam = resSet.getString("name_roles"); }
            else{nam = nam +"," +resSet.getString("name_roles");}
        }

        return nam;
    }

}
