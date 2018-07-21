package com.github.ko_noguchi.books;

import java.util.regex.Pattern;

enum InsertState {
    ISBN("ISBNを入力してください。(最大13文字)", false) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            if (input.length() > 13) {
                return ISBN_ERROR;
            }
            builder.isbn(input);
            return BOOK_NAME;
        }
    },
    ISBN_ERROR("ISBNは13文字以内で入力してください。", true) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            return ISBN;
        }
    },
    BOOK_NAME("書籍名を入力してください。(最大50文字)", false) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            if (input.length() > 50) {
                return BOOK_NAME_ERROR;
            }
            builder.bookName(input);
            return AUTHOR;
        }
    },
    BOOK_NAME_ERROR("書籍名は50文字以内で入力してください。", true) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            return BOOK_NAME;
        }
    },
    AUTHOR("著者名を入力してください。(最大30文字)", false) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            if (input.length() > 30) {
                return AUTHOR_ERROR;
            }
            builder.author(input);
            return PUBLISHER;
        }
    },
    AUTHOR_ERROR("著者名は30文字以内で入力してください。", true) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            return AUTHOR;
        }
    },
    PUBLISHER("出版社を入力してください。(最大30文字)", false) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            if (input.length() > 30) {
                return PUBLISHER_ERROR;
            }
            builder.publisher(input);
            return PUBLICATION_DATE;
        }
    },
    PUBLISHER_ERROR("出版社は30文字以内で入力してください。", true) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            return PUBLISHER;
        }
    },
    PUBLICATION_DATE("出版日を入力してください。(YYYYMMDD)", false) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            if (!PUBLICATION_DATE_PATTERN.matcher(input).matches()) {
                return PUBLICATION_DATE_ERROR;
            }
            builder.publicationDate(input);
            return PRICE;
        }
    },
    PUBLICATION_DATE_ERROR("出版日はYYYYMMDD形式で入力してください。", true) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            return PUBLICATION_DATE;
        }
    },
    PRICE("価格を入力してください。(9桁以内)", false) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            if (!PRICE_PATTERN.matcher(input).matches()) {
                return PRICE_ERROR;
            }
            builder.price(Integer.parseInt(input));
            return COMPLETE;
        }
    },
    PRICE_ERROR("価格は9桁以内で入力してください。", true) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            return PRICE;
        }
    },
    COMPLETE("書籍を登録しました。", false) {
        @Override
        InsertState next(String input, Book.Builder builder) {
            throw new RuntimeException("No next state");
        }
    };

    private static final Pattern PUBLICATION_DATE_PATTERN = Pattern.compile("^\\d{8}$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d{1,9}$");

    private final String text;
    private final boolean error;

    InsertState(String text, boolean error) {
        this.text = text;
        this.error = error;
    }

    abstract InsertState next(String input, Book.Builder builder);

    boolean isError() {
        return error;
    }

    String text() {
        return text;
    }
}
