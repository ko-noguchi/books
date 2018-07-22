package com.github.ko_noguchi.books;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class StreamBooksTest {
    private static final List<String> BOOK_RECORDS = asList(
            "'id00000000000001','isbn1','book1','author1','publisher1','20180101',1000," +
                    "'creator1','20181111 111111.111','updater1','20181111 222222.222'",
            "'id00000000000002','isbn2','book2','author2','publisher2','20180202',2000," +
                    "'creator2','20181122 111111.111','updater2','20181122 222222.222'");
    private static final Book BOOK1 = Book.builder()
            .id("id00000000000001")
            .isbn("isbn1")
            .bookName("book1")
            .author("author1")
            .publisher("publisher1")
            .publicationDate("20180101")
            .price(1000)
            .createdBy("creator1")
            .createdAt("20181111 111111.11")
            .updatedBy("updater1")
            .updatedAt("20181111 222222.222").build();
    private static final Book BOOK2 = Book.builder()
            .id("id00000000000002")
            .isbn("isbn2")
            .bookName("book2")
            .author("author2")
            .publisher("publisher2")
            .publicationDate("20180202")
            .price(2000)
            .createdBy("creator2")
            .createdAt("20181122 111111.11")
            .updatedBy("updater1")
            .updatedAt("20181122 222222.222").build();

    private OutputStream target = new ByteArrayOutputStream();

    @Test
    void parse_createsBooksFromString() throws IOException {
        Books sut = StreamBooks.parse(BOOK_RECORDS, () -> target);


        assertThat(sut.getAll()).containsExactly(BOOK1, BOOK2);
    }

    @Test
    void insert_insertsBook() throws IOException {
        Books sut = StreamBooks.parse(emptyList(), () -> target);


        sut.insert(BOOK1);


        assertThat(sut.getAll()).containsExactly(BOOK1);
    }

    @Test
    void delete_deletesBookOfSpecifiedId() throws IOException {
        Books sut = StreamBooks.parse(BOOK_RECORDS, () -> target);


        boolean deleted = sut.delete("id00000000000001");


        assertThat(deleted).isTrue();
        assertThat(sut.getAll()).containsExactly(BOOK2);
    }

    @Test
    void commit_writesBooksToOutputStream() throws IOException {
        Books sut = StreamBooks.parse(BOOK_RECORDS, () -> target);


        sut.commit();


        assertThat(target.toString()).isEqualTo(String.join(System.lineSeparator(), BOOK_RECORDS) + System.lineSeparator());
    }
}
