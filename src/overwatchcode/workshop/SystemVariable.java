package overwatchcode.workshop;

import java.util.List;

import overwatchcode.workshop.block.RuleEvent;

public class SystemVariable {
	
	private String name;
	private List<RuleEvent> events;
	private List<WorkshopFunction> functions;
	private boolean caseSensitive;
	private String[] text;
	
	
	public SystemVariable(String name, List<RuleEvent> events, List<WorkshopFunction> functions, String[] text, boolean caseSensitive) {
		super();
		this.name = name;
		this.events = events;
		this.functions = functions;
		this.text = text;
		this.caseSensitive = caseSensitive;
	}
	
	public String getName() {
		return name;
	}
	public List<RuleEvent> getEvents() {
		return events;
	}
	public List<WorkshopFunction> getFunctions() {
		return functions;
	}
	public String[] getText() {
		return text;
	}
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
}