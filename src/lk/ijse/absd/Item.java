package lk.ijse.absd;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
import java.sql.*;

@WebServlet(urlPatterns = "/item")
public class Item extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    private DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (PrintWriter out = resp.getWriter()) {

            resp.setContentType("application/json");

            try {
                Connection connection = ds.getConnection();

                Statement stm = connection.createStatement();
                ResultSet rst = stm.executeQuery("SELECT * FROM item");

                JsonArrayBuilder items = Json.createArrayBuilder();

                while (rst.next()) {
                    String id = rst.getString("id");
                    String description = rst.getString("description");
                    String price = rst.getString("price");
                    String qty = rst.getString("qty");

                    JsonObject item = Json.createObjectBuilder().add("id", id)
                            .add("description", description)
                            .add("price", price)
                            .add("qty", qty)
                            .build();
                    items.add(item);
                }

                out.println(items.build().toString());

                connection.close();
            } catch (Exception ex) {
                resp.sendError(500, ex.getMessage());
                ex.printStackTrace();
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        resp.setContentType("application/json");

        PrintWriter out = resp.getWriter();

        Connection connection = null;

        try {
            JsonObject item = reader.readObject();
            int id = item.getInt("id");
            String description = item.getString("description");
            String price = item.getString("price");
            double priceOne = Double.parseDouble(price);
            int qty = item.getInt("qty");
            connection = ds.getConnection();

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            pstm.setObject(1, id);
            pstm.setObject(2, description);
            pstm.setObject(3, priceOne);
            pstm.setObject(4, qty);
            boolean result = pstm.executeUpdate() > 0;

            if (result) {
                out.println("true");
            } else {
                out.println("false");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("false");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        resp.setContentType("application/json");

        PrintWriter out = resp.getWriter();

        Connection connection = null;

        try {
            JsonObject item = reader.readObject();
            int id = item.getInt("id");
            String description = item.getString("description");
            String price = item.getString("price");
            double priceOne = Double.parseDouble(price);
            int qty = item.getInt("qty");
            connection = ds.getConnection();

            PreparedStatement pstm = connection.prepareStatement(" UPDATE item SET description=?, price=?, qty=? WHERE id=?;");
            pstm.setObject(1, description);
            pstm.setObject(2, priceOne);
            pstm.setObject(3, qty);
            pstm.setObject(4, id);
            boolean result = pstm.executeUpdate() > 0;

            if (result) {
                out.println("true");
            } else {
                out.println("false");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("false");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        resp.setContentType("application/json");

        PrintWriter out = resp.getWriter();
        String id = req.getParameter("id");
        int itemId = Integer.parseInt(id);
        Connection connection = null;

        try {
            connection = ds.getConnection();

            PreparedStatement pstm = connection.prepareStatement(" DELETE FROM item WHERE id=?;");
            pstm.setObject(1, itemId);
            boolean result = pstm.executeUpdate() > 0;

            if (result) {
                out.println("true");
            } else {
                out.println("false");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("false");
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
