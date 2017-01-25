import com.mysql.fabric.jdbc.FabricMySQLDriver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;

@WebServlet("/roles")
public class Roles extends HttpServlet {
    private static final String  username = "root";
    private static final String  password = "root";
    private static final String  URL = "jdbc:mysql://localhost:3306/mysql";
    public void init() throws ServletException  {
        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection(URL,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection(URL,username,password);
            String query = "select id_roles from usersandroles.roles";
            Statement statement = conn.createStatement();
            ResultSet resSet = statement.executeQuery(query);
            String name = "";
            while(resSet.next()){
                if(name.equals("")){name = resSet.getString("id_roles"); }
                else{name = name +"," +resSet.getString("id_roles");}
            }
            request.setAttribute("listRoles", name);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("roles.jsp").forward(request,response);


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doPostGet(request, response);

    }
    public void doPostGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String str = request.getParameter("nevidimka").toString();


        doGet(request,response);
    }
}
