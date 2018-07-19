package com.github.ko_noguchi.books;

import java.util.Objects;

public class Book {
  private final String id;
  private final String isbn;
  private final String bookName;
  private final String author;
  private final String publisher;
  private final String publicationDate;
  private final int price;
  private final String createdBy;
  private final String createdAt;
  private final String updatedBy;
  private final String updatedAt;

  static Builder builder() {
    return new Builder();
  }

  private Book(
      String id, String isbn, String bookName, String author,
      String publisher, String publicationDate, int price,
      String createdBy, String createdAt, String updatedBy, String updatedAt) {
    this.id = id;
    this.isbn = isbn;
    this.bookName = bookName;
    this.author = author;
    this.publisher = publisher;
    this.publicationDate = publicationDate;
    this.price = price;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.updatedBy = updatedBy;
    this.updatedAt = updatedAt;
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
    return "Book{" +
            "isbn='" + isbn + '\'' +
            ", bookName='" + bookName + '\'' +
            ", author='" + author + '\'' +
            ", publisher='" + publisher + '\'' +
            ", publicationDate='" + publicationDate + '\'' +
            ", price=" + price +
            '}';
  }

  String dump() {
    return String.join(",",
        quote(id), quote(isbn), quote(bookName), quote(author),
        quote(publisher), quote(publicationDate), String.valueOf(price),
        quote(createdBy), quote(createdAt), quote(updatedBy), quote(updatedAt));
  }

  private static String quote(String text) {
    return "'" + text.replace("'", "''") + "'";
  }

  static class Builder {
    private String isbn;
    private String bookName;
    private String author;
    private String publisher;
    private String publicationDate;
    private int price;
    private String id;
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;

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

    Builder id(String id) {
      this.id = id;
      return this;
    }

    Builder createdBy(String createdBy) {
      this.createdBy = createdBy;
      return this;
    }

    Builder createdAt(String createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    Builder updatedBy(String updatedBy) {
      this.updatedBy = updatedBy;
      return this;
    }

    Builder updatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    Book build() {
      return new Book(
          id, isbn, bookName, author, publisher, publicationDate, price,
          createdBy, createdAt, updatedBy, updatedAt);
    }
  }
}
