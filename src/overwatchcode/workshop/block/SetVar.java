package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public abstract class SetVar extends GetVar {

	private Block value;

	
	public SetVar(boolean global) {
		super(global);
	}

	public Block getValue() {
		return value;
	}
	public void setValue(Block value) {
		this.value = value;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		super.updateNames(event, ruleName, functions);

		value.updateNames(event, ruleName, functions);
	}
}