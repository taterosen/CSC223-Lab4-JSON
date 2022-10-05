package input.parser;


import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import input.components.ComponentNode;
import input.components.FigureNode;
import input.exception.ParseException;

class JSONParserTest
{
	public static ComponentNode runFigureParseTest(String filename)
	{
		JSONParser parser = new JSONParser();

		String figureStr = utilities.io.FileUtilities.readFileFilterComments(filename);
		System.out.print(figureStr);
		return parser.parse(figureStr);
	}
	
	@Test
	void empty_json_string_test()
	{
		JSONParser parser = new JSONParser();

		assertThrows(ParseException.class, () -> { parser.parse("{}"); });
	}

	@Test
	void single_triangle_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/single_triangle.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	@Test
	void point_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/point.json");


		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}

	@Test
	void line_seg_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/lineseg.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}

	@Test
	void triangle_four_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/trianglefourpoints.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	@Test
	void snake_test()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/snake.json");
		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
	void collinear_line_segments()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/collinear_line_segments.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
	void crossing_symmetric_triangle()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/crossing_symmetric_triangle.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
	void fully_connected_irregular_polygon()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/fully_connected_irregular_polygon.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
	
	@Test
	void house()
	{
		ComponentNode node = JSONParserTest.runFigureParseTest("jsonfiles/house.json");

		assertTrue(node instanceof FigureNode);
		
		StringBuilder sb = new StringBuilder();
		node.unparse(sb, 0);
		System.out.println(sb.toString());
	}
}
