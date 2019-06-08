package overwatchcode.workshop.block;

public class RuleCondition extends CompareCondition {
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
