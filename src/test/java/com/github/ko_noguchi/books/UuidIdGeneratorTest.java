package com.github.ko_noguchi.books;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class UuidIdGeneratorTest {
  @Test
  void generate16CharacterId_generatesUniqueId() {
    UuidIdGenerator sut = new UuidIdGenerator();


    assertThat(IntStream.range(0, 100_000)
        .mapToObj(i -> sut.generate16CharacterId())
        .distinct()
        .count()
    ).isEqualTo(100_000);
  }
}
