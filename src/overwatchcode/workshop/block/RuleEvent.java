package overwatchcode.workshop.block;

import overwatchcode.Manager;

public class RuleEvent {
	
	private String name;
	private String[] text;
	private boolean global;
	private boolean caseSensitive;
	
	public RuleEvent(String name, String[] text, boolean global, boolean caseSensitive) {
		this.name = name;
		this.text = text;
		this.global = global;
		this.caseSensitive = caseSensitive;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getText() {
		return text;
	}
	public boolean isGlobal() {
		return global;
	}
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	
	public static RuleEvent parse(String string) {
		for(RuleEvent event: Manager.INSTANCE.getEvents()) {
			String s = event.caseSensitive ? string : string.toLowerCase();
			
			for(String text : event.text) {
				String t = event.caseSensitive ? text : text.toLowerCase();
				
				if(t.equals(s)) {
					return event;
				}
			}
		}
		return null;
	}
}
