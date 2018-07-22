package com.github.ko_noguchi.books;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

class Book {
    private static final Pattern YYYYMMDD = Pattern.compile("^\\d{8}$");
    private static final Pattern PRICE = Pattern.compile("^\\d{1,9}$");

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

    static Book parse(String text) throws IOException {
        List<String> csv = CsvUtils.parse(text);
        return builder()
                .id(csv.get(0))
                .isbn(csv.get(1))
                .bookName(csv.get(2))
                .author(csv.get(3))
                .publisher(csv.get(4))
                .publicationDate(csv.get(5))
                .price(Integer.parseInt(csv.get(6)))
                .createdBy(csv.get(7))
                .createdAt(csv.get(8))
                .updatedBy(csv.get(9))
                .updatedAt(csv.get(10))
                .build();
    }

    static Builder builder() {
        return new Builder();
    }

    static Builder builderForInsertion(String text) throws IOException {
        List<String> csv = CsvUtils.parse(text);
        if (csv.size() != 6) {
            throw new BookFormatException(
                    "本の情報は次の形式で入力してください: '<ISBN>','<書籍名>','<著者名>','<出版社>','<出版日>',<価格>");
        }

        return builderForInsertion(csv);
    }

    private static Builder builderForInsertion(List<String> csv) {
        return new Builder()
                .isbn(getLengthBoundString(csv, 0, 13, "ISBN"))
                .bookName(getLengthBoundString(csv, 1, 50, "書籍名"))
                .author(getLengthBoundString(csv, 2, 30, "著者名"))
                .publisher(getLengthBoundString(csv, 3, 30, "出版社"))
                .publicationDate(getPatternMatchingString(csv, 4, YYYYMMDD, "出版日", "YYYYMMDDの形式"))
                .price(Integer.parseInt(getPatternMatchingString(csv, 5, PRICE, "価格", "9桁以内の数値")));
    }

    private static String getLengthBoundString(
            List<String> csv, int index, int maxLength, String name) {
        String item = csv.get(index);
        if (item.length() > maxLength) {
            throw new BookFormatException(String.format("%s[%s]は%d桁以内で入力してください", name, item, maxLength));
        }
        return item;
    }

    private static String getPatternMatchingString(
            List<String> csv, int index, Pattern pattern, String name, String format) {
        String item = csv.get(index);
        if (!pattern.matcher(item).matches()) {
            throw new BookFormatException(String.format("%s[%s]は%sで入力してください", name, item, format));
        }
        return item;
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

    String getId() {
        return id;
    }

    String dump() throws IOException {
        return CsvUtils.toCsv(
                id, isbn, bookName, author, publisher, publicationDate, price,
                createdBy, createdAt, updatedBy, updatedAt);
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
        return "Book{"
                + "id='" + id + '\''
                + ", isbn='" + isbn + '\''
                + ", bookName='" + bookName + '\''
                + ", author='" + author + '\''
                + ", publisher='" + publisher + '\''
                + ", publicationDate='" + publicationDate + '\''
                + ", price=" + price
                + ", createdBy='" + createdBy + '\''
                + ", createdAt='" + createdAt + '\''
                + ", updatedBy='" + updatedBy + '\''
                + ", updatedAt='" + updatedAt + '\''
                + '}';
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
