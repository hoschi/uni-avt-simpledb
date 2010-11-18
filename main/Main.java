package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import parser.gene.ParseException;
import parser.gene.SimpleSQLParser;
import parser.syntaxtree.CompilationUnit;
import parser.visitor.ObjectDepthFirst;
import relationenalgebra.ITreeNode;
import database.Table;

public class Main {

//Verzeichnis der Buchversandsdatenbank	
public static final String KUNDENDB = "db";

	  public static void main(String[] args){
		  Logger.debug = true;
		  Logger.debug("test");
		  Main.sqlToRelationenAlgebra("select mycol from mytable, myother");
		  //Main.readFile(args[1]);
	  }
		
	public static void printKundenDB(){
		File dir = new File(Main.KUNDENDB);

		File[] children = dir.listFiles();
		if (children == null) {
			// Either dir does not exist or is not a directory
			System.out.println("no files");
		} else {
			for (int i=0; i<children.length; i++) {
				// Get filename of file or directory
				File file = children[i];
				if (!file.isDirectory()) {
					Table t = Table.loadTable(file.getAbsolutePath() + File.separator + file.getName());
					if (t != null)
						System.out.print(t);
				}
			}
		}
	}
	
	public static void createKundenDB(){
		Main.readFile("kundendb.txt");
	}
	
	public static void execute(String simpleSQL){
		 //TODO Anfrage �bersetzen
		 //TODO Anfrage ausf�hren
	}
	
	public static ITreeNode sqlToRelationenAlgebra(String simpleSQL){
		SimpleSQLParser parser = 
			 new SimpleSQLParser(
			 new StringReader(simpleSQL));
		parser.setDebugALL(true);
		try{ CompilationUnit cu = 
			parser.CompilationUnit();
		ObjectDepthFirst v = new ObjectDepthFirst();
		cu.accept(v,null);
			}catch(ParseException e){
				System.err.println(e.getMessage());
				return null;
			}

		return null;
	}
	
	
	private static void executePlan(ITreeNode plan){
		//TODO
		 
	}
	
	private static void readFile(String filename) {
		File f = new File(filename);
		if (!f.isFile())
			return;
		try{
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(filename);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)	 {
				// Print the content on the console
				Main.execute(strLine);
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}
