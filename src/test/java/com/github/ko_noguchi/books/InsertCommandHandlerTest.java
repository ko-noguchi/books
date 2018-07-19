package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class InsertCommandHandlerTest {
  private ByteArrayOutputStream consoleSpy;
  private ByteArrayInputStream registererDummy;
  private ByteArrayOutputStream booksSpy;

  @BeforeEach
  void setUp() {
    consoleSpy = new ByteArrayOutputStream();
    registererDummy = new ByteArrayInputStream(new byte[0]);
    booksSpy = new ByteArrayOutputStream();
  }

  @Test
  void match_matchesInsert() {
    CommandHandler sut =
            new InsertCommandHandler(new Scanner(""), consoleSpy, registererDummy, booksSpy);


    assertThat(sut.match("insert")).isTrue();
  }

  @Test
  void match_matchesInsertIgnoringCase() {
    CommandHandler sut =
            new InsertCommandHandler(new Scanner(""), consoleSpy, registererDummy, booksSpy);


    assertThat(sut.match("inSerT")).isTrue();
  }

  @Test
  void match_doesNotMatchOtherThanInsert() {
    CommandHandler sut =
        new InsertCommandHandler(new Scanner(""), consoleSpy, registererDummy, booksSpy);


    assertThat(sut.match("not insert")).isFalse();
  }

  @Test
  void handle_handlesValidInputs() throws IOException {
    handle_handlesInsertion(
        asList(
            "9784866211305",
            "これは本です",
            "これは著者です",
            "犬々社",
            "20180322",
            "1620"),
        asList("ISBNを入力",
            "書籍名を入力",
            "著者名を入力",
            "出版社を入力",
            "出版日を入力",
            "価格を入力",
            "書籍を登録"),
        "'9784866211305','これは本です','これは著者です','犬々社','20180322',1620" + System.lineSeparator());
  }

  @Test
  void handle_handlesInvalidInputs() throws IOException {
    String invalidIsbn = "THIS_IS_INVALID_ISBN";
    String invalidPublicationDate = "INVALID_PUBLICATION_DATE";
    String invalidPrice = "INVALID_PRICE";

    handle_handlesInsertion(
        asList(
            invalidIsbn,
            "9784866211305",
            "これは本です",
            "これは著者です",
            "犬々社",
            invalidPublicationDate,
            "20180322",
            invalidPrice,
            "1620",
            "trailing_input"),
        asList("ISBNを入力",
            "ISBNは",
            "ISBNを入力",
            "書籍名を入力",
            "著者名を入力",
            "出版社を入力",
            "出版日を入力",
            "出版日は",
            "出版日を入力",
            "価格を入力",
            "価格は",
            "価格を入力",
            "書籍を登録"),
            "'9784866211305','これは本です','これは著者です','犬々社','20180322',1620" + System.lineSeparator());
  }

  private void handle_handlesInsertion(
      List<String> inputs, List<String> consoleOutputs, String fileOutput) throws IOException {
    String joinedInput = String.join(System.lineSeparator(), inputs);
    InputStream registererStub = new ByteArrayInputStream("登録者です。".getBytes());

    CommandHandler sut =
        new InsertCommandHandler(new Scanner(joinedInput), consoleSpy, registererStub, booksSpy);


    sut.handle("insert");


    String spaceOrNewLine = String.format(" |%s", System.lineSeparator());
    assertThat(consoleSpy.toString().split(spaceOrNewLine)).satisfies(outputs -> {
      assertThat(outputs).hasSameSizeAs(consoleOutputs);

      for (int i = 0; i < outputs.length; i++) {
        assertThat(outputs[i]).startsWith(consoleOutputs.get(i));
      }
    });
    assertThat(booksSpy.toString()).isEqualTo(fileOutput);
  }
}
