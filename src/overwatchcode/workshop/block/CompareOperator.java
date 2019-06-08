package overwatchcode.workshop.block;

public enum CompareOperator {
	GT(">"),
	ST("<"),
	STEQ("<="),
	GTEQ(">="),
	EQ("=="),
	NEQ("!=");
	
	private String text;
	
	private CompareOperator(String text) {
		this.text = text;
	}
	
	
	public String getText() {
		return text;
	}
	
	public static CompareOperator parse(String string) {
		for(CompareOperator op: CompareOperator.values()) {
			if(op.text.equals(string)) {
				return op;
			}
		}
		return null;
	}
	
}
