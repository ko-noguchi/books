package com.github.ko_noguchi.books;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookTest {
    @Test
    void builderWith_createsBookBuilderWithArgument() throws IOException {
        Book book = Book.builderWith("'9784866211305','これは本です','これは著者です','犬々社','20180322',1620").build();


        Book expected = Book.builder()
                .isbn("9784866211305")
                .bookName("これは本です")
                .author("これは著者です")
                .publisher("犬々社")
                .publicationDate("20180322")
                .price(1620)
                .build();
        assertThat(book).isEqualTo(expected);
    }

    @Test
    void builderWith_createsBookBuilderWithArgumentUnescapingSingleQuotations() throws IOException {
        Book book = Book.builderWith("'9784866211305','That''s it','Ma''am','R''n''B','20180322',1620").build();


        Book expected = Book.builder()
                .isbn("9784866211305")
                .bookName("That's it")
                .author("Ma'am")
                .publisher("R'n'B")
                .publicationDate("20180322")
                .price(1620)
                .build();
        assertThat(book).isEqualTo(expected);
    }

    @Test
    void builderWith_throwsExceptionWhenThereIsNotSixItems() {
        assertThatThrownBy(() -> Book.builderWith("9784866211305,これは本です,これは著者です,犬々社,20180322"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("本の情報は次の形式で入力してください: '<ISBN>','<書籍名>','<著者名>','<出版社>','<出版日>',<価格>");
    }

    @Test
    void builderWith_throwsExceptionWhenIsbnIsTooLong() {
        assertThatThrownBy(() -> Book.builderWith("97848662113056,これは本です,これは著者です,犬々社,20180322,1620"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("ISBN[97848662113056]は13桁以内で入力してください");
    }

    @Test
    void builderWith_throwsExceptionWhenBookNameIsTooLong() {
        assertThatThrownBy(() -> Book.builderWith(
                "9784866211305,123456789012345678901234567890123456789012345678901,これは著者です,犬々社,20180322,1620"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("書籍名[123456789012345678901234567890123456789012345678901]は50桁以内で入力してください");
    }

    @Test
    void builderWith_throwsExceptionWhenAuthorIsTooLong() {
        assertThatThrownBy(() -> Book.builderWith(
                "9784866211305,これは本です,1234567890123456789012345678901,犬々社,20180322,1620"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("著者名[1234567890123456789012345678901]は30桁以内で入力してください");
    }

    @Test
    void builderWith_throwsExceptionWhenPublisherIsTooLong() {
        assertThatThrownBy(() -> Book.builderWith(
                "9784866211305,これは本です,これは著者です,1234567890123456789012345678901,20180322,1620"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("出版社[1234567890123456789012345678901]は30桁以内で入力してください");
    }

    @Test
    void builderWith_throwsExceptionWhenPublicationDateIsNotInFormatOfYYYYMMDD() {
        assertThatThrownBy(() -> Book.builderWith("9784866211305,これは本です,これは著者です,犬々社,123456789,1620"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("出版日[123456789]はYYYYMMDDの形式で入力してください");
    }

    @Test
    void builderWith_throwsExceptionWhenPriceIsNotANumber() {
        assertThatThrownBy(() -> Book.builderWith("9784866211305,これは本です,これは著者です,犬々社,20180322,abc"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("価格[abc]は9桁以内の数値で入力してください");
    }

    @Test
    void builderWith_throwsExceptionWhenPriceExceedsNineDigits() {
        assertThatThrownBy(() -> Book.builderWith("9784866211305,これは本です,これは著者です,犬々社,20180322,1234567890"))
                .isInstanceOf(BookFormatException.class)
                .hasMessage("価格[1234567890]は9桁以内の数値で入力してください");
    }

    @Test
    void dump_returnsCommaSeparatedString() throws IOException {
        Book sut = Book.builder()
                .isbn("9784866211305")
                .bookName("これは本です")
                .author("これは著者です")
                .publisher("犬々社")
                .publicationDate("20180322")
                .price(1620)
                .id("0123456789abcdef")
                .createdBy("これは登録者です")
                .createdAt("20180719 193623.123")
                .updatedBy("これは更新者です")
                .updatedAt("20180720 151253.456")
                .build();


        assertThat(sut.dump()).isEqualTo(
                "'0123456789abcdef','9784866211305','これは本です','これは著者です','犬々社','20180322',1620,"
                        + "'これは登録者です','20180719 193623.123','これは更新者です','20180720 151253.456'");
    }

    @Test
    void dump_returnsStringWhereSingleQuotationEscaped() throws IOException {
        Book sut = Book.builder()
                .id("")
                .isbn("9784866211305")
                .bookName("That's it")
                .author("Ma'am")
                .publisher("R'n'B")
                .publicationDate("20180322")
                .price(1620)
                .id("0123456789abcdef")
                .createdBy("Reg'er")
                .createdAt("20180719 193623.123")
                .updatedBy("Upd'er")
                .updatedAt("20180720 151253.456")
                .build();


        assertThat(sut.dump()).isEqualTo(
                "'0123456789abcdef','9784866211305','That''s it','Ma''am','R''n''B','20180322',1620,"
                        + "'Reg''er','20180719 193623.123','Upd''er','20180720 151253.456'");
    }
}
