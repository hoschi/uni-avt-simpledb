package main;

import relationenalgebra.ITreeNode;
import java.io.*;
import database.*;

public class Main {

//Verzeichnis der Buchversandsdatenbank	
public static final String KUNDENDB = "db";

	  public static void main(String[] args){
		  Main.readFile(args[0]);
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
		 //TODO Anfrage übersetzen
		 //TODO Anfrage ausführen
	}
	
	public static ITreeNode sqlToRelationenAlgebra(String simpleSQL){
		//TODO default
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
