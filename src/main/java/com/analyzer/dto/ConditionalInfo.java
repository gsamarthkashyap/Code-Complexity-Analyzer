package com.analyzer.dto;

public class ConditionalInfo {
    private String type;
    private String content;
    private int nestingLevel;

    public ConditionalInfo(String type, String content, int nestingLevel) {
        this.type = type;
        this.content = content;
        this.nestingLevel = nestingLevel;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }
}
