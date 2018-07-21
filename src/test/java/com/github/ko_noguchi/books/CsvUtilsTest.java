package com.github.ko_noguchi.books;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CsvUtilsTest {
    @Test
    void parse_parsesCsv() throws IOException {
        assertThat(CsvUtils.parse("'a','b,c','d''e',123"))
                .containsExactly("a", "b,c", "d'e", "123");
    }

    @Test
    void toCsv_generatesCsvFromValues() throws IOException {
        assertThat(CsvUtils.toCsv("a", "b,c", "d'e", 123))
                .isEqualTo("'a','b,c','d''e',123");
    }
}
