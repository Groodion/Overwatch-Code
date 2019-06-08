

import java.io.FileInputStream;

import overwatchcode.interpreter.OVCVisistor;
import overwatchcode.parser.OWCParser;
import overwatchcode.parser.SimpleNode;
import overwatchcode.workshop.Block;

public class ParserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String filePath = "test.owc";

		FileInputStream fileStream = new FileInputStream(filePath);
		
		OWCParser parser = new OWCParser(fileStream);
		
		SimpleNode node = parser.Start();
		
		//node.dump(" ");
		
		Block block = (Block) node.jjtAccept(OVCVisistor.INSTANCE, null);
				
		block.updateNames(null, null, null);
		
		System.out.println(block.toOVWCode(true));
	}
}