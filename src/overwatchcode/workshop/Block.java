package overwatchcode.workshop;

import java.util.List;

import overwatchcode.workshop.block.RuleEvent;

public abstract class Block {
	
	public abstract void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions);
	
	public abstract String toOVWCode(boolean min);
}
