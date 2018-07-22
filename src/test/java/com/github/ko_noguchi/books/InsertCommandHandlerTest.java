package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InsertCommandHandlerTest {
    private ByteArrayOutputStream consoleSpy;
    private BooksFakeSpy booksFakeSpy;
    private ClockStub clockStub;
    private IdGeneratorStub idGeneratorStub;

    @BeforeEach
    void setUp() {
        consoleSpy = new ByteArrayOutputStream();
        booksFakeSpy = new BooksFakeSpy();
        clockStub = new ClockStub(LocalDateTime.of(2018, 7, 19, 19, 40, 23, 123_000_000));
        idGeneratorStub = new IdGeneratorStub("stub-id");
    }

    @Test
    void match_matchesStringStartingWithInsertAndSpace() {
        CommandHandler sut =
                new InsertCommandHandler("", consoleSpy, booksFakeSpy, clockStub, idGeneratorStub);


        assertThat(sut.match("insert something")).isTrue();
    }

    @Test
    void match_matchesIgnoringCase() {
        CommandHandler sut =
                new InsertCommandHandler("", consoleSpy, booksFakeSpy, clockStub, idGeneratorStub);


        assertThat(sut.match("inSerT something")).isTrue();
    }

    @Test
    void match_doesNotMatchOtherThanInsert() {
        CommandHandler sut =
                new InsertCommandHandler("", consoleSpy, booksFakeSpy, clockStub, idGeneratorStub);


        assertThat(sut.match("not insert")).isFalse();
    }

    @Test
    void handle_handlesValidInput() throws IOException {
        CommandHandler sut =
                new InsertCommandHandler("Registererです。", consoleSpy, booksFakeSpy, clockStub, idGeneratorStub);


        sut.handle("insert '9784866211305','これは本です','これは著者です','犬々社','20180322',1620");


        assertThat(consoleSpy.toString()).isEqualTo("書籍を登録しました。" + System.lineSeparator());
        assertThat(booksFakeSpy.committedBooks).containsExactly(Book.builder()
                .id("stub-id")
                .isbn("9784866211305")
                .bookName("これは本です")
                .author("これは著者です")
                .publisher("犬々社")
                .publicationDate("20180322")
                .price(1620)
                .createdBy("Registererです。")
                .createdAt("20180719 194023.123")
                .updatedBy("Registererです。")
                .updatedAt("20180719 194023.123")
                .build());
    }

    @Test
    void handle_handlesInvalidInput() throws IOException {
        CommandHandler sut =
                new InsertCommandHandler("Registererです。", consoleSpy, booksFakeSpy, clockStub, idGeneratorStub);


        sut.handle("insert invalid-string");


        assertThat(consoleSpy.toString()).startsWith("本の情報は次の形式で入力してください");
        assertThat(booksFakeSpy.committedBooks).isEmpty();
    }
}
