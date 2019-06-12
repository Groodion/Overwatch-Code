package overwatchcode.workshop.block;

import overwatchcode.workshop.Block;

public class RuleCondition extends CompareCondition {

	public RuleCondition() {
		super();
	}
	public RuleCondition(Block block) {
		super(block);
	}

	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();

		sB.append(getA().toOVWCode(min));
		if(!min) sB.append(' ');
		sB.append(getOp().getText());
		if(!min) sB.append(' ');
		sB.append(getB().toOVWCode(min));
		
		return sB.toString();
	}
}
