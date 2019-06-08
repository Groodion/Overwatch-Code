package overwatchcode.workshop.block;

public class Variable extends Terminal {
	
	public GlobalVar toGlobalVar() {
		GlobalVar get = new GlobalVar();
		get.setVar(this);
		
		return get;
	}
	
	public Variable(String var) {
		setValue(var);
	}
}