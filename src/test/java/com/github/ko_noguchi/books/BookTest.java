package com.github.ko_noguchi.books;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {
  @Test
  void dump_returnsCommaSeparatedString() {
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
            "'0123456789abcdef','9784866211305','これは本です','これは著者です','犬々社','20180322',1620," +
            "'これは登録者です','20180719 193623.123','これは更新者です','20180720 151253.456'");
  }

  @Test
  void dump_returnsStringWhereSingleQuotationEscaped() {
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
            "'0123456789abcdef','9784866211305','That''s it','Ma''am','R''n''B','20180322',1620," +
            "'Reg''er','20180719 193623.123','Upd''er','20180720 151253.456'");
  }
}
