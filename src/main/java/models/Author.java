package models;

/**
 * Represents an Author entity for the Bookstore API.
 * This class is used for creating, updating, and validating author data in tests.
 */
public class Author {

    // Unique identifier for the author
    private Integer id;

    // Identifier of the book associated with the author
    private Integer idBook;

    // Author's first name
    private String firstName;

    // Author's last name
    private String lastName;

    // -------------------- GETTERS AND SETTERS --------------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
