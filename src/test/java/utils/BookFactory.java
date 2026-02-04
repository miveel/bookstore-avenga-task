package utils;

import models.Book;

/**
 * Factory class to create Book objects for API tests.
 */
public class BookFactory {

    /**
     * Creates a Book with full payload.
     *
     * @param title     Book title
     * @param pageCount Number of pages
     * @return Book object
     */
    public static Book createFullBook(String title, int pageCount) {
        Book book = new Book();
        book.setTitle(title);
        book.setDescription("Created via API test");
        book.setPageCount(pageCount);
        book.setExcerpt("Short excerpt");
        book.setPublishDate(TestDateUtils.getTodayIsoDate());
        return book;
    }

    /**
     * Creates a Book with minimal payload.
     * Only pageCount and publishDate are set.
     *
     * @return Book object
     */
    public static Book createMinimalBook() {
        Book book = new Book();
        book.setPageCount(1);
        book.setPublishDate(TestDateUtils.getTodayIsoDate());
        return book;
    }

    /**
     * Creates a Book without title (nullable field scenario).
     *
     * @param pageCount Number of pages
     * @return Book object
     */
    public static Book createBookWithoutTitle(int pageCount) {
        Book book = new Book();
        book.setDescription("No title book");
        book.setPageCount(pageCount);
        book.setExcerpt("Excerpt");
        book.setPublishDate(TestDateUtils.getTodayIsoDate());
        return book;
    }
}
