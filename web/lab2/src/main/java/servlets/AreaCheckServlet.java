package servlets;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import point.PointManager;
import point.PointsArr;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.*;

@WebServlet("/checkArea")
public class AreaCheckServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Привет от сервлета проверки попадания");
        Gson gson = new Gson();

        String xStr = request.getParameter("x");
        String yStr = request.getParameter("y");
        String rStr = request.getParameter("r");
        System.out.println(xStr);
        if (xStr == null || yStr == null || rStr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не все параметры были переданы");
            return;
        }

        try {
            System.out.println(rStr);
            System.out.println(xStr);
            System.out.println(yStr);
            double x = Double.parseDouble(xStr.replaceAll("\"", ""));
            double y = Double.parseDouble(yStr.replaceAll("\"", ""));
            double r = Double.parseDouble(rStr);

            System.out.println("Получены значения: x = " + x + ", y = " + y + ", r = " + r);

            PointManager point = new PointManager(x, y, r);

            HttpSession session = request.getSession();
            PointsArr points = (PointsArr) session.getAttribute("points");

            if (points == null) {
                points = new PointsArr();
                session.setAttribute("points", points);
            }

            points.addPoint(point);

            session.setAttribute("points", points);

            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("x", point.getX());
            jsonResponse.put("y", point.getY());
            jsonResponse.put("r", point.getR());
            jsonResponse.put("isInArea", point.isInside(point.getX(), point.getY(), point.getR()));
            System.out.println(jsonResponse);

            PrintWriter out = response.getWriter();
            out.print(gson.toJson(jsonResponse));
            out.flush();

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный формат данных. X, Y и R должны быть числами.");
            System.err.println("Ошибка парсинга чисел: " + e.getMessage());
        }
    }
}