package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExitCommandHandlerTest {
  private CommandHandler sut;

  @BeforeEach
  void setUp() {
    sut = new ExitCommandHandler();
  }

  @Test
  void match_matchesExit() {
    assertThat(sut.match("exit")).isTrue();
  }

  @Test
  void match_matchesExitIgnoringCase() {
    assertThat(sut.match("ExIt")).isTrue();
  }

  @Test
  void match_doesNotMatchOtherThanInsert() {
    assertThat(sut.match("not exit")).isFalse();
  }
}
