package com.example.vinhomeproject.config;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeConfig extends StdConverter<String, LocalTime> {

    @Override
    public LocalTime convert(String s) {
        return LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
