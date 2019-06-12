package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class SetGlobalVarAtIndex extends SetGlobalVar {

	private Block index;
	
	
	public Block getIndex() {
		return index;
	}
	public void setIndex(Block index) {
		this.index = index;
	}
	
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		super.updateNames(event, ruleName, functions);
		
		index.updateNames(event, ruleName, functions);
	}
	
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Set Global Variable At Index");		
		sB.append('(');
		sB.append(getVar().toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(index.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(getValue().toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
