import src.exceptions.*;
import src.lexical.*;
import src.syntactic.*;

public class Main {
    public static void main(String[] args) {
        try{

        //place the directory of your input here    
        TokenScanner sc = new TokenScanner("C:\\Users\\Berg\\arthur\\compiler\\input.txt");
        Parser pa = new Parser(sc);
        
        pa.programa();

        System.out.println("Compilation Successful!");
        }catch(SyntaxException ex) {
            System.out.println("Syntax error: " + ex.getMessage());
        }  
        catch(LexicalException ex) {
            System.out.println("Lexical error: " + ex.getMessage());
        } catch(Exception ex) {
            System.out.println("Generic error");
        }
    }
}