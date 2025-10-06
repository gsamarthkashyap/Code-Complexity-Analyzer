# Code Complexity Analyzer 🚀

## About The Project

Code Complexity Analyzer is an intelligent tool that automatically analyzes your code and determines its time complexity in Big O notation. Built with ANTLR for robust parsing and custom heuristic algorithms, it helps developers understand and optimize their algorithm performance.

Whether you're learning algorithms, preparing for technical interviews, or optimizing production code, this tool provides instant complexity analysis for your functions, loops, and recursive algorithms.

## ✨ Features

- **Automatic Complexity Detection**: Analyzes code and determines Big O notation automatically
- **Multiple Complexity Classes**: Supports O(1), O(log n), O(n), O(n log n), O(n²), O(n³), O(2^n)
- **Loop Analysis**: Detects nested loops, logarithmic patterns, and tracks nesting levels
- **Recursion Detection**: Identifies recursive functions and determines their complexity patterns
- **Logarithmic Pattern Recognition**: Detects logarithmic loops (i *= 2, i /= 2) and binary search patterns
- **Detailed Metrics**: Provides comprehensive information including loop count, nesting levels, and function details
- **REST API**: Simple HTTP endpoints for easy integration into your workflow
- **Real-time Analysis**: Get instant feedback on your code's performance characteristics

## 🎯 Supported Complexity Classes

| Complexity | Description | Example Algorithms |
|------------|-------------|-------------------|
| **O(1)** | Constant time | Variable assignments, array access |
| **O(log n)** | Logarithmic | Binary search, logarithmic loops |
| **O(n)** | Linear | Single loop, linear search, linear recursion |
| **O(n log n)** | Linearithmic | Merge sort, quick sort (average case) |
| **O(n²)** | Quadratic | Bubble sort, nested loops |
| **O(n³)** | Cubic | Matrix multiplication, triple nested loops |
| **O(2^n)** | Exponential | Fibonacci recursion, subset generation |

## 🛠️ Technology Stack

- **Java 17+** - Core programming language
- **Spring Boot 3.x** - REST API framework and application structure
- **ANTLR 4** - Parser generator for robust code analysis
- **Maven** - Build automation and dependency management

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation & Running

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/complexity-analyzer.git
cd complexity-analyzer
```

2. **Build the project**
```bash
mvn clean install
```

3. **Run the application**
```bash
mvn spring-boot:run
```

The server will start on `http://localhost:8080`

### Quick Test

Use Postman or cURL to test the analyzer:

```bash
curl -X POST http://localhost:8080/api/analyze \
  -H "Content-Type: application/json" \
  -d '{"code": "for(int i = 0; i < n; i++) { }"}'
```

## 🔍 How It Works

The analyzer follows a multi-step process:

1. **Parsing**: ANTLR parses the input code into an Abstract Syntax Tree (AST)
2. **Tree Walking**: A custom listener traverses the AST, identifying patterns
3. **Metric Collection**: Tracks loops, recursion, nesting levels, and function calls
4. **Pattern Detection**: Identifies logarithmic patterns, recursive structures, and nesting
5. **Complexity Estimation**: Applies heuristic rules to determine the final Big O notation

### Detection Heuristics

- **O(1)**: No loops, no recursion detected
- **O(log n)**: Logarithmic loops (`i *= 2`, `i /= 2`) or binary search recursion patterns
- **O(n)**: Single loop or single recursive call with linear reduction
- **O(n log n)**: Linear loop containing logarithmic operations
- **O(n^k)**: k levels of nested loops
- **O(2^n)**: Multiple recursive calls (Fibonacci-like patterns)



## 🛣️ Future Enhancements

- [ ] **Space Complexity Analysis** - Analyze memory usage patterns
- [ ] **Best/Average/Worst Case** - Distinguish between different scenarios
- [ ] **Multi-language Support** - Python, C++, JavaScript support
- [ ] **Advanced Recursion Patterns** - Master theorem implementation
- [ ] **Array Operations Tracking** - Detect array manipulation patterns
- [ ] **Confidence Scores** - Provide reliability metrics for estimates
- [ ] **Web Frontend Interface** - Interactive UI for easier testing
- [ ] **Code Optimization Suggestions** - Recommend improvements
- [ ] **Batch Analysis** - Analyze multiple files at once
- [ ] **IDE Integration** - VS Code and IntelliJ plugins


## **Write code that works, optimize code that matters, and always know your complexity.** 💡✨
