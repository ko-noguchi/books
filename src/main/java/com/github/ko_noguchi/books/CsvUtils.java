package com.github.ko_noguchi.books;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class CsvUtils {
    private static final CSVFormat CSV_FORMAT =
            CSVFormat.DEFAULT.withQuote('\'').withQuoteMode(QuoteMode.NON_NUMERIC);

    static List<String> parse(String csv) throws IOException {
        List<String> result = new ArrayList<>();
        CSVParser.parse(csv, CSV_FORMAT).getRecords().get(0).forEach(result::add);
        return result;
    }

    static String toCsv(Object... values) throws IOException {
        StringBuilder sb = new StringBuilder();
        CSVPrinter csvPrinter = new CSVPrinter(sb, CSV_FORMAT);
        csvPrinter.printRecord(values);
        return sb.toString().trim();
    }
}
