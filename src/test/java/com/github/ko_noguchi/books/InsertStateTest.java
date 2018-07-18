package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.ko_noguchi.books.InsertState.*;
import static org.assertj.core.api.Assertions.assertThat;

class InsertStateTest {
  private static final String STRING_13 = "1234567890123";
  private static final String STRING_50 = "12345678901234567890123456789012345678901234567890";
  private static final String STRING_30 = "123456789012345678901234567890";
  private static final String VALID_DATE = "20180717";
  private static final String VALID_PRICE = "2400";

  private Book.Builder builder;

  @BeforeEach
  void setUp() {
    builder = Book.builder();
  }

  @Test
  void isbn() {
    assertThat(ISBN.next(STRING_13 + "a", builder)).isEqualTo(ISBN_ERROR);
    assertThat(ISBN_ERROR.next("", builder)).isEqualTo(ISBN);
    assertThat(ISBN.next(STRING_13, builder)).isEqualTo(BOOK_NAME);
    assertThat(builder.build()).isEqualTo(Book.builder().isbn(STRING_13).build());
  }

  @Test
  void bookName() {
    assertThat(BOOK_NAME.next(STRING_50 + "a", builder)).isEqualTo(BOOK_NAME_ERROR);
    assertThat(BOOK_NAME_ERROR.next("", builder)).isEqualTo(BOOK_NAME);
    assertThat(BOOK_NAME.next(STRING_50, builder)).isEqualTo(AUTHOR);
    assertThat(builder.build()).isEqualTo(Book.builder().bookName(STRING_50).build());
  }

  @Test
  void author() {
    assertThat(AUTHOR.next(STRING_30 + "a", builder)).isEqualTo(AUTHOR_ERROR);
    assertThat(AUTHOR_ERROR.next("", builder)).isEqualTo(AUTHOR);
    assertThat(AUTHOR.next(STRING_30, builder)).isEqualTo(PUBLISHER);
    assertThat(builder.build()).isEqualTo(Book.builder().author(STRING_30).build());
  }

  @Test
  void publisher() {
    assertThat(PUBLISHER.next(STRING_30 + "a", builder)).isEqualTo(PUBLISHER_ERROR);
    assertThat(PUBLISHER_ERROR.next("", builder)).isEqualTo(PUBLISHER);
    assertThat(PUBLISHER.next(STRING_30, builder)).isEqualTo(PUBLICATION_DATE);
    assertThat(builder.build()).isEqualTo(Book.builder().publisher(STRING_30).build());
  }

  @Test
  void publicationDate() {
    assertThat(PUBLICATION_DATE.next(VALID_DATE + "a", builder)).isEqualTo(PUBLICATION_DATE_ERROR);
    assertThat(PUBLICATION_DATE_ERROR.next("", builder)).isEqualTo(PUBLICATION_DATE);
    assertThat(PUBLICATION_DATE.next(VALID_DATE, builder)).isEqualTo(PRICE);
    assertThat(builder.build()).isEqualTo(Book.builder().publicationDate(VALID_DATE).build());
  }

  @Test
  void price() {
    assertThat(PRICE.next(VALID_PRICE + "a", builder)).isEqualTo(PRICE_ERROR);
    assertThat(PRICE_ERROR.next("", builder)).isEqualTo(PRICE);
    assertThat(PRICE.next(VALID_PRICE, builder)).isEqualTo(COMPLETE);
    assertThat(builder.build()).isEqualTo(Book.builder().price(Integer.parseInt(VALID_PRICE)).build());
  }
}
