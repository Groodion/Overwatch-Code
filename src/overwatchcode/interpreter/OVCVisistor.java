package overwatchcode.interpreter;

import java.util.ArrayList;
import java.util.List;

import overwatchcode.parser.ASTAdditiveExpression;
import overwatchcode.parser.ASTAdditiveOp;
import overwatchcode.parser.ASTArguments;
import overwatchcode.parser.ASTArrayItem;
import overwatchcode.parser.ASTAssignment;
import overwatchcode.parser.ASTBoolean;
import overwatchcode.parser.ASTChain;
import overwatchcode.parser.ASTConditionalAndExpression;
import overwatchcode.parser.ASTConditionalOrExpression;
import overwatchcode.parser.ASTElse;
import overwatchcode.parser.ASTExpression;
import overwatchcode.parser.ASTFnArrayItem;
import overwatchcode.parser.ASTFunctionCall;
import overwatchcode.parser.ASTFunctionName;
import overwatchcode.parser.ASTIf;
import overwatchcode.parser.ASTIfElseRest;
import overwatchcode.parser.ASTLineStatement;
import overwatchcode.parser.ASTMultiplicativeExpression;
import overwatchcode.parser.ASTMultiplocativeOp;
import overwatchcode.parser.ASTNumber;
import overwatchcode.parser.ASTPlayerScope;
import overwatchcode.parser.ASTRelationalExpression;
import overwatchcode.parser.ASTRelationalOp;
import overwatchcode.parser.ASTRule;
import overwatchcode.parser.ASTRuleCondition;
import overwatchcode.parser.ASTRuleEvent;
import overwatchcode.parser.ASTRuleName;
import overwatchcode.parser.ASTRuleScope;
import overwatchcode.parser.ASTStart;
import overwatchcode.parser.ASTStatement;
import overwatchcode.parser.ASTString;
import overwatchcode.parser.ASTTeamScope;
import overwatchcode.parser.ASTUnaryExpression;
import overwatchcode.parser.ASTVarArrayItem;
import overwatchcode.parser.ASTVarName;
import overwatchcode.parser.ASTVector;
import overwatchcode.parser.ASTVectorComponent;
import overwatchcode.parser.OWCParserVisitor;
import overwatchcode.parser.SimpleNode;
import overwatchcode.workshop.Block;
import overwatchcode.workshop.block.AndCondition;
import overwatchcode.workshop.block.Function;
import overwatchcode.workshop.block.GetVar;
import overwatchcode.workshop.block.CompareCondition;
import overwatchcode.workshop.block.CompareOperator;
import overwatchcode.workshop.block.ComponentOfVector;
import overwatchcode.workshop.block.Condition;
import overwatchcode.workshop.block.GlobalVar;
import overwatchcode.workshop.block.PlayerVar;
import overwatchcode.workshop.block.MathBlock;
import overwatchcode.workshop.block.Not;
import overwatchcode.workshop.block.OrCondition;
import overwatchcode.workshop.block.PlayerScope;
import overwatchcode.workshop.block.Rule;
import overwatchcode.workshop.block.RuleEvent;
import overwatchcode.workshop.block.Script;
import overwatchcode.workshop.block.SetGlobalVar;
import overwatchcode.workshop.block.SetGlobalVarAtIndex;
import overwatchcode.workshop.block.SetPlayerVar;
import overwatchcode.workshop.block.SetPlayerVarAtIndex;
import overwatchcode.workshop.block.SetVar;
import overwatchcode.workshop.block.TeamScope;
import overwatchcode.workshop.block.Terminal;
import overwatchcode.workshop.block.ValueAtIndex;
import overwatchcode.workshop.block.Variable;
import overwatchcode.workshop.block.Vector;
import overwatchcode.workshop.block.container.Container;
import overwatchcode.workshop.block.container.If;

public class OVCVisistor implements overwatchcode.parser.OWCParserVisitor {

	public static final OWCParserVisitor INSTANCE = new OVCVisistor();
	

	private OVCVisistor() {
	}
	
	/**
	 * Never gets called
	 */
	@Override
	public Block visit(SimpleNode node, Block data) {
		return null;
	}

	/**
	 * ( Rule() )*
	 */
	@Override
	public Block visit(ASTStart node, Block data) {
		Script script = new Script();
		
		node.childrenAccept(this, script);
		
		script.resolveContainerBlocks();
				
		return script;
	}

	/**
	 * RuleEvent() (LOOKAHEAD("<") RuleScope())? RuleName() RuleCondition() "{"
	 * 		(LOOKAHEAD(2) Statement() (LOOKAHEAD(";") ";")?)*
	 * "}"
	 */
	@Override
	public Block visit(ASTRule node, Block data) {
		Script script = (Script) data;
		Rule rule = new Rule();
		
		node.childrenAccept(this, rule);
		
		script.getRules().add(rule);
		
		return rule;
	}

	/**
	 * < IDENTIFIER >
	 */
	@Override
	public Block visit(ASTRuleEvent node, Block data) {
		Rule rule = (Rule) data;
		
		String event = node.jjtGetFirstToken().image;
		
		rule.setEvent(RuleEvent.parse(event));
		
		return rule;
	}

	/**
	 * < IDENTIFIER >
	 */
	@Override
	public Block visit(ASTRuleName node, Block data) {
		Rule rule = (Rule) data;
		
		String name = node.jjtGetFirstToken().image;
		
		rule.setName(name);
		
		return rule;
	}

	/**
	 * "<" TeamScope() (LOOKAHEAD(2) "," PlayerScope())? ">"
	 */
	@Override
	public Block visit(ASTRuleScope node, Block data) {
		Rule rule = (Rule) data;
		
		node.childrenAccept(this, rule);
		
		return rule;
	}

	/**
	 *   < IDENTIFIER >
	 * | < SCOPE_ALL >
	 */
	@Override
	public Block visit(ASTTeamScope node, Block data) {
		Rule rule = (Rule) data;
		
		String teamScope = node.jjtGetFirstToken().image;
		rule.setTeamScope(TeamScope.parse(teamScope));
		
		return rule;
	}

	/**
	 *   < IDENTIFIER >
	 * | < SCOPE_ALL >
	 */
	@Override
	public Block visit(ASTPlayerScope node, Block data) {
		Rule rule = (Rule) data;
		
		String playerScope = node.jjtGetFirstToken().image;
		rule.setPlayerScope(PlayerScope.parse(playerScope));
		
		return rule;
	}
	
	/**
	 *   "(" (Expression())? ")"
	 */
	@Override
	public Block visit(ASTRuleCondition node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			if(data != null) {
				Rule rule = (Rule) data;
				Block condition = node.jjtGetChild(0).jjtAccept(this, data);

				List<Block> conditions = new ArrayList<>();
				
				if(condition instanceof AndCondition) {
					conditions.addAll(((AndCondition) condition).unwrap());
				} else {
					conditions.add(condition);
				}
				
				for(Block c: conditions) {
					if(c instanceof Condition) {
						rule.getConditions().add(((Condition) c).toRuleCondition());
					} else {
						throw new RuntimeException("Expression is not a Condition");
					}
				}				
				
				return condition;
			} else {
				return node.jjtGetChild(0).jjtAccept(this, data);
			}
		}
		
		return null;
	}

	/**
	 *   LineStatement()
	 * | If()
	 */
	@Override
	public Block visit(ASTStatement node, Block data) {
		Container container = (Container) data;
		Block action = node.jjtGetChild(0).jjtAccept(this, data);
				
		container.getActions().add(action);
		
		return action;
	}
	/**
	 *   FunctionCall() ";")?
	 * | "$" Assignment() ";")?
	 */
	@Override
	public Block visit(ASTLineStatement node, Block data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
	}


	/**
	 * FunctionName() "(" ( Arguments() )? ")"
	 */
	@Override
	public Block visit(ASTFunctionCall node, Block data) {
		Function function = new Function();
		
		node.childrenAccept(this, function);
		
		return function;
	}

	/**
	 * < IDENTIFIER >
	 */
	@Override
	public Block visit(ASTFunctionName node, Block data) {
		Function function = (Function) data;
		
		function.setName(node.jjtGetFirstToken().image);
		
		return data;
	}

	/**
	 * Expression() ( "," Expression() )*
	 */
	@Override
	public Block visit(ASTArguments node, Block data) {
		Function function = (Function) data;
		
		for(int i = 0; i < node.jjtGetNumChildren(); i++) {
			Block argument = node.jjtGetChild(i).jjtAccept(this, data);
			function.getArguments().add(argument);
		}
		
		return data;
	}

	/**
	 * "[" AdditiveExpression() "]"
	 */
	@Override
	public Block visit(ASTArrayItem node, Block data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	/**
	 *   FnArrayItem() ( Chain() )?
	 * | VarArrayItem() ( Chain() )?
	 * 
	 * 
	 * a[3]
	 * valueInArr(GlobalVar(a), 3)
	 * 
	 * fun().a.b[3 + 2].c
	 * playerVar(ValueInArray(playerVar(playerVar(fun(), a), b), 3 + 2), c)
	 */
	@Override
	public Block visit(ASTChain node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			Block block = node.jjtGetChild(0).jjtAccept(this, data);
			
			if(block instanceof Variable) {
				return ((Variable) block).toGlobalVar();
			} else {
				return block;
			}
		} else {
			Block player = node.jjtGetChild(0).jjtAccept(this, data);
			
			PlayerVar get = new PlayerVar();
			get.setPlayer(player);
			
			Block v = node.jjtGetChild(1).jjtAccept(this, data);
			
			if(v instanceof GlobalVar) {
				get.setVar(((GlobalVar) v).getVar());
				
				return get;
			} else if(v instanceof ValueAtIndex) {
				GlobalVar gVar = (GlobalVar) ((ValueAtIndex) v).getArray();
				
				get.setVar(gVar.getVar());
				((ValueAtIndex) v).setArray(get);
				
				return v;
			} else {
				PlayerVar pVar;
				for(pVar = (PlayerVar) v; pVar.getPlayer() instanceof PlayerVar; pVar = (PlayerVar) pVar.getPlayer()) {
					if(pVar.getPlayer() instanceof ValueAtIndex && ((ValueAtIndex) pVar.getPlayer()).getArray() instanceof PlayerVar) {
						pVar = (PlayerVar) ((ValueAtIndex) pVar.getPlayer()).getArray();
					}
				}
				
				if(pVar.getPlayer() instanceof GlobalVar) {
					get.setVar(((GlobalVar) pVar.getPlayer()).getVar());
					pVar.setPlayer(get);
				} else {
					ValueAtIndex vai = (ValueAtIndex) pVar.getPlayer();
					
					get.setVar(((GlobalVar) vai.getArray()).getVar());
					
					vai.setArray(get);
				}
				
				return v;
			}			
		}
	}	
	@Override
	public Block visit(ASTFnArrayItem node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			return node.jjtGetChild(0).jjtAccept(this, data);
		} else {
			ValueAtIndex v = new ValueAtIndex();
			
			Block array = node.jjtAccept(this, data);
			Block index = node.jjtAccept(this, data);
			
			v.setArray(array);
			v.setIndex(index);
			
			return v;
		}
	}
	@Override
	public Block visit(ASTVarArrayItem node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			return node.jjtGetChild(0).jjtAccept(this, data);
		} else {
			ValueAtIndex v = new ValueAtIndex();
			
			Block array = node.jjtGetChild(0).jjtAccept(this, data);
			Block index = node.jjtGetChild(1).jjtAccept(this, data);
			
			v.setArray(array);
			v.setIndex(index);
			
			return v;
		}
	}


	/**
	 *   Chain() "=" Expression()
	 */
	@Override
	public Block visit(ASTAssignment node, Block data) {
		SetVar set = null;
		
		Block chain = node.jjtGetChild(0).jjtAccept(this, data);
		Block value = node.jjtGetChild(1).jjtAccept(this, data);
		
		GetVar get = null;
		if(chain instanceof ValueAtIndex) {
			if(((ValueAtIndex) chain).getArray() instanceof GlobalVar) {
				set = new SetGlobalVarAtIndex();
				get = (GetVar)((ValueAtIndex) chain).getArray();
				
				((SetGlobalVarAtIndex) set).setIndex(((ValueAtIndex) chain).getIndex());
			} else if(((ValueAtIndex) chain).getArray() instanceof PlayerVar) {
				set = new SetPlayerVarAtIndex();
				get = (GetVar)((ValueAtIndex) chain).getArray();

				((SetPlayerVarAtIndex) set).setPlayer(((PlayerVar)((ValueAtIndex) chain).getArray()).getPlayer());
				((SetPlayerVarAtIndex) set).setIndex(((ValueAtIndex) chain).getIndex());
			} else {
				throw new RuntimeException("Unable to set a value in an array which was returned by a function!");
			}
		} else if(chain instanceof GlobalVar) {
			get = (GetVar) chain;
			set = new SetGlobalVar();
		} else if(chain instanceof PlayerVar) {
			get = (GetVar) chain;
			set = new SetPlayerVar();
			
			((SetPlayerVar) set).setPlayer(((PlayerVar) get).getPlayer());
		} else {
			throw new RuntimeException("Unable to set a function!");
		}
		
		set.setVar(get.getVar());
		set.setValue(value);
		
		return set;
	}

	/**
	 *   ConditionalOrExpression()
	 */
	@Override
	public Block visit(ASTExpression node, Block data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
	}
	/**
	 *   ConditionalAndExpression() ("||" ConditionalAndExpression())?
	 */
	@Override
	public Block visit(ASTConditionalOrExpression node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			return node.jjtGetChild(0).jjtAccept(this, data);
		} else {
			OrCondition or = new OrCondition();
			
			or.setA(node.jjtGetChild(0).jjtAccept(this, data));
			or.setB(node.jjtGetChild(1).jjtAccept(this, data));
						
			return or;
		}
	}

	/**
	 *   RelationalExpression() ("&&" RelationalExpression())?
	 */
	@Override
	public Block visit(ASTConditionalAndExpression node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			return node.jjtGetChild(0).jjtAccept(this, data);
		} else {
			AndCondition and = new AndCondition();
			
			and.setA(node.jjtGetChild(0).jjtAccept(this, data));
			and.setB(node.jjtGetChild(1).jjtAccept(this, data));
			
			return and;
		}
	}

	/**
	 *     AdditiveExpression() (RelationalOp() AdditiveExpression())?
	 */
	@Override
	public Block visit(ASTRelationalExpression node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			return node.jjtGetChild(0).jjtAccept(this, data);
		} else {
			CompareCondition condition = (CompareCondition) node.jjtGetChild(1).jjtAccept(this, data);
			
			Block a = node.jjtGetChild(0).jjtAccept(this, data);
			Block b = node.jjtGetChild(2).jjtAccept(this, data);
			
			condition.setA(a);
			condition.setB(b);
						
			return condition;
		}
	}

	/**
	 *   MultiplicativeExpression() (AdditiveOp() AdditiveExpression())?
	 */
	@Override
	public Block visit(ASTAdditiveExpression node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			return node.jjtGetChild(0).jjtAccept(this, data);
		} else {
			MathBlock m = (MathBlock) node.jjtGetChild(1).jjtAccept(this, data);

			Block a = (Block) node.jjtGetChild(0).jjtAccept(this, data);
			Block b = (Block) node.jjtGetChild(2).jjtAccept(this, data);
			
			m.setA(a);
			m.setB(b);			
			
			return m;
		}
	}
	
	/**
	 * UnaryExpression() (MultiplocativeOp() MultiplicativeExpression())?
	 */
	@Override
	public Block visit(ASTMultiplicativeExpression node, Block data) {
		if(node.jjtGetNumChildren() == 1) {
			return node.jjtGetChild(0).jjtAccept(this, data);
		} else {
			MathBlock m = (MathBlock) node.jjtGetChild(1).jjtAccept(this, data);

			Block a = (Block) node.jjtGetChild(0).jjtAccept(this, data);
			Block b = (Block) node.jjtGetChild(2).jjtAccept(this, data);
			
			m.setA(a);
			m.setB(b);
						
			return m;
		}
	}

	/**
	 *   Number()
	 * | Boolean()
	 * | Vector()
	 * | "!" UnaryExpression()
	 * | "(" Expression() ")"
	 * | Chain()
	 */
	@Override
	public Block visit(ASTUnaryExpression node, Block data) {
		if(node.jjtGetFirstToken().image.equals("!")) {
			Not not = new Not();
			
			not.setA(node.jjtGetChild(0).jjtAccept(this, data));
			
			return not;
		} else {
			return node.jjtGetChild(0).jjtAccept(this, data);
		}
	}

	/**
	 *   "<" AdditiveExpression() "," AdditiveExpression() "," AdditiveExpression() ">"
	 */
	@Override
	public Block visit(ASTVector node, Block data) {
		Vector vector = new Vector();
		
		vector.setX(node.jjtGetChild(0).jjtAccept(this, data));
		vector.setY(node.jjtGetChild(1).jjtAccept(this, data));
		vector.setZ(node.jjtGetChild(2).jjtAccept(this, data));
		
		return vector;
	}

	@Override
	public Block visit(ASTNumber node, Block data) {
		return new Terminal(node.jjtGetFirstToken().image);
	}

	@Override
	public Block visit(ASTBoolean node, Block data) {
		return new Terminal(node.jjtGetFirstToken().image);
	}


	@Override
	public Block visit(ASTVarName node, Block data) {
		Variable var = new Variable(node.jjtGetFirstToken().image);
		
		return var.toGlobalVar();
	}


	@Override
	public Block visit(ASTRelationalOp node, Block data) {
		CompareCondition condition = new CompareCondition();
		
		condition.setOp(CompareOperator.parse(node.jjtGetFirstToken().image));
		
		return condition;
	}


	@Override
	public Block visit(ASTAdditiveOp node, Block data) {
		return MathOperator.parse(node.jjtGetFirstToken().image).createBlock();
	}


	@Override
	public Block visit(ASTMultiplocativeOp node, Block data) {
		return MathOperator.parse(node.jjtGetFirstToken().image).createBlock();
	}

	/**
	 *  "[" Expression() "]" ( "->X" |"->Y" | "->Z" )
	 */
	@Override
	public Block visit(ASTVectorComponent node, Block data) {
		ComponentOfVector comp = new ComponentOfVector();
		
		comp.setVector(node.jjtGetChild(0).jjtAccept(this, data));
		comp.setComponent(node.jjtGetLastToken().image);
		
		return comp;
	}

	@Override
	public Block visit(ASTString node, Block data) {
		return new Terminal(node.jjtGetFirstToken().image);
	}

	/**
	 * "if" "(" Expression() ")" IfElseRest() ( Else() )?
	 */
	@Override
	public Block visit(ASTIf node, Block data) {
		If ifBlock = new If();
		
		Block condition = node.jjtGetChild(0).jjtAccept(this, data);
		ifBlock.setCondition(condition);

		for(int i = 1; i < node.jjtGetNumChildren(); i++) {
			node.jjtGetChild(i).jjtAccept(this, ifBlock);
		}
		
		return ifBlock;
	}
	/**
	 *  "{" ( Statement() )* "}"
	 * | Statement()
	 */
	@Override
	public Block visit(ASTIfElseRest node, Block data) {
		Container container = (Container) data;
		
		node.childrenAccept(this, container);
		
		return data;
	}
	/**
	 *  "else" IfElseRest()
	 */
	@Override
	public Block visit(ASTElse node, Block data) {
		If ifBlock = (If) data;

		node.childrenAccept(this, ifBlock.getElse());
		
		return ifBlock;
	}
}