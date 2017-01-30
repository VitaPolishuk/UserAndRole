import com.mysql.fabric.jdbc.FabricMySQLDriver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;

@WebServlet("/r")
public class UsersAndRoles extends HttpServlet {
    private static final String  username = "root";
    private static final String  password = "root";
    Connection conn;
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.getRequestDispatcher("usersAndRoles.jsp").forward(request,response);
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
