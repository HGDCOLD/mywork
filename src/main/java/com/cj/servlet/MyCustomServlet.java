package com.cj.servlet;

import com.cj.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "MyCustomServlet", urlPatterns = {"/testServlet/test1"})
public class MyCustomServlet extends javax.servlet.http.HttpServlet {
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println("MyCustomServlet doPost method called");
//        resp.setContentType("text/html");
//        resp.setStatus(200);
//        objectMapper.writeValue(resp.getWriter(), Result.success("Hello World! Servlet"));
//    }

    public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("MyCustomServlet doGet method called");
        response.setContentType("text/html");
        response.setStatus(200);
        objectMapper.writeValue(response.getWriter(), Result.success("Hello World! Servlet"));
    }

    public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("MyCustomServlet doPost method called");
        response.setContentType("text/html");
        response.setStatus(200);
        objectMapper.writeValue(response.getWriter(), Result.success("Hello World! Servlet"));
    }
}
