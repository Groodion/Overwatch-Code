package overwatchcode.workshop.block;

import java.util.ArrayList;
import java.util.List;

import overwatchcode.Manager;
import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class Function extends Condition {

	private String name;
	private List<Block> arguments;
	
	
	public Function() {
		arguments = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Block> getArguments() {
		return arguments;
	}
	
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		name = Manager.INSTANCE.getFunctionName(name, arguments.size());
		
		List<WorkshopFunction> f = new ArrayList<>();
		f.addAll(functions);
		f.add(Manager.INSTANCE.getFunction(name));
		
		for(Block argument: arguments) {
			argument.updateNames(event, ruleName, f);
		}
	}
	
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append(name);
		sB.append('(');
		for(int i = 0; i < arguments.size(); i++) {
			if(i != 0) {
				sB.append(',');
				if(!min) sB.append(' ');
			}
			sB.append(arguments.get(i).toOVWCode(min));
		}
		sB.append(')');
		
		
		return sB.toString();
	}
}