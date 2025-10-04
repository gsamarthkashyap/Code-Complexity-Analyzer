package com.analyzer.dto;

import java.util.List;

public class ComplexityResponse {
    private int loopCount;
    private int maxNestingLevel;
    private String timeComplexity;
    private List<FunctionInfo> functions;
    private List<LoopInfo> loops;
    private List<ConditionalInfo> conditionals;

    // Getters
    public int getLoopCount() {
        return loopCount;
    }

    public int getMaxNestingLevel() {
        return maxNestingLevel;
    }

    public String getTimeComplexity() {
        return timeComplexity;
    }

    public List<FunctionInfo> getFunctions() {
        return functions;
    }

    public List<LoopInfo> getLoops() {
        return loops;
    }

    public List<ConditionalInfo> getConditionals() {
        return conditionals;
    }

    // Setters
    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public void setMaxNestingLevel(int maxNestingLevel) {
        this.maxNestingLevel = maxNestingLevel;
    }

    public void setTimeComplexity(String timeComplexity) {
        this.timeComplexity = timeComplexity;
    }

    public void setFunctions(List<FunctionInfo> functions) {
        this.functions = functions;
    }

    public void setLoops(List<LoopInfo> loops) {
        this.loops = loops;
    }

    public void setConditionals(List<ConditionalInfo> conditionals) {
        this.conditionals = conditionals;
    }
}
