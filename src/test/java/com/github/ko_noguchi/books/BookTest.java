package com.github.ko_noguchi.books;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {
  @Test
  void toString_returnsCommaSeparatedString() {
    Book sut = Book.builder()
        .isbn("9784866211305")
        .bookName("これは本です")
        .author("これは著者です")
        .publisher("犬々社")
        .publicationDate("20180322")
        .price(1620)
        .build();


    assertThat(sut.toString()).isEqualTo("9784866211305,これは本です,これは著者です,犬々社,20180322,1620");
  }
}
