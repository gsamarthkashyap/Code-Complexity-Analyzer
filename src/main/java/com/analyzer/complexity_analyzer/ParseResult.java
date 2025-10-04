package com.analyzer.complexity_analyzer;

import com.analyzer.dto.FunctionInfo;
import com.analyzer.dto.LoopInfo;
import com.analyzer.dto.ConditionalInfo;

import java.util.List;

public class ParseResult {
    private int loopCount;
    private int maxNestingDepth;
    private String timeComplexity;
    private List<FunctionInfo> functions;
    private List<LoopInfo> loops;
    private List<ConditionalInfo> conditionals;

    public ParseResult(
        int loopCount,
        int maxNestingDepth,
        String timeComplexity,
        String unusedSpaceComplexity, // to match legacy signature, ignored
        List<FunctionInfo> functions,
        List<LoopInfo> loops,
        List<ConditionalInfo> conditionals
    ) {
        this.loopCount = loopCount;
        this.maxNestingDepth = maxNestingDepth;
        this.timeComplexity = timeComplexity;
        this.functions = functions;
        this.loops = loops;
        this.conditionals = conditionals;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public int getMaxNestingDepth() {
        return maxNestingDepth;
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
}
