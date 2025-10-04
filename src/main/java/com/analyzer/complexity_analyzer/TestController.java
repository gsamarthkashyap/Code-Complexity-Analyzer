package com.analyzer.complexity_analyzer;

import com.analyzer.service.CodeParserService;
import com.analyzer.dto.ComplexityResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private CodeParserService codeParserService;

    @PostMapping("/analyze")
    public ResponseEntity<ComplexityResponse> analyzeCode(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        ComplexityResponse response = codeParserService.parseAndAnalyze(code);
        return ResponseEntity.ok(response);
    }
}
