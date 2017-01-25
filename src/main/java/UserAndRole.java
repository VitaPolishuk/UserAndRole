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
public class UserAndRole extends HttpServlet {
    private static final String  username = "root";
    private static final String  password = "root";
    private static final String  URL = "jdbc:mysql://localhost:3306/mysql";
    public void init() throws ServletException  {


         try {
             DBConnection db = new DBConnection();
            Connection conn = db.getConnection(URL,username,password);
            String query = "select * from usersandroles.users";
            Statement statement = conn.createStatement();
            ResultSet resSet = statement.executeQuery(query);
            while(resSet.next()){
                int id = resSet.getInt("id_user");
                String name = resSet.getString("name_user");
                String email = resSet.getString("email");
                int idrole = resSet.getInt("id_roles");
                System.out.println("id_user = "+id+", name = "+name+", email = "+ email+", id_role = "+idrole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.getRequestDispatcher("users.jsp").forward(request,response);


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doPostGet(request, response);

    }
    public void doPostGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        doGet(request,response);
    }
}
