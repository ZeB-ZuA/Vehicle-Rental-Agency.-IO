package com.epam.rd.autocode.assessment.basics.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CsvStorageImpl implements CsvStorage {
    private String encoding;
    private String quoteCharacter;
    private String valuesDelimiter;
    private boolean headerLine;
    public CsvStorageImpl() {
        this.encoding = "UTF-8";
        this.quoteCharacter = "\"";
        this.valuesDelimiter = ",";
        this.headerLine = true;
    }

    public CsvStorageImpl(Map<String, String> props) {
        encoding = props.getOrDefault("encoding", "UTF-8");
        quoteCharacter = props.getOrDefault("quoteCharacter", "\"");
        valuesDelimiter = props.getOrDefault("valuesDelimiter", ",");
        headerLine = Boolean.parseBoolean(props.getOrDefault("headerLine", "true"));
    }

    @Override
    public <T> List<T> read(InputStream source, Function<String[], T> mapper) throws IOException {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(source, encoding))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine && headerLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }
                String[] parts = line.split(valuesDelimiter);
                T object = mapper.apply(parts);
                result.add(object);
            }
        }
        return result;
    }
    @Override
    public <T> void write(OutputStream dest, List<T> values, Function<T, String[]> mapper) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(dest))) {
            for (T value : values) {
                String[] fields = mapper.apply(value);
                String line = String.join(",", fields);
                writer.write(line);
                writer.newLine();
            }
        }
    }

}
