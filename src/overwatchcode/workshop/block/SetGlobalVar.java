package overwatchcode.workshop.block;

public class SetGlobalVar extends SetVar {

	public SetGlobalVar() {
		super(true);
	}

	@Override
	public String toOVWCode(boolean min) {
		StringBuilder sB = new StringBuilder();
		
		sB.append("Set Global Variable");		
		sB.append('(');
		sB.append(getVar().toOVWCode(min));
		sB.append(',');
		if(!min) sB.append(' ');
		sB.append(getValue().toOVWCode(min));
		sB.append(')');
		
		return sB.toString();
	}
}