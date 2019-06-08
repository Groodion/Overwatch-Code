package overwatchcode.workshop.block;

import overwatchcode.workshop.Block;

public abstract class Condition extends Block {
	public RuleCondition toRuleCondition() {
		RuleCondition rCondition = new RuleCondition();
		
		rCondition.setA(this);
		
		return rCondition;
	}
}