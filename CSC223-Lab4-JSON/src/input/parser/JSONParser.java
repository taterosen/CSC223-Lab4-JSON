package input.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import input.components.*;
import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;
import input.exception.ParseException;

public class JSONParser
{
	protected ComponentNode  _astRoot;

	/**
	 * constructor. sets _astRoot to null
	 */
	public JSONParser()
	{
		_astRoot = null;
	}

	/**
	 * throws ParseException with message passed in
	 * @param message explanation of error
	 * @throws ParseException
	 */
	private void error(String message) throws ParseException
	{
		throw new ParseException("Parse error: " + message);
	}

	/**
	 * Parse a string into a tree
	 * @param str String to parse
	 * @return ComponentNode, the root of the tree built from str
	 * @throws ParseException
	 */
	public ComponentNode parse(String str) throws ParseException
	{
		// Parsing is accomplished via the JSONTokenizer class.
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();

        // TODO: Build the whole AST, check for return class object, and return the root
		JSONObject fig = JSONroot.getJSONObject("Figure");
		
		String description = parseDescription(fig.getJSONObject("Description"));
		PointNodeDatabase points = parsePoints(fig.getJSONArray("Points"));
		SegmentNodeDatabase segments = parseSegments(fig.getJSONArray("Segments"), points);
		
		FigureNode root = new FigureNode(description, points, segments);
		
		return root;
	}
	
	/**
	 * 
	 * @param figure
	 * @return 
	 */
	private String parseDescription(JSONObject figure) {
		return figure.getString("Description");
	}
	
	private PointNodeDatabase parsePoints(JSONArray arr) {
		PointNodeDatabase points = new PointNodeDatabase();
		
		for(int i = 0; i < arr.length(); i++) {
			JSONObject JSONpoint = arr.getJSONObject(i);
			PointNode point = new PointNode(JSONpoint.getString("name"), JSONpoint.getInt("x"),
											JSONpoint.getInt("y"));
			points.put(point);
		}
		
		return points;
	}
	
	private SegmentNodeDatabase parseSegments(JSONArray arr, PointNodeDatabase points) {
		SegmentNodeDatabase snd = new SegmentNodeDatabase();
		
		for(int i = 0; i <= arr.length(); i++) {
			JSONObject JSONseg = arr.getJSONObject(i);
			
			Iterator<String> segKeys = JSONseg.keys();
			String currentKey = segKeys.next();

			PointNode edgeStart = points.getPoint(currentKey);
			
			JSONArray JSONedgeEnds = JSONseg.getJSONArray(currentKey);
			List<PointNode> edgeEnds = parseSegmentsHelper(JSONedgeEnds, points);
			
			snd.addAdjacencyList(edgeStart, edgeEnds);
		}
		
		return snd;
	}
	
	private List<PointNode> parseSegmentsHelper(JSONArray arr, PointNodeDatabase points) {
		List<PointNode> edgeEnds = null;
		for(int i = 0; i < arr.length(); i++) {
			edgeEnds.add(points.getPoint(arr.getString(i)));
		}
		
		return edgeEnds;
	}


}