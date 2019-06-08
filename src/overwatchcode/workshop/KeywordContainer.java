package overwatchcode.workshop;

public class KeywordContainer {
	
	private String rule;
	private String event;
	private String conditions;
	private String actions;
	
	
	public KeywordContainer(String rule, String event, String conditions, String actions) {
		super();
		this.rule = rule;
		this.event = event;
		this.conditions = conditions;
		this.actions = actions;
	}


	public String getRule() {
		return rule;
	}
	public String getEvent() {
		return event;
	}
	public String getConditions() {
		return conditions;
	}
	public String getActions() {
		return actions;
	}	
}