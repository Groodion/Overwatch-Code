package gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import overwatchcode.Manager;
import overwatchcode.interpreter.OVCVisistor;
import overwatchcode.parser.OWCParser;
import overwatchcode.parser.SimpleNode;
import overwatchcode.workshop.Block;

public class Controller implements Initializable {
	
	
	@FXML
	private CheckBox minCodeCB;
	@FXML
	private ChoiceBox<String> languageCB;
	@FXML
	private TextField libPathTF;
	@FXML
	private TextField varPathTF;
	@FXML
	private SwingNode owcTAContainer;
	private RSyntaxTextArea owcTA;
	@FXML
	private SwingNode owwTAContainer;
	private RSyntaxTextArea owwTA;
    @FXML
    private CheckBox extraElementsCB;
    

	
	@FXML 
	private TextArea console;

	private FileChooser fileChooser;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rB) {
		fileChooser = new FileChooser();
		
		updateLanguages();
		
		languageCB.valueProperty().addListener((b, o, n) -> {
			Manager.INSTANCE.setSelectedLanguage(n);
		});
		
		owcTA = new RSyntaxTextArea(20, 60);
		owcTA.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		owcTA.setCodeFoldingEnabled(true);
		RTextScrollPane sp = new RTextScrollPane(owcTA);
		
		owcTAContainer.setContent(sp);
		
		owwTA = new RSyntaxTextArea(20, 60);
		owwTA.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		owwTA.setCodeFoldingEnabled(true);
		sp = new RTextScrollPane(owwTA);
		
		owwTAContainer.setContent(sp);
	}
	
	
	public void openOWCFile() {
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("OverwatchCode", "owc", "txt"));
		File file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			try {
				String string = fileToText(file);
				owcTA.setText(string);
				
			} catch (FileNotFoundException e) {
				console.setText("File Not Found Exception!");
			}
		}
	}
	public void saveOWCFile() {
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("OverwatchCode", "owc", "txt"));
		File file = fileChooser.showSaveDialog(null);
		
		if(file != null) {
			try {
				if(!file.exists()) {
					file.createNewFile();
				}
				PrintWriter out = new PrintWriter(file);
				for(String line: owcTA.getText().split("\n"))
					out.println(line);
				out.flush();
				out.close();
			} catch (IOException e) {
				console.setText("Unable to save File");
			}
		}
	}
	public void openOWWFile() {
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("OverwatchWorkshopCode", "txt"));
		File file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			try {
				String string = fileToText(file);
				owwTA.setText(string);
				
			} catch (FileNotFoundException e) {
				console.setText("File Not Found Exception!");
			}
		}		
	}
	public void saveOWWFile() {
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("OverwatchWorkshopCode", "txt"));
		File file = fileChooser.showSaveDialog(null);
		
		if(file != null) {
			try {
				if(!file.exists()) {
					file.createNewFile();
				}
				PrintWriter out = new PrintWriter(file);
				for(String line: owwTA.getText().split("\n"))
					out.println(line);
				out.flush();
				out.close();
			} catch (IOException e) {
				console.setText("Unable to save File");
			}
		}
	}
	public void copyToClipboard() {
		StringSelection selection = new StringSelection(owwTA.getText());
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);
		owwTA.selectAll();
	}
	public void openLibFile() {
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Json", "json"));
		File file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			libPathTF.setText(file.getAbsolutePath());
			try {
				Manager.INSTANCE.loadLibrary(file);
			} catch (FileNotFoundException e) {
				console.setText("File Not Found Exception!");
			}
		}
	}
	public void openVarFile() {
		fileChooser.setSelectedExtensionFilter(new ExtensionFilter("Json", "json"));
		File file = fileChooser.showOpenDialog(null);
		
		if(file != null) {
			varPathTF.setText(file.getAbsolutePath());
			try {
				Manager.INSTANCE.loadVarNameRules(file);
			} catch (FileNotFoundException e) {
				console.setText("File Not Found Exception!");
			}
		}
	}
	
	public void convertToOWW() {
		OWCParser parser = new OWCParser(new StringReader(owcTA.getText()));
		
		try {
			SimpleNode node = parser.Start();
			
			Block block = (Block) node.jjtAccept(OVCVisistor.INSTANCE, null);
					
			block.updateNames(null, null, null);
	
			owwTA.setText(block.toOVWCode(minCodeCB.isSelected()));
			console.setText(Manager.INSTANCE.getReport());
		} catch(Exception e) {
			console.setText(e.toString());
			
			e.printStackTrace();
		}
	}
	
	
	private String fileToText(File file) throws FileNotFoundException {
		StringBuilder sB = new StringBuilder();
		
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNextLine()) {
			sB.append(scanner.nextLine());
			sB.append('\n');
		}
		
		scanner.close();
		
		return sB.toString();
	}
	
	private void updateLanguages() {
		languageCB.getItems().setAll(Manager.INSTANCE.getAvailableLanguages());
		
		if(!Manager.INSTANCE.getAvailableLanguages().contains(Manager.INSTANCE.getSelectedLanguage())) {
			Manager.INSTANCE.setSelectedLanguage(Manager.INSTANCE.getAvailableLanguages().iterator().next());
		}
		
		languageCB.setValue(Manager.INSTANCE.getSelectedLanguage());
	}
}