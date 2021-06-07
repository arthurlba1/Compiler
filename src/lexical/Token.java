package src.lexical;

public class Token {
    public static final String TK_CHAR = "Char";
    public static final String TK_IDENTIFIER = "Identificador";
    public static final String TK_NUMBER_INT = "Inteiro";
    public static final String TK_NUMBER_FLOAT = "Float";
    public static final String TK_OPERATOR = "Relacional";
    public static final String TK_ARITHMETIC = "Aritmético";
    public static final String TK_SPECIAL_CHAR = "Especial";
    public static final String TK_RESERVED_STRING = "Reservado";


    public static final  String TK_TEXT[] = {
        "CHAR","IDENTIFIER", "NUMBER_INT",
        "NUMBER_FLOAT", "OPERATOR", "ARITHMETIC", 
        "SPECIAL_CHAR", "RESERVED STRING"
    };

    //variables
    private String type;
	private String text;
	private int line;
	private int column;
	
    //constructor
	public Token(String type, String text) {
		super();
		this.type = type;
		this.text = text;
	}

    //super token
	public Token() {
		super();
	}

    //get's and set's 
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Token [Tipo: " + type + ", Texto: " + text + "]";
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
