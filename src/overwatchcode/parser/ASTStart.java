/* Generated By:JJTree: Do not edit this line. ASTStart.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package overwatchcode.parser;

public
class ASTStart extends SimpleNode {
  public ASTStart(int id) {
    super(id);
  }

  public ASTStart(OWCParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public overwatchcode.workshop.Block jjtAccept(OWCParserVisitor visitor, overwatchcode.workshop.Block data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=a3fdcf3ea5ce444901baa55d2a6da32c (do not edit this line) */
