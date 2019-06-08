package overwatchcode.interpreter;

import overwatchcode.workshop.block.RuleEvent;

public class VarNameManager {
	public String getVarName(String name, boolean global, RuleEvent event) {
		if(name.equals("EventPlayer")) return "Event Player";
		
		return name;
	}

	public boolean isSystemVariable(String value) {
		return value.equals("Event Player");
	}
}
