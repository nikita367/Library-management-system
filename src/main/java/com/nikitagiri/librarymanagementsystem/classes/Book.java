package com.nikitagiri.librarymanagementsystem.classes;


public class Book {
    private Integer id;
    private String title;
    private String author;
    private int available;

    public Book(Integer id, String title, String author, int available)
    {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public Integer getId() { return id; }
    public String getTitle()
    {
        return title;
    }
    public String getAuthor()
    {
        return author;
    }

    public String getAvailable() {
        if(available == 1) {
            return "true";
        }else{
            return "false";
        }
    }


}
