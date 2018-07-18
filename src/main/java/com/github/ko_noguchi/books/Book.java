package com.github.ko_noguchi.books;

import java.util.Objects;

public class Book {
  private final String isbn;
  private final String bookName;
  private final String author;
  private final String publisher;
  private final String publicationDate;
  private final int price;

  static Builder builder() {
    return new Builder();
  }

  private Book(
      String isbn, String bookName, String author,
      String publisher, String publicationDate, int price) {
    this.isbn = isbn;
    this.bookName = bookName;
    this.author = author;
    this.publisher = publisher;
    this.publicationDate = publicationDate;
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Book book = (Book) o;
    return price == book.price
        && Objects.equals(isbn, book.isbn)
        && Objects.equals(bookName, book.bookName)
        && Objects.equals(author, book.author)
        && Objects.equals(publisher, book.publisher)
        && Objects.equals(publicationDate, book.publicationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isbn, bookName, author, publisher, publicationDate, price);
  }

  @Override
  public String toString() {
    return String.join(",",
        isbn, bookName, author, publisher, publicationDate, String.valueOf(price));
  }

  static class Builder {
    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String publicationDate;
    private int price;

    private Builder() {
    }

    Builder isbn(String isbn) {
      this.isbn = isbn;
      return this;
    }

    Builder bookName(String bookName) {
      this.bookName = bookName;
      return this;
    }

    Builder author(String author) {
      this.author = author;
      return this;
    }

    Builder publisher(String publisher) {
      this.publisher = publisher;
      return this;
    }

    Builder publicationDate(String publicationDate) {
      this.publicationDate = publicationDate;
      return this;
    }

    Builder price(int price) {
      this.price = price;
      return this;
    }

    Book build() {
      return new Book(isbn, bookName, author, publisher, publicationDate, price);
    }
  }
}
