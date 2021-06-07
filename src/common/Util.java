package src.common;

public class Util {
    public static boolean isChar(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');  
    }
    public static boolean isUnderscore(char c){
        return c == '_';
    }
    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    public static boolean isDot(char c){
        return c == '.';
    }
    public static boolean isReserved(String term){
        return term.equals("main") || term.equals("if") || term.equals("else") || term.equals("while")||
         term.equals("do") || term.equals("for") || term.equals("int")|| term.equals("float") || term.equals("float");
    }
    public static boolean isOperator(char c) {
        return c == '<' || c == '>' || c == '=' || c == '!';
    }
    public static boolean isArithmetic(char c){
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '=';
    }
    public static boolean isSpecial(char c){
        return c == ')' || c == '(' || c == '{' || c== '}' || c == ',' || c == ';';
    }
}
