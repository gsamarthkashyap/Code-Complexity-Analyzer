package com.analyzer.dto;

public class LoopInfo {
    private String type;
    private String content;
    private int nestingLevel;

    public LoopInfo(String type, String content, int nestingLevel) {
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
