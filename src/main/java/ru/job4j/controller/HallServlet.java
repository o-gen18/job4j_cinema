package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.persistence.PsqlHallStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HallServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("json");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(PsqlHallStore.instOf().showOccupiedSeats());
        System.out.println("Servlet writing:" + json);
        resp.getWriter().write(json);
    }
}
