import com.mysql.fabric.jdbc.FabricMySQLDriver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;

@WebServlet("/")
public class Users extends HttpServlet  {
    private static final String  username = "root";
    private static final String  password = "root";

    private static final String CONTENT_TYPE =
            "text/html; charset=utf-8";
    public static Connection conn ;

    public void init() {
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
        try {

            String query = "select name_user from usersandroles.users";
            Statement statement = conn.createStatement();
            //загрузка списка пользователей
            ResultSet resSet = statement.executeQuery(query);
            String name = "";
            while(resSet.next()){
                if(name.equals("")){name = resSet.getString("name_user"); }
                else{name = name +"," +resSet.getString("name_user");}
            }
            request.setAttribute("listUser", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            String query = "select name_roles from usersandroles.roles";
            Statement statement = conn.createStatement();
            //загрузка списка пользователей
            ResultSet resSet = statement.executeQuery(query);
            String name = "";
            while(resSet.next()){
                if(name.equals("")){name = resSet.getString("name_roles"); }
                else{name = name +"," +resSet.getString("name_roles");}
            }
            request.setAttribute("listRole", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("users.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        try {
            doPostGet(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void doPostGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException, SQLException, ClassNotFoundException {

    }
    }

