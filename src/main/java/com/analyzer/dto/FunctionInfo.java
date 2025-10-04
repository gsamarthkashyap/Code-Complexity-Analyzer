package com.analyzer.dto;

public class FunctionInfo {
    private String name;
    private int line;

    public FunctionInfo(String name, int line) {
        this.name = name;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public int getLine() {
        return line;
    }
}
