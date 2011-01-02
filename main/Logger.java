package main;

public class Logger {
	public static boolean debug = false;

	static public void debug(String msg){
		if (debug == true)
			System.out.println(msg);
	}
}
