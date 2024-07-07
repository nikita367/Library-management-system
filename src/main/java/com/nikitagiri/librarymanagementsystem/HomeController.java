package com.nikitagiri.librarymanagementsystem;


import com.nikitagiri.librarymanagementsystem.classes.API;
import com.nikitagiri.librarymanagementsystem.classes.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeController {
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, Integer> id;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String > author;
    @FXML
    private TableColumn<Book, String> available;

    @FXML
    private TextField bookTitle;

    @FXML
    private TextField bookAuthor;

    @FXML
    private CheckBox bookAvailable;

    @FXML
    private Label responseLabel;


    private final ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {

        id.setCellValueFactory(new PropertyValueFactory<Book,Integer>("id"));
        title.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
        available.setCellValueFactory(new PropertyValueFactory<Book,String>("available"));

        tableView.setItems(bookList);

        // Fetch books and populate the table
        try {
            String json = API.getData("http://localhost:8080/librarymanagement/api/books","GET");
            List<Book> books = parseBooks(json);
            bookList.addAll(books);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private List<Book> parseBooks(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Book book = new Book(jsonObject.getInt("id"),jsonObject.getString("title"),jsonObject.getString("author"),jsonObject.getInt("available"));
            books.add(book);
        }

        return books;
    }
    @FXML
    private void addBook() {
        String bookTitle = this.bookTitle.getText();
        String bookAuthor = this.bookAuthor.getText();
        Boolean bookAvailable = this.bookAvailable.isSelected();
        int availableInt = 0;
        if(bookAvailable.equals(true)){
            availableInt = 1;
        }
        try{
            String postData = "title=" + bookTitle+ "&author="+bookAuthor + "&available="+ availableInt;

            String response = API.sendPost("http://localhost:8080/librarymanagement/api/books",postData);
            if(response.equals("book_added_successfully")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Book");
                alert.setHeaderText(null);
                alert.setContentText("Book added successfully");
                alert.showAndWait();
                this.bookTitle.clear();
                this.bookAuthor.clear();
                this.bookAvailable.setSelected(false);
                refreshTable();
            }else{
                responseLabel.setText(response);
                responseLabel.setStyle("-fx-text-fill: red");
            }
        }catch (Exception e){
            e.printStackTrace();
            responseLabel.setText("Error while adding book");
        }

    }

    private void refreshTable() {
        try {
            String json = API.getData("http://localhost:8080/librarymanagement/api/books","GET");
            List<Book> books = parseBooks(json);

            // Clear old data and add new data
            bookList.clear();
            bookList.addAll(books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
