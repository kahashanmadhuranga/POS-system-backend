package lk.ijse.absd;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/deleteCustomer")
public class CustomerDelete extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        System.out.println(id);
//        resp.setContentType("application/json");
//
//        PrintWriter out = resp.getWriter();
//
//        Connection connection = null;
//
//        try {
//            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
//            pstm.setObject(1, id);
//            boolean result = pstm.executeUpdate() > 0;
//
//            if (result) {
//                out.println("true");
//            } else {
//                out.println("false");
//            }
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            out.println("false");
//        } finally {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            out.close();
//        }
    }
}