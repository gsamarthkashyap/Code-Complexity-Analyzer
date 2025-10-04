package com.analyzer.service;

import com.analyzer.ComplexityAnalyzerLexer;
import com.analyzer.ComplexityAnalyzerParser;
import com.analyzer.complexity_analyzer.CodeAnalyzer;
import com.analyzer.dto.ComplexityResponse;
import com.analyzer.complexity_analyzer.ParseResult;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

@Service
public class CodeParserService {

    public ComplexityResponse parseAndAnalyze(String code) {
        // Step 1: Convert input code to ANTLR CharStream
        CharStream input = CharStreams.fromString(code);

        // Step 2: Create Lexer and Parser from ANTLR grammar
        ComplexityAnalyzerLexer lexer = new ComplexityAnalyzerLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ComplexityAnalyzerParser parser = new ComplexityAnalyzerParser(tokens);

        // Step 3: Parse the code to generate a parse tree
        ParseTree tree = parser.program();

        // Step 4: Analyze the parse tree
        ParseResult result = CodeAnalyzer.analyze((ComplexityAnalyzerParser.ProgramContext) tree);

        // Step 5: Build and return the response
        ComplexityResponse response = new ComplexityResponse();
        response.setLoopCount(result.getLoopCount());
        response.setMaxNestingLevel(result.getMaxNestingDepth());
        response.setTimeComplexity(result.getTimeComplexity());
        response.setFunctions(result.getFunctions());
        response.setLoops(result.getLoops());
        response.setConditionals(result.getConditionals());

        return response;
    }
}
