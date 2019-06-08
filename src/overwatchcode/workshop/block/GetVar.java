package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.Manager;
import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public abstract class GetVar extends Block {
	
	private boolean global;
	private Variable var;

	
	public GetVar(boolean global) {
		this.global = global;
	}
	
	public Variable getVar() {
		return var;
	}
	public void setVar(Variable var) {
		this.var = var;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		var.setValue(Manager.INSTANCE.getVarName(var.getValue(), global, ruleName, event, functions));		
	}
}