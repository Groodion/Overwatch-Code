/* Generated By:JJTree: Do not edit this line. ASTChain.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package overwatchcode.parser;

public
class ASTChain extends SimpleNode {
  public ASTChain(int id) {
    super(id);
  }

  public ASTChain(OWCParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public overwatchcode.workshop.Block jjtAccept(OWCParserVisitor visitor, overwatchcode.workshop.Block data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=919b7b2c59d7c80ac8c15fcb8f4a31c1 (do not edit this line) */