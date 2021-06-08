# Compiler
![Banner](/img/COMPILER.png?raw=true)

Java compiler divided into three parts, lexical analysis, semantic analysis, and sintactic analysis. 
The goal of the project is to understand how each part of a compiler works, from the moment each character is captured to the code representation.

# Lexical Analysis
![Banner](/img/LEXICAL.png?raw=true)

The image shows the lexico system used, which corresponds to each token. When set the token type, its state is given, whether it remains in that state or whether it will exchange.

# Syntactic Analyzer
![Banner](/img/SINTATICA.png?raw=true)

This part consists of defining a context-free language to define the path that the compiler will take when executing its "code".
It's essential because without it you only have tokens without purpose but with "type".
The image above is about the context-free language used in this project, it defines the path my compiler will take.

# Semantic Analysis

the semantic part has a logic in which it is definitions/rules of code. Its semantics that say which "cast standards" are acceptable. Or because variables with the same identifier are not supported, or even name but different scopes. They're just classic examples, but it's free for you to set your rules.





