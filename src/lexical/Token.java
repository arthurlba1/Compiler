package src.lexical;

public class Token {
    public static final String TK_CHAR = "char";
    public static final String TK_IDENTIFIER = "identifier";
    public static final String TK_NUMBER_INT = "int";
    public static final String TK_NUMBER_FLOAT = "float";
    public static final String TK_OPERATOR = "relacional";
    public static final String TK_ARITHMETIC = "arithmetic";
    public static final String TK_SPECIAL_CHAR = "special";
    public static final String TK_RESERVED_STRING = "reserved";


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
