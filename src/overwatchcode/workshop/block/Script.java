package overwatchcode.workshop.block;

import java.util.ArrayList;
import java.util.List;


import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class Script extends Block {
	
	private List<Rule> rules;

	
	public Script() {
		rules = new ArrayList<>();
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		for(Rule rule: rules) {
			rule.updateNames(event, null, functions);
		}
	}
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		for(Rule rule: rules) {
			sB.append(rule.toOVWCode(min));
			if(!min) sB.append('\n');
		}
		
		return sB.toString();
	}
}
