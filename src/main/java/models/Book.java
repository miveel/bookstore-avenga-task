package models;

/**
 * Represents a Book entity for the Bookstore API.
 * This class is used for creating, updating, and validating book data in tests.
 */
public class Book {
    // Unique identifier for the book
    private int id;

    // Title of the book
    private String title;

    // Description or summary of the book
    private String description;

    // Total number of pages in the book
    private int pageCount;

    // Short excerpt or snippet from the book
    private String excerpt;

    // Publish date in ISO 8601 format (e.g., "2026-02-03T00:00:00")
    private String publishDate;

    // -------------------- GETTERS AND SETTERS --------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
