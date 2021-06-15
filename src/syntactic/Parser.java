package src.syntactic;

import java.util.List;
import java.util.ArrayList;
import src.exceptions.SyntaxException;
import javafx.util.Pair;
import src.exceptions.SemanticException;
import src.lexical.*;
import src.semantics.DictionarySemantics;
import src.semantics.DictionaryStack;

public class Parser {
    
    private TokenScanner scanner;
    private Token token;
    
    //data
    private String type, id, value;
    private DictionarySemantics dic;
    private DictionaryStack stack ;

    private String T = "";
    private String y = "";
    private String x = "";
    private String op = "";
    List<String> values;
    List<String> ahrit;
    
    public Parser(TokenScanner scanner){
        this.scanner = scanner;
        this.dic = new DictionarySemantics();
        this.stack = new DictionaryStack();
        this.stack.add(this.dic);
    }

////PROGRAMA
    public void programa () {
        token = scanner.nextToken();
        if(!token.getText().equals("int")){
            throw new SyntaxException("You inserted: " + token.getText() +", 'int' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals("main")){
            throw new SyntaxException("You inserted: " + token.getText() +", 'main' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals("(")){
            throw new SyntaxException("You inserted: " + token.getText() +", '(' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals(")")){
            throw new SyntaxException("You inserted: " + token.getText() +", ')' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals("{")){
            throw new SyntaxException("You inserted: " + token.getText() +", '{' expected!");
        }

        bloco();
        //token = scanner.nextToken();
        if(!token.getText().equals("}")){
            throw new SyntaxException("You inserted: " + token.getText() +", '}' expected!");
        }
    }   

////BLOCO
    public void bloco () {
        token = scanner.nextToken(); // verifica qual proximo token
        decl_var(); // entra em declaração de variavel 
        comando(); // entra em comando
    }

///COMANDO
    public void comando () {
        while(token.getText().equals("float") || token.getText().equals("char") || token.getText().equals("int") || token.getText().equals("=")
        || token.getType() == Token.TK_IDENTIFIER || token.getText().equals("while") || token.getText().equals("if")){
        
            if(token.getText().equals("float") || token.getText().equals("char") || token.getText().equals("int")){
            comando_basico();
            }
            if(token.getText().equals("=")){
            atribuicao();
            }
            if(token.getType() == Token.TK_IDENTIFIER){
                while(token.getType() == Token.TK_IDENTIFIER){
                    id = token.getText();
                    if(stack.peek().get(id) == null){
                        throw new SemanticException("Variable not declared: " + id );
                    }
                    token = scanner.nextToken();
                    if(token.getText().equals("=")){
                        atribuicao();
                        token = scanner.nextToken();
                    }
                    if(token.getText().equals("int") || token.getText().equals("float") || token.getText().equals("char") ){
                        decl_var();
                    }
                }
            }
            if(token.getText().equals("while")){
                iteracao();
            }

            if(token.getText().equals("if")){
                if_expression();
            }
            if(token.getText().equals("}")){
                stack.pop();
            }
                //token = scanner.nextToken();
            }
            
        }

//IF_EXPRESSION
    public void if_expression(){
        token = scanner.nextToken();

        if(!token.getText().equals("(")){
            throw new SyntaxException("You inserted: " + token.getText() +", '(' expected!");
        }

        exp_relacional();

        if(!token.getText().equals(")")){
            throw new SyntaxException("You inserted: " + token.getText() +", ')' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals("{")){
            throw new SyntaxException("You inserted: " + token.getText() +", '{' expected!");
        }

        stack.add(new DictionarySemantics(stack.peek()));
        token = scanner.nextToken();
        if(!token.getText().equals("{")){
            comando();
        }

        if(!token.getText().equals("}")){
            token = scanner.nextToken();
        }
        
        if(!token.getText().equals("}")){
            throw new SyntaxException("You inserted: " + token.getText() +", '}' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals("else")){
            throw new SyntaxException("You inserted: " + token.getText() +", 'else' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals("{")){
            throw new SyntaxException("You inserted: " + token.getText() +", '{' expected!");
        }

        stack.add(new DictionarySemantics(stack.peek()));
        token = scanner.nextToken();
        if(!token.getText().equals("{")){
            comando();
        }

        if(!token.getText().equals("}")){
            token = scanner.nextToken();
        }
        
        if(!token.getText().equals("}")){
            throw new SyntaxException("You inserted: " + token.getText() +", '}' expected!");
        }
    }

///COMANDO BASICO
    public void comando_basico () {
            decl_var();
    }

//ATRIBUICAO
    public void atribuicao () {
        values=new ArrayList<String>();
        ahrit=new ArrayList<String>();
        int tsun = 0;
        exp_arit();
        int aux1 = 0;
        while(aux1 <= ahrit.size()){
            for(int i = 0; i < ahrit.size(); i++){
                T = "T";
                if(ahrit.get(i).equals("/") || ahrit.get(i).equals("*")){
                    x = values.get(i);
                    op = ahrit.get(i);
                    values.remove(i);
                    y = values.get(i);
                    ahrit.remove(i);
                    tsun++;
                    T = T+tsun;
                    values.set(i, T);
                    System.out.println(T + " = " + x + " "+ op + " " + y);
                }
            }   
        aux1++;
        }
        aux1 = 0;
        while(aux1 <= ahrit.size()){
            for(int i = 0; i < ahrit.size(); i++){
                T = "T";
                if(ahrit.get(i).equals("+") || ahrit.get(i).equals("-")){
                    x = values.get(i);
                    op = ahrit.get(i);
                    values.remove(i);
                    y = values.get(i);
                    ahrit.remove(i);
                    tsun++;
                    T = T+tsun;
                    values.set(i, T);
                    System.out.println(T + " = " + x + " "+ op + " " + y);
                }
            }
            aux1++;
        }
        if(values.size() > 1){
            System.out.println(id + " = " + T);
        }
        Pair aux = stack.peek().get(id);
        if(aux.getKey().toString().equals(type) || (type.equals("int") && aux.getKey().toString().equals("float"))){
            aux = new Pair<String,String>(aux.getKey().toString(), value);
            stack.peek().put(id,aux);
        }
        else{
            throw new SemanticException("Incompatible type!\nVariable type: " + stack.peek().get(id).getKey() + " \ntrying to insert: " +type);
        }
        if(!token.getText().equals(";")){
            throw new SyntaxException("You inserted: " + token.getText() +", ';' expected!");
        }
    }
//ITERACAO
    public void iteracao () {
        token = scanner.nextToken();
        if(!token.getText().equals("(")){
            throw new SyntaxException("You inserted: " + token.getText() +", '(' expected!");
        }

        exp_relacional();

        //token = scanner.nextToken();
        if(!token.getText().equals(")")){
            throw new SyntaxException("You inserted: " + token.getText() +", ')' expected!");
        }

        token = scanner.nextToken();
        if(!token.getText().equals("{")){
            throw new SyntaxException("You inserted: " + token.getText() +", '{' expected!");
        }

        stack.add(new DictionarySemantics(stack.peek()));
        token = scanner.nextToken();

        if(!token.getText().equals("{")){
            comando();
        }
        
        if(!token.getText().equals("}")){
            token = scanner.nextToken();
        }
        if(!token.getText().equals("}")){
            throw new SyntaxException("You inserted: " + token.getText() +", '}' expected!");
        }
        
    }


///EXP_ARIT
    public void exp_arit () {
        termo();
        exp_arit_();
    }

///EXP_ARIT_
    public void exp_arit_ () {
        
        if(token != null){
            if(token.getText().equals("+") || token.getText().equals("-")){
                if(!token.getText().equals("+") && !token.getText().equals("-")){
                    throw new SyntaxException("You inserted: " + token.getText() +", '+' or '-' expected!");
                }
            termo();
            exp_arit_();
            }
        }
    }

///TERMO
    public void termo () {
        fator();
        termo_();
    }

///TERMO_
    public void termo_ () {
        token = scanner.nextToken();
        if(token.getType() == Token.TK_ARITHMETIC){
            ahrit.add(token.getText());
        }
        if(token != null){
            if(token.getText().equals("*") || token.getText().equals("/")){
                if(!token.getText().equals("*") && !token.getText().equals("/")){
                    throw new SyntaxException("You inserted: " + token.getText() +", '*' or '/' expected!");
                }
            termo();
            exp_arit_();
            }
        }
    }    

///FATOR
    public void fator () {
        token = scanner.nextToken();
        values.add(token.getText());
        if(token.getType() == Token.TK_NUMBER_FLOAT || token.getType() == Token.TK_NUMBER_INT){
            //save the variable type
            type = token.getType(); 
            //save the value 
            value = token.getText();
        }
        if(token.getType() == Token.TK_CHAR || token.getType() == Token.TK_IDENTIFIER){
            String data = token.getText();
            if(stack.peek().get(data) == null){
                throw new SemanticException("Variable not declared: " +data);
            }
            type = stack.peek().get(data).getKey().toString();
            value = stack.peek().get(data).getValue().toString();
            
            if(stack.peek().get(data).getValue().toString().equals("")) {
                throw new SemanticException("Variable not initialized: " +data);
            }
 
        }

        if(token.getText().equals("(")){
            exp_arit();
            token = scanner.nextToken();
            if(!token.getText().equals(")")){
                throw new SyntaxException("You inserted: " + token.getText()+", ')' expected!");
            }
        }
        if(token.getType() != Token.TK_NUMBER_FLOAT && token.getType() == Token.TK_NUMBER_INT && token.getType() == Token.TK_IDENTIFIER && token.getType() == Token.TK_CHAR){
            throw new SyntaxException("terminal expected!");
        }
    }

///EXP_RELACIONAL
    public void exp_relacional () {
        exp_arit();
        if(token.getType() != Token.TK_OPERATOR){
            throw new SyntaxException("You inserted:" + token.getText() +", Operator expected!");
        }
        exp_arit();
    }

///DECL_VAR
    public void decl_var () {
        tipo();
        id();
        boolean declared = false;
        token = scanner.nextToken();

        if(!token.getText().equals(";") && !token.getText().equals("=")){
            throw new SyntaxException("You inserted: " + token.getText() +", ';' or '=' expected!");
        }

        if(token.getText().equals("=")){
            if(stack.peek().get(id) == null){
                stack.peek().put(id,new Pair<String,String>(type, ""));
            } else {
                throw new SemanticException("Variable already declared: " + id );
            }
            comando();
            declared = true;
        }

        if(token.getText().equals(";")){
            if(!declared)
            if(stack.peek().get(id) == null){
                stack.peek().put(id,new Pair<String,String>(type, ""));
            } else {
                throw new SemanticException("Variable already declared: " + id );
            }
            token = scanner.nextToken();
            if(token.getText().equals("float") || token.getText().equals("char") || token.getText().equals("int")){
                decl_var();
            }
        }
        
    }

////TIPO
    public void tipo () {
        if(!token.getText().equals("float") && !token.getText().equals("char") && !token.getText().equals("int")){
            throw new SyntaxException("You inserted: " + token.getText() +", Type expected!");
        }
        //determines the variable type
        type = token.getText();
    }

////ID    
    public void id () {
        token = scanner.nextToken();
        if(token.getType() != Token.TK_IDENTIFIER){
            throw new SyntaxException("You inserted: " + token.getText() +"Identifier expected!");
        }
        //determines the variable `name`
        id = token.getText();
    }
}
