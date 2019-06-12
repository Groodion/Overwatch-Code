package overwatchcode.workshop.block;

import java.util.List;

import overwatchcode.workshop.Block;
import overwatchcode.workshop.WorkshopFunction;

public class CompareCondition extends Condition {
	
	private Block a;
	private Block b;
	private CompareOperator op;
	
	
	public CompareCondition() {
		a = Terminal.TRUE;
		b = Terminal.TRUE;
		op = CompareOperator.EQ;
	}
	public CompareCondition(Block block) {
		this();
		
		a = block;
	}
	
	public Block getA() {
		return a;
	}
	public void setA(Block a) {
		this.a = a;
	}
	public Block getB() {
		return b;
	}
	public void setB(Block b) {
		this.b = b;
	}
	public CompareOperator getOp() {
		return op;
	}
	public void setOp(CompareOperator op) {
		this.op = op;
	}

	@Override
	public RuleCondition toRuleCondition() {
		RuleCondition rCondition = new RuleCondition();
		
		rCondition.setA(a);
		rCondition.setOp(op);
		rCondition.setB(b);
		
		return rCondition;
	}
	
	@Override
	public void updateNames(RuleEvent event, String ruleName, List<WorkshopFunction> functions) {
		a.updateNames(event, ruleName, functions);
		b.updateNames(event, ruleName, functions);
	}
	
	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Compare");
		sB.append('(');
		sB.append(a.toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(op.getText());
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(b.toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}