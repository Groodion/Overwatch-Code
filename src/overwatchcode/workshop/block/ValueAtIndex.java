package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class ValueAtIndex extends Block {

	private Block array;
	private Block index;
	
	
	public Block getArray() {
		return array;
	}
	public void setArray(Block array) {
		this.array = array;
	}
	public Block getIndex() {
		return index;
	}
	public void setIndex(Block index) {
		this.index = index;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		array.updateNames(event, ruleName, functions);
		index.updateNames(event, ruleName, functions);
	}
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Value In Array");
		sB.append('(');
		sB.append(array.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(index.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
