package ru.job4j.controller;

import ru.job4j.persistence.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("username");
        String tel = req.getParameter("phone");
        int row = Integer.parseInt(req.getParameter("row"));
        int seat = Integer.parseInt(req.getParameter("seat"));
        if (!PsqlStore.instOf().bookPlace(name, tel, row, seat)) {
            resp.sendError(500);
        }
    }
}
