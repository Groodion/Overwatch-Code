/* Generated By:JJTree: Do not edit this line. ASTExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package overwatchcode.parser;

public
class ASTExpression extends SimpleNode {
  public ASTExpression(int id) {
    super(id);
  }

  public ASTExpression(OWCParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public overwatchcode.workshop.Block jjtAccept(OWCParserVisitor visitor, overwatchcode.workshop.Block data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=34b4bfc918d1ecf7c09ca918c62e48a1 (do not edit this line) */
