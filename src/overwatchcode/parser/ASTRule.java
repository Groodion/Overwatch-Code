/* Generated By:JJTree: Do not edit this line. ASTRule.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package overwatchcode.parser;

public
class ASTRule extends SimpleNode {
  public ASTRule(int id) {
    super(id);
  }

  public ASTRule(OWCParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public overwatchcode.workshop.Block jjtAccept(OWCParserVisitor visitor, overwatchcode.workshop.Block data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=474227eae1abcc69112ef0506284b432 (do not edit this line) */
