package overwatchcode.workshop.block;

import overwatchcode.Manager;

public class GlobalVar extends GetVar {
	
	public GlobalVar() {
		super(true);
	}

	@Override
	public String toOVWCode(boolean min) {
		if(Manager.INSTANCE.isSystemVariable(getVar().getValue())) {
			return getVar().getValue();
		} else {		
			StringBuilder sB = new StringBuilder();
			
			sB.append("Global Variable");
			sB.append('(');
			sB.append(getVar().toOVWCode(min));
			sB.append(')');
			
			return sB.toString();
		}
	}
}