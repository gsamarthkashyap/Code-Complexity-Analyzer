grammar ComplexityAnalyzer;

program: (functionDefinition | statement)* EOF;

// Function Definitions (e.g., int main() { ... } or function foo() { ... })
functionDefinition
    : (type | 'void') Identifier '(' parameterList? ')' block
    | 'function' Identifier '(' parameterList? ')' block
    ;

parameterList
    : (type Identifier) (',' type Identifier)*
    ;

// Statements (loop, if, return, etc.)
statement
    : forStatement
    | whileStatement
    | ifStatement
    | functionCall ';'
    | returnStatement
    | expressionStatement           // NEW: Added expression statement
    | block
    | ';'
    ;

// NEW: Expression Statement (allows assignments and other expressions as statements)
expressionStatement
    : expression ';'
    ;

// Block of code
block
    : '{' statement* '}'
    ;

// Loop Types
forStatement
    : 'for' '(' forInit? ';' expression? ';' expression? ')' block
    ;

whileStatement
    : 'while' '(' expression ')' block
    ;

// Conditionals
ifStatement
    : 'if' '(' expression ')' block ('else' block)?
    ;

// Function Call
functionCall
    : Identifier '(' argumentList? ')'
    ;

argumentList
    : expression (',' expression)*
    ;

// Return
returnStatement
    : 'return' expression? ';'
    ;

// Initialization inside for loop
forInit
    : type Identifier '=' expression
    | Identifier '=' expression              // NEW: Allow assignment without type in for init
    ;

// Expression (basic arithmetic and variables)
expression
    : Identifier '=' expression                          # assignmentExpr
    | Identifier op=('+='|'-='|'*='|'/=') expression    # compoundAssignExpr
    | expression op=('*'|'/') expression                 # mulDivExpr
    | expression op=('+'|'-') expression                 # addSubExpr
    | expression op=('<' | '<=' | '>' | '>=') expression # compareExpr
    | expression op=('==' | '!=') expression             # equalityExpr
    | Identifier                                         # idExpr
    | Number                                             # numberExpr
    | '(' expression ')'                                 # parenExpr
    | functionCall                                       # funcCallExpr
    ;

// Data Types
type
    : 'int' | 'float' | 'double' | 'char' | 'string' | 'bool'
    ;

// Terminals
Identifier: [a-zA-Z_][a-zA-Z_0-9]*;
Number: [0-9]+;

WS: [ \t\r\n]+ -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;