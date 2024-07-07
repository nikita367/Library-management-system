package com.nikitagiri.librarymanagementsystem.servlets;

import com.nikitagiri.librarymanagementsystem.classes.DatabaseConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");

        try{
            connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM books");
            ResultSet resultSet = statement.executeQuery();
            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject bookJson = new JSONObject();
                bookJson.put("id", resultSet.getInt("id"));
                bookJson.put("title", resultSet.getString("title"));
                bookJson.put("author", resultSet.getString("author"));
                bookJson.put("available", resultSet.getInt("available"));
                jsonArray.put(bookJson);
            }

            out.print(jsonArray.toString());
        }catch (SQLException e){
            e.printStackTrace();
        }catch (Exception e){

            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookTitle = request.getParameter("title");
        String bookAuthor = request.getParameter("author");
        String  bookAvailable = request.getParameter("available");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        try{
            connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO books(title,author,available) VALUES(?,?,?)");
            statement.setString(1, bookTitle);
            statement.setString(2, bookAuthor);
            statement.setInt(3, Integer.valueOf(bookAvailable));

            int insertData = statement.executeUpdate();
            if (insertData == 1) {
                out.println("book_added_successfully");
            } else {
                out.println("error_while_adding_book");
            }
        }catch (SQLException e){
            e.printStackTrace();
//            out.println(e.getMessage());

        }catch (Exception e){

            e.printStackTrace();
//            out.println(e.getMessage());

        }finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

}
