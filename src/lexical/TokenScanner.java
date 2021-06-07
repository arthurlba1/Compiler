package src.lexical;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TokenScanner {
    
    //variables 
    private char[] conteudo;
    private Analyzer mAnalyzer;
    
    //constructor
    public TokenScanner(String filename){
        try {
            
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)),StandardCharsets.UTF_8);
			conteudo = txtConteudo.toCharArray();
			this.mAnalyzer = new Analyzer(conteudo);
            
		}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Token nextToken(){
        
        return mAnalyzer.nextToken();

    }
}
