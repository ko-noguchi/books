package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class DeleteCommandHandlerTest {
    private static final Book.Builder DEFAULT_BOOK = Book.builder()
            .id("dummy-id")
            .isbn("9784866211305")
            .bookName("これは本です")
            .author("これは著者です")
            .publisher("犬々社")
            .publicationDate("20180322")
            .price(1620)
            .createdBy("Registererです。")
            .createdAt("20180719 194023.123")
            .updatedBy("Registererです。")
            .updatedAt("20180719 194023.123");

    private ByteArrayOutputStream consoleSpy;
    private ByteArrayOutputStream booksOutputSpy;

    @BeforeEach
    void setUp() {
        consoleSpy = new ByteArrayOutputStream();
        booksOutputSpy = new ByteArrayOutputStream();
    }

    @Test
    void match_matchesStringStartingWithDeleteAndSpace() {
        CommandHandler sut = new DeleteCommandHandler(consoleSpy, StreamBooks.empty(() -> booksOutputSpy));


        assertThat(sut.match("delete something")).isTrue();
    }

    @Test
    void match_matchesIgnoringCase() {
        CommandHandler sut = new DeleteCommandHandler(consoleSpy, StreamBooks.empty(() -> booksOutputSpy));


        assertThat(sut.match("DeLeTe something")).isTrue();
    }

    @Test
    void match_doesNotMatchOtherThanDelete() {
        CommandHandler sut = new DeleteCommandHandler(consoleSpy, StreamBooks.empty(() -> booksOutputSpy));


        assertThat(sut.match("not delete")).isFalse();
    }

    @Test
    void handle_deletesBookOfSpecifiedId() throws IOException {
        Book book1 = DEFAULT_BOOK.id("id00000000000001").build();
        Book book2 = DEFAULT_BOOK.id("id00000000000002").build();
        Book book3 = DEFAULT_BOOK.id("id00000000000003").build();
        BooksFakeSpy booksFakeSpy = new BooksFakeSpy(book1, book2, book3);

        CommandHandler sut = new DeleteCommandHandler(consoleSpy, booksFakeSpy);


        sut.handle("delete id00000000000002");


        assertThat(consoleSpy.toString()).isEqualTo("指定されたIDの書籍を削除しました。" + System.lineSeparator());
        assertThat(booksFakeSpy.committedBooks).containsExactly(book1, book3);
    }

    @Test
    void handle_outputsMessageWhenBookWithSpecifiedIdDoesNotExist() throws IOException {
        CommandHandler sut = new DeleteCommandHandler(
                consoleSpy, new BooksFakeSpy(DEFAULT_BOOK.id("id00000000000001").build()));


        sut.handle("delete id-which-does-not-exist");


        assertThat(consoleSpy.toString()).isEqualTo("指定されたIDの書籍が見つかりませんでした。" + System.lineSeparator());
        assertThat(booksOutputSpy.toString()).isEmpty();
    }
}
