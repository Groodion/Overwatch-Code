package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class SetPlayerVar extends SetVar {

	private Block player;
	
	
	public SetPlayerVar() {
		super(false);
	}
	
	public Block getPlayer() {
		return player;
	}
	public void setPlayer(Block player) {
		this.player = player;
	}
	
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		super.updateNames(event, ruleName, functions);
		
		player.updateNames(event, ruleName, functions);
	}
	
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Set Player Variable");
		sB.append('(');
		sB.append(player.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(getVar().toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(getValue().toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}
