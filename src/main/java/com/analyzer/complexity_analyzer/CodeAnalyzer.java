package com.analyzer.complexity_analyzer;

import com.analyzer.ComplexityAnalyzerBaseListener;
import com.analyzer.ComplexityAnalyzerParser;
import com.analyzer.dto.*;

import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;

public class CodeAnalyzer extends ComplexityAnalyzerBaseListener {

    private int loopCount = 0;
    private int maxNestingLevel = 0;
    private int currentNestingLevel = 0;
    private boolean hasLogarithmicLoop = false;
    private boolean hasLogarithmicRecursion = false; // NEW: Track logarithmic recursion

    private String currentFunction = null;

    private final List<LoopInfo> loops = new ArrayList<>();
    private final List<FunctionInfo> functions = new ArrayList<>();
    private final List<ConditionalInfo> conditionals = new ArrayList<>();

    // Track recursive calls count per function name
    private final Map<String, Integer> recursionCalls = new HashMap<>();

    public static ParseResult analyze(ComplexityAnalyzerParser.ProgramContext tree) {
        CodeAnalyzer listener = new CodeAnalyzer();
        ParseTreeWalker.DEFAULT.walk(listener, tree);

        String timeComplexity = listener.estimateTimeComplexity();

        return new ParseResult(
            listener.loopCount,
            listener.maxNestingLevel,
            timeComplexity,
            null, // spaceComplexity removed/unused
            listener.functions,
            listener.loops,
            listener.conditionals
        );
    }

    // Function declaration
    @Override
    public void enterFunctionDefinition(ComplexityAnalyzerParser.FunctionDefinitionContext ctx) {
        currentFunction = ctx.Identifier().getText();
        int line = ctx.getStart().getLine();
        functions.add(new FunctionInfo(currentFunction, line));
        // initialize recursion counter for this function
        recursionCalls.put(currentFunction, 0);
    }

    @Override
    public void exitFunctionDefinition(ComplexityAnalyzerParser.FunctionDefinitionContext ctx) {
        // reset currentFunction when leaving a function body
        currentFunction = null;
    }

    // Detect recursive calls
    @Override
    public void enterFunctionCall(ComplexityAnalyzerParser.FunctionCallContext ctx) {
        String functionCalled = ctx.Identifier().getText();
        // If inside a function and the call target equals the current function name -> recursion
        if (currentFunction != null && functionCalled.equals(currentFunction)) {
            recursionCalls.put(currentFunction, recursionCalls.getOrDefault(currentFunction, 0) + 1);
            
            // NEW: Check if this recursive call has logarithmic pattern
            String callText = ctx.getText();
            if (isLogarithmicRecursion(callText)) {
                hasLogarithmicRecursion = true;
            }
        }
    }

    // Entering for loop
    @Override
    public void enterForStatement(ComplexityAnalyzerParser.ForStatementContext ctx) {
        loopCount++;
        currentNestingLevel++;
        maxNestingLevel = Math.max(maxNestingLevel, currentNestingLevel);

        String loopText = ctx.getText();

        if (isLogarithmicUpdate(loopText)) {
            hasLogarithmicLoop = true;
        }

        loops.add(new LoopInfo("for", loopText, currentNestingLevel));
    }

    @Override
    public void exitForStatement(ComplexityAnalyzerParser.ForStatementContext ctx) {
        currentNestingLevel--;
    }

    // Entering while loop
    @Override
    public void enterWhileStatement(ComplexityAnalyzerParser.WhileStatementContext ctx) {
        loopCount++;
        currentNestingLevel++;
        maxNestingLevel = Math.max(maxNestingLevel, currentNestingLevel);

        String loopText = ctx.getText();

        if (isLogarithmicUpdate(loopText)) {
            hasLogarithmicLoop = true;
        }

        loops.add(new LoopInfo("while", loopText, currentNestingLevel));
    }

    @Override
    public void exitWhileStatement(ComplexityAnalyzerParser.WhileStatementContext ctx) {
        currentNestingLevel--;
    }

    @Override
    public void enterIfStatement(ComplexityAnalyzerParser.IfStatementContext ctx) {
        conditionals.add(new ConditionalInfo("if", ctx.getText(), currentNestingLevel));
    }

    // NEW: Listen to expression statements to catch assignments inside loops
    @Override
    public void enterExpressionStatement(ComplexityAnalyzerParser.ExpressionStatementContext ctx) {
        // Only process if we're inside a loop (currentNestingLevel > 0)
        if (currentNestingLevel > 0) {
            String exprText = ctx.getText();
            if (isLogarithmicUpdate(exprText)) {
                hasLogarithmicLoop = true;
            }
        }
    }

    private boolean isLogarithmicUpdate(String loopText) {
        // Check for compound assignment operators
        if (loopText.contains("*=2") || loopText.contains("*= 2") || 
            loopText.contains("*=  2") || loopText.contains("/=2") || 
            loopText.contains("/= 2") || loopText.contains("/=  2")) {
            return true;
        }
        
        // Check for simple patterns first (more permissive)
        if (loopText.contains("*2") && loopText.contains("=")) {
            return true;
        }
        if (loopText.contains("/2") && loopText.contains("=")) {
            return true;
        }
        
        // Use regex to match patterns with flexible whitespace
        // Matches: i = i * 2, i=i*2, j = j / 2, etc.
        return loopText.matches(".*[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[a-zA-Z_][a-zA-Z0-9_]*\\s*\\*\\s*2.*") ||
               loopText.matches(".*[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[a-zA-Z_][a-zA-Z0-9_]*\\s*/\\s*2.*");
    }

    /**
     * NEW: Detect if a recursive call follows a logarithmic pattern
     * (dividing the problem space by half)
     */
    private boolean isLogarithmicRecursion(String callText) {
        // Patterns that indicate dividing problem space by half
        return callText.contains("/2") ||
               callText.contains("/ 2") ||
               callText.contains("mid-1") ||
               callText.contains("mid+1") ||
               callText.contains("mid -1") ||
               callText.contains("mid +1") ||
               callText.contains("mid- 1") ||
               callText.contains("mid+ 1") ||
               callText.matches(".*\\(right-left\\)/2.*") ||
               callText.matches(".*\\(left\\+right\\)/2.*") ||
               callText.matches(".*\\(.*\\+.*\\)/2.*") ||
               callText.matches(".*\\(.*-.*\\)/2.*");
    }

    /**
     * Estimate time complexity using the gathered metrics.
     * Note: This is heuristic-based and intentionally conservative/simple.
     */
    private String estimateTimeComplexity() {
        boolean anyRecursive = recursionCalls.values().stream().anyMatch(c -> c > 0);

        // Check for O(1) - no loops, no recursion
        if (loopCount == 0 && !anyRecursive) return "O(1)";
        
        // Check for O(log n) - single logarithmic loop, no recursion
        if (hasLogarithmicLoop && loopCount == 1 && !anyRecursive) return "O(log n)";

        // If there is recursion, inspect recursionCounts per function (heuristics)
        if (anyRecursive) {
            for (Map.Entry<String, Integer> entry : recursionCalls.entrySet()) {
                int calls = entry.getValue();
                if (calls <= 0) continue;

                // Simple heuristics:
                // NEW: Check for logarithmic recursion FIRST (binary search pattern)
                if (calls == 1 && hasLogarithmicRecursion && loopCount == 0) {
                    return "O(log n)";
                }
                // Single recursive call in function body (common n-step recursion) -> O(n)
                else if (calls == 1 && loopCount == 0) {
                    return "O(n)";
                } 
                // Multiple recursive calls without loops (Fibonacci-like) -> O(2^n)
                else if (calls > 1 && loopCount == 0) {
                    return "O(2^n)";
                } 
                // Single recursive call + logarithmic loop -> O(n log n) (heuristic)
                else if (calls == 1 && hasLogarithmicLoop) {
                    return "O(n log n)";
                } 
                // Any recursion together with loops -> raise polynomial degree by nesting
                else if (calls >= 1 && loopCount > 0) {
                    return "O(n^" + (maxNestingLevel + 1) + ")";
                }
            }
        }

        // Non-recursive loop-based complexity:
        if (loopCount > 1 && hasLogarithmicLoop) return "O(n log n)";

        return switch (maxNestingLevel) {
            case 1 -> "O(n)";
            case 2 -> "O(n^2)";
            case 3 -> "O(n^3)";
            default -> maxNestingLevel > 3 ? "O(n^" + maxNestingLevel + ")" : "O(?)";
        };
    }
}