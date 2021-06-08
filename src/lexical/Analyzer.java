package src.lexical;

import src.common.Util;
import src.exceptions.LexicalException;

public class Analyzer {

    private char[] conteudo;
	private int estado;
	private int pos;
	private int linha;
	private int coluna;
    private char aux;

    public Analyzer(char[] conteudo){
        this.conteudo = conteudo;
        this.linha = 1;
        this.coluna = 0;
        this.pos=0;
    }

    public Token nextToken() {
        char charAtual;
        Token token;
        String term = "";
        coluna++;       
        if (isEOF()){
            return null;
        }
        estado = 0;
        while(true){
            charAtual = nextChar();
            switch(estado){
               case 0:
                    if(Util.isChar(charAtual) || Util.isUnderscore(charAtual)){
                        term += charAtual;
					    estado = 1;
                    }
                    else if(Util.isDigit(charAtual)){
                        estado = 2;
                        term += charAtual;
                    }
                    else if(Util.isOperator(charAtual)){
                        term += charAtual;
                        estado = 4;
                    }
                    else if(Util.isArithmetic(charAtual)){
                        term += charAtual;
                        estado = 5;
                    }
                    else if(Util.isSpecial(charAtual)){
                        term += charAtual;
                        estado = 6;
                    }
                    else if (isSpace(charAtual)) {
                        estado = 0;
                    }
                    else if (charAtual=='\'') {
                        term += charAtual;
                        estado = 8;
                    }
                    break;
                case 1:
                    if(Util.isChar(charAtual) || Util.isUnderscore(charAtual) || Util.isDigit(charAtual)){
                        term += charAtual;
                            estado = 1;
                    } else if (!Util.isChar(charAtual) || !Util.isUnderscore(charAtual) || !Util.isDigit(charAtual)) {
                        if(Util.isReserved(term)){
                            estado = 7;
                        }
                        else{ 
						back();
                        token = new Token();
					    token.setType(Token.TK_IDENTIFIER);
					    token.setText(term);
                        token.setLine(linha);
					    token.setColumn(coluna - term.length());
					    return token;
                        }
                    }
                    else {
                        throw new LexicalException("Identificador mal formado");
                    }
                    break;
                case 2:
                    if (Util.isDigit(charAtual)){
                        estado = 2;
                        term += charAtual;
                    }
                    else if(Util.isDot(charAtual)){
                        estado = 3;
                        term += charAtual;
                    }
                    else if(!Util.isDigit(charAtual)){
                        back();
                        token = new Token();
                        token.setType(Token.TK_NUMBER_INT);
                        token.setText(term);
                        token.setLine(linha);
					    token.setColumn(coluna - term.length());
                        return token;
                    }
                    else {
                        throw new LexicalException("Inteiro mal formado");
                    }
                    break;
                case 3:
                    if(Util.isDigit(charAtual)){
                        estado = 3;
                        term += charAtual;
                    } else if(!Util.isDigit(charAtual)){
                        back();
                        token = new Token();
                        token.setType(Token.TK_NUMBER_FLOAT);
                        token.setText(term);
                        token.setLine(linha);
					    token.setColumn(coluna - term.length());
                        return token;
                    } else {
                        throw new LexicalException("Número float mal formado");
                    }
                    break;
                case 4:
                    aux = conteudo[pos-1];
                    if(term.equals("=") && aux != '='){
                        estado = 5;
                        back();
                        token = new Token();
                        token.setType(Token.TK_ARITHMETIC);
                        token.setText(term);
                        token.setLine(linha);
                        token.setColumn(coluna - term.length());
                        return token;
                    }
                    else if(aux == '='){
                        term += charAtual;
                        token = new Token();
                        token.setType(Token.TK_OPERATOR);
                        token.setText(term);
                        token.setLine(linha);
					    token.setColumn(coluna - term.length());
                    }
                    else {
                        token = new Token();
                        token.setType(Token.TK_OPERATOR);
                        token.setText(term);
                        token.setLine(linha);
					    token.setColumn(coluna - term.length());
                        return token;
                    }
                    break;
                case 5:
                    if (!isEOF(charAtual)){
                        back();
                        token = new Token();
                        token.setType(Token.TK_ARITHMETIC);
                        token.setText(term);
                        token.setLine(linha);
                        token.setColumn(coluna - term.length());
                        return token;
                    }
                    else{
                         throw new LexicalException("Aritmético mal formado");
                    }
                case 6:
                    if (!isEOF(charAtual)){
                        back();
                        token = new Token();
                        token.setType(Token.TK_SPECIAL_CHAR);
                        token.setText(term);
                        token.setLine(linha);
                        token.setColumn(coluna - term.length());
                        return token;
                    }
                        // else{
                        //     throw new LexicalException("Caracter especial mal formado");
                        // }
                case 7:
                    back();
                    token = new Token();
                    token.setType(Token.TK_RESERVED_STRING);
                    token.setText(term);
                    token.setLine(linha);
                    token.setColumn(coluna - term.length());
                    return token;
                case 8: 
                    if(Util.isChar(charAtual)||Util.isDigit(charAtual)){
                        term += charAtual;
                        nextChar();
                        term += '\'';
                        token = new Token();
                        token.setType(Token.TK_CHAR);
                        token.setText(term);
                        token.setLine(linha);
                        token.setColumn(coluna - term.length());
                        back();
                        return token;
                    }
             }
        }
    }

    //reading files
    private boolean isEOF() {
		return pos >= conteudo.length;
	}
    private char nextChar() {
		if (isEOF()) {
			return '\0';
		}
		return conteudo[pos++];
	}
    private void back() {
        pos--;
    }
    private boolean isEOF(char c) {
    	return c == '\0';
    }

    private boolean isSpace(char c) {
		if (c == '\n' || c== '\r') {
			linha++;
			coluna=0;
		}
		return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
	}
}
