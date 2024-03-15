package sample.Library_spr.models;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String imageUrl;
    private String author;

    // dates of reservation
    private String dateFrom;
    private String dateTo;

    public Book() {
    }

    public Book(String title, String description, String imageUrl, String author, String dateFrom, String dateTo) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.author = author;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    // Getters and setters

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
