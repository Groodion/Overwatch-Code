package overwatchcode.workshop;

import java.util.HashMap;

import overwatchcode.Manager;
import overwatchcode.workshop.block.RuleEvent;


public class VarNameRule {
	
	private VariableScope scope;
	private String ruleEvent;
	private String ruleName;
	private boolean caseSensitive;
	private HashMap<String, String[]> nameConnections;
	private boolean fillTopDown;
	private char fillTopDownStart;
	private char fillTopDownEnd;
	private boolean ignoreLetters;
	
	
	public VarNameRule() {
		scope = null;
		ruleEvent = ".*";
		ruleName = ".*";
		ignoreLetters = false;
		nameConnections = new HashMap<>();
		caseSensitive = true;
		fillTopDown = false;
		fillTopDownStart = 'A';
		fillTopDownEnd = 'Z';
	}

	public String getRuleEvent() {
		return ruleEvent;
	}
	public void setRuleEvent(String ruleEvent) {
		this.ruleEvent = ruleEvent;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String functionName) {
		this.ruleName = functionName;
	}
	public HashMap<String, String[]> getNameConnections() {
		return nameConnections;
	}
	public boolean isFillTopDown() {
		return fillTopDown;
	}
	public void setFillTopDown(boolean fillTopDown) {
		this.fillTopDown = fillTopDown;
	}
	public char getFillTopDownStart() {
		return fillTopDownStart;
	}
	public void setFillTopDownStart(char fillTopDownStart) {
		this.fillTopDownStart = Character.toUpperCase(fillTopDownStart);
	}
	public char getFillTopDownEnd() {
		return fillTopDownEnd;
	}
	public void setFillTopDownEnd(char fillTopDownEnd) {
		this.fillTopDownEnd = Character.toUpperCase(fillTopDownEnd);
	}
	public VariableScope getScope() {
		return scope;
	}
	public void setScope(VariableScope scope) {
		this.scope = scope;
	}
	public boolean isCaseSensitive() {
		return caseSensitive;
	}
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}
	public boolean isIgnoreLetters() {
		return ignoreLetters;
	}
	public void setIgnoreLetters(boolean ignoreLetters) {
		this.ignoreLetters = ignoreLetters;
	}

	public String applyRule(String variableName, boolean global, String ruleName, RuleEvent event) {
		if((isIgnoreLetters() && variableName.length() == 1) || (global && scope == VariableScope.PLAYER) || (!global && scope == VariableScope.GLOBAL)) {
			return null;
		}
		
		boolean f = false;
		
		if(!event.getName().matches(ruleEvent)) {
			for(String rText: event.getText()) {
				if(rText.matches(ruleEvent)) {
					f = true;
					break;
				}
			}
			
			if(!f || !ruleName.matches(this.ruleName)) {
				return null;
			}
		}
		
		if(Manager.INSTANCE.getVariable(variableName) != null) {
			return Manager.INSTANCE.getVariable(variableName);
		}
		
		String n = caseSensitive ? variableName : variableName.toLowerCase();
		for(String var: nameConnections.keySet()) {
			for(String text: nameConnections.get(var)) {
				String t = caseSensitive ? text : text.toLowerCase();
				
				if(n.equals(t)) {
					Manager.INSTANCE.setVariable(ruleName, event, variableName, var);
					return var;
				}
			}
		}
		
		if(fillTopDown) {
			char fillTopDownCounter = Manager.INSTANCE.getFillTopDownCounter();
			
			if(fillTopDownCounter < fillTopDownStart) fillTopDownCounter = fillTopDownStart;
			else if(fillTopDownCounter > fillTopDownEnd) fillTopDownCounter = fillTopDownEnd;
			
			String var = String.valueOf(fillTopDownCounter);
			
			Manager.INSTANCE.incFillTopDownCounter();
			
			Manager.INSTANCE.setVariable(ruleName, event, variableName, var);
			return var;
		}
		
		return null;
	}
}