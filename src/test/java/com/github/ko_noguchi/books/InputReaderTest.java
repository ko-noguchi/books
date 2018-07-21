package com.github.ko_noguchi.books;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class InputReaderTest {
    private CommandHandlerStubSpy inputHandlerStubSpy;
    private String[] defaultHandler_argument_line = new String[1];
    private InputReader sut;

    @BeforeEach
    void setUp() {
        inputHandlerStubSpy = new CommandHandlerStubSpy();
        sut = new InputReader(l -> defaultHandler_argument_line[0] = l);
    }

    @Test
    void next_handlesWithHandlerWhenMatches() throws IOException {
        inputHandlerStubSpy.match_returnValue = true;
        sut.addHandler(inputHandlerStubSpy);


        sut.next("line");


        assertThat(inputHandlerStubSpy.match_argument_line).isEqualTo("line");
        assertThat(inputHandlerStubSpy.handle_argument_line).isEqualTo("line");
    }

    @Test
    void next_doesNotHandleWhenDoesNotMatches() throws IOException {
        inputHandlerStubSpy.match_returnValue = false;
        sut.addHandler(inputHandlerStubSpy);


        sut.next("line");

        assertThat(inputHandlerStubSpy.match_argument_line).isEqualTo("line");
        assertThat(inputHandlerStubSpy.handle_argument_line).isNull();
    }

    @Test
    void next_handleWithDefaultHandlerWhenDoesNotMatches() throws IOException {
        sut.next("does not match");


        assertThat(defaultHandler_argument_line[0]).isEqualTo("does not match");
    }
}
