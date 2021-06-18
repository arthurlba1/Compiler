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
            System.err.println(" Syntax error\n " + ex.getMessage());
        }  
        catch(LexicalException ex) {
            System.err.println(" Lexical error\n " + ex.getMessage());
        }
        catch(SemanticException ex){
            System.err.println(" Semantic error\n " + ex.getMessage());
        }
        catch(Exception ex) {
            System.err.println("Generic error");
        }   
    }
}