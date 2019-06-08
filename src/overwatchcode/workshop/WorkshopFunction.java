package overwatchcode.workshop;

public class WorkshopFunction {
	
	private String name;
	private int arguments;
	private boolean caseSensitive;
	private String[] text;
	private boolean action;
	
	
	public WorkshopFunction(String name, int arguments, boolean caseSensitive, String[] text, boolean action) {
		this.name = name;
		this.arguments = arguments;
		this.caseSensitive = caseSensitive;
		this.text = text;
		this.action = action;
	}

	public String getName() {
		return name;
	}
	public int getArguments() {
		return arguments;
	}
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	public String[] getText() {
		return text;
	}
	public boolean getAction() {
		return action;
	}
}