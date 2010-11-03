package test.block1;

import java.io.StringReader;

import org.junit.Test;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import parser.gene.SimpleSQLParser;
import parser.syntaxtree.CompilationUnit;


public class A1RelationalQuery {
	
	@Test
	public void testForSqlSyntaxTree() throws parser.gene.ParseException {
		String sql = "select name from user where name like 'blubb'";
		SimpleSQLParser parser = new SimpleSQLParser(new StringReader(sql));
		parser.setDebugALL(true);
		@SuppressWarnings("unused")
		CompilationUnit cu = parser.CompilationUnit();
		sql = "truianetirane";
	}
}
