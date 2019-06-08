package overwatchcode.workshop.block;

public enum TeamScope {
	All("All"),
	Team1("Team 1"),
	Team2("Team 2");
	
	private String text;
	
	
	TeamScope(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public static TeamScope parse(String string) {
		if(string.equals("?")) return All;
		
		return valueOf(string);
	}
}