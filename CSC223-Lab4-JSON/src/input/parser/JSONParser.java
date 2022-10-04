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

	public JSONParser()
	{
		_astRoot = null;
	}

	private void error(String message)
	{
		throw new ParseException("Parse error: " + message);
	}

	public ComponentNode parse(String str) throws ParseException
	{
		// Parsing is accomplished via the JSONTokenizer class.
		JSONTokener tokenizer = new JSONTokener(str);
		JSONObject  JSONroot = (JSONObject)tokenizer.nextValue();

        // TODO: Build the whole AST, check for return class object, and return the root
		
		String description = parseDescription(JSONroot.getJSONObject("Figure"));
		PointNodeDatabase points = parsePoints(JSONroot.getJSONObject("Figure"));
		SegmentNodeDatabase segments = parseSegments(JSONroot.getJSONObject("Figure"));
		
		FigureNode root = new FigureNode(description, points, segments);
		
		return root;
	}
	
	public String parseDescription(JSONObject figure) {
		return figure.getString("Description");
	}
	
	public PointNodeDatabase parsePoints(JSONObject figure) {
		PointNodeDatabase points = new PointNodeDatabase();
		JSONArray arr = figure.getJSONArray("Points");
		
		for(int i = 0; i < arr.length(); i++) {
			JSONObject JSONpoint = arr.getJSONObject(i);
			PointNode point = new PointNode(JSONpoint.getString("name"), JSONpoint.getInt("x"),
											JSONpoint.getInt("y"));
			points.put(point);
		}
		
		return points;
	}
	
	public SegmentNodeDatabase parseSegments(JSONObject figure) {
		SegmentNodeDatabase snd = new SegmentNodeDatabase();
		JSONArray arr = figure.getJSONArray("Segments");
		PointNodeDatabase points = parsePoints(figure);
		
		
		
		
		for(int i = 0; i < arr.length(); i++) {
			JSONObject JSONseg = arr.getJSONObject(i);
			
			Iterator<String> segKeys = JSONseg.keys();
			String currentKey = segKeys.next();

			PointNode edgeStart = points.getPoint(currentKey);
			
			
			// **STILL WORKING ON THIS!!**
			List<PointNode> edgeEnds = new ArrayList<PointNode>();
			
			snd.addAdjacencyList(edgeStart, edgeEnds);
			
						
		}
		
		return snd;
	}

    // TODO: implement supporting functionality

}