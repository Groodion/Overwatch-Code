/* Generated By:JJTree: Do not edit this line. ASTTeamScope.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=true,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package overwatchcode.parser;

public
class ASTTeamScope extends SimpleNode {
  public ASTTeamScope(int id) {
    super(id);
  }

  public ASTTeamScope(OWCParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public overwatchcode.workshop.Block jjtAccept(OWCParserVisitor visitor, overwatchcode.workshop.Block data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=368c80a92214114d3f4ed3a81b896daf (do not edit this line) */
