package com.example.mainweb2.servlets;

import com.example.mainweb2.Point;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.System.getLogger;

@WebServlet(name = "AreaCheckServlet" , value = "/check")
public class AreaCheckServlet extends HttpServlet {

    private List<Point> resultList;
    @Override
    public void init() {

        resultList = new ArrayList<>();
        getServletContext().setAttribute("resultList", resultList);
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        final long start = System.nanoTime();



        // Получаем или создаем список результатов внутри сессии
        resultList = (List<Point>) session.getAttribute("resultList");

        if (resultList == null) {
            resultList = new ArrayList<>();
            session.setAttribute("resultList", resultList);
        }
        double x =0 ;
        double y = 0;
        double r =0;
        try {
             x = Double.parseDouble(request.getParameter("x").replace(',', '.'));
             y = Double.parseDouble(request.getParameter("y").replace(',', '.'));
             r = Double.parseDouble(request.getParameter("r").replace(',', '.'));
        } catch (NumberFormatException e) {
            String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("kk:mm:ss"));
            final long end = System.nanoTime();
            final long execTime = end - start;
            final boolean res = false;
            Point resultPoint = new Point(x, y, r, res, currentTime, execTime);
            resultPoint.setError(true);
            resultList.add(resultPoint);
            request.setAttribute("resultList", resultList);
            request.getRequestDispatcher("/result.jsp").forward(request, response);

            return;
        }
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("kk:mm:ss"));

        // Выполняем проверку попадания точки в область и сохраняем результат
         final boolean res = checkArea(x, y, r);




        final long end = System.nanoTime();
        final long execTime = end - start;
        Point resultPoint = new Point(x, y, r, res, currentTime, execTime);
        resultList.add(resultPoint);
        request.setAttribute("resultList", resultList);
        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    public boolean checkSquare(double x, double y, double r) {
        return  x <= 0 && y <= 0 &&  x >= -r/2 && y >= -r;
    }

    public boolean checkCircle(double x, double y, double r)  {
        return (x >= 0 && y >= 0 && (Math.pow(x,2) + Math.pow(y,2) <= (Math.pow(r,2))/4 ));
    }

    public boolean checkTriangle(double x, double y, double r) {
        return (x >= 0 && y<=0 && x <= r/2 && y >= 2 * x - r && y>= -r);
    }




    public boolean checkArea(double x, double y, double r) {
        return checkCircle(x, y, r) || checkTriangle(x, y, r) || checkSquare(x, y, r);

    }
}
