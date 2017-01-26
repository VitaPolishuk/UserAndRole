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
        String selectUs = request.getParameter("name");
        System.out.println(str+ "   "+selectUs);
        switch(str){
            case "selectUser": {
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
                System.out.println(selectUs+"   "+email+"   "+id_roles);
                try (PrintWriter out = response.getWriter()) {
                    JSONObject jsonEnt = new JSONObject();

                    jsonEnt.put("selectUs", selectUs );
                    jsonEnt.put("email", email);
                    jsonEnt.put("id_roles",""+id_roles);

                    out.print(jsonEnt.toString());

                break;
            }}
            case "Add":{

                String nameUser = request.getParameter("nameUser");
                System.out.println(nameUser + " name user");
                String email = request.getParameter("email");
                System.out.println(email + " email");
                int id_roles = Integer.parseInt(request.getParameter("id_roles"));
                System.out.println(id_roles + "   id roles");

                PreparedStatement statement = conn.prepareStatement("insert into usersandroles.users (name_user,email,id_roles) values(?,?,?)");

                statement.setString(1,nameUser);
                statement.setString(2,email);
                statement.setInt(3,id_roles);

                statement.executeUpdate();
                doGet(request,response);
                break;
            }
            case "Delete": {

                break;
            }
            case "Change":{


                break;
            }

        }

    }

}
