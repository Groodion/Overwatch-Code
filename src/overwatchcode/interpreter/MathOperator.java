package overwatchcode.interpreter;

import overwatchcode.workshop.block.MathBlock;
import overwatchcode.workshop.block.Add;
import overwatchcode.workshop.block.Subtract;
import overwatchcode.workshop.block.Multiply;
import overwatchcode.workshop.block.Divide;
import overwatchcode.workshop.block.Modulo;
import overwatchcode.workshop.block.Pow;


public enum MathOperator {
	ADD("+", () -> new Add()),
	SUB("-", () -> new Subtract()),
	MULT("*", () -> new Multiply()),
	DIVIDE("/", () -> new Divide()),
	MOD("%", () -> new Modulo()),
	POW("^", () -> new Pow());
	
	
	private String symbol;
	private MathBlockFactory factory;
	
	private MathOperator(String symbol, MathBlockFactory factory) {
		this.symbol = symbol;
		this.factory = factory;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public MathBlock createBlock() {
		return factory.create();
	}
	
	
	public static MathOperator parse(String string) {
		for(MathOperator op: values()) {
			if(op.symbol.equals(string)) {
				return op;
			}
		}
		
		return null;
	}
}

interface MathBlockFactory {
	MathBlock create();
}