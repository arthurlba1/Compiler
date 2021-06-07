package src.syntactic;

import src.exceptions.SyntaxException;
import src.lexical.*;
public class Parser {
    
    private TokenScanner scanner;
    private Token token;
    
    public Parser(TokenScanner scanner){
        this.scanner = scanner;
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

        token = scanner.nextToken();
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
        if(token.getText().equals("float") || token.getText().equals("char") || token.getText().equals("int")){
            comando_basico();
        }
        if(token.getText().equals("=")){
            atribuicao();
        }

        if(token.getText().equals("while")){
            iteracao();
        }

        if(token.getText().equals("if")){
            if_expression();
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
        exp_arit();
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
        
        if(token.getText().equals("(")){
            exp_arit();
            token = scanner.nextToken();
            if(!token.getText().equals(")")){
                throw new SyntaxException("You inserted: " + token.getText()+", ')' expected!");
            }
        }
        
        if(token.getType() != Token.TK_CHAR && token.getType() != Token.TK_NUMBER_FLOAT && token.getType() != Token.TK_NUMBER_INT && token.getType() != Token.TK_IDENTIFIER){
            throw new SyntaxException("You inserted:" + token.getText() +", terminal expected!");
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
        token = scanner.nextToken();

        if(!token.getText().equals(";") && !token.getText().equals("=")){
            throw new SyntaxException("You inserted: " + token.getText() +", ';' or '=' expected!");
        }

        if(token.getText().equals("=")){
            comando();
        }

        if(token.getText().equals(";")){
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
    }

////ID    
    public void id () {
        token = scanner.nextToken();
        if(token.getType() != Token.TK_IDENTIFIER){
            throw new SyntaxException("You inserted: " + token.getText() +"Identifier expected!");
        }
    }
}
