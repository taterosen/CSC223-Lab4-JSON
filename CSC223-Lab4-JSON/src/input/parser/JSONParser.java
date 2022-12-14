/**
 * Parses from a JSON file to a tree and unparses in reverse
 * JSON file looks like:
 * { "Triangle" :
 *   { "Description" : "triangle with three points",
 *     "Points" : [ { "name" : "A", "x" : 0, "y" : 0 }
 *                  { "name" : "B", "x" : 1, "y" : 1 }
 *                  { "name" : "C", "x" : 0, "y" : 1 }
 *                ]
 *     "Segments" : [ { "A" : ["B", "C"] }
 *                    { "B" : ["C", "A"] }
 *                    { "C" : ["B", "A"] }
 *                  ]
 *   }
 * }
 *  To a tree structure like:
 *               |-- Description
 *               |
 *  FigureNode --|-- PointNodeDatabase (contains a Set of PointNode, each containing a name, x, and y)
 *               |
 *               |-- SegmentNodeDatabase (contains a map of PointNode and PointNodeDatabase)
 *  
 *  @author Tate Rosen, Regan Richardson, Hanna King
 *  @date 10/5/2022
 */
 
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
		
		try
			{
				JSONroot.getJSONObject(JSON_Constants.JSON_FIGURE);
			}
				
		catch (JSONException e)
			{
				throw new ParseException(e);
			}

		JSONObject fig = JSONroot.getJSONObject(JSON_Constants.JSON_FIGURE);

		String description = parseDescription(fig.getString(JSON_Constants.JSON_DESCRIPTION));
		PointNodeDatabase points = parsePoints(fig.getJSONArray(JSON_Constants.JSON_POINT_S));
		
		SegmentNodeDatabase segments = parseSegments(fig.getJSONArray(JSON_Constants.JSON_SEGMENTS), points);
		
		 
		
		return new FigureNode(description, points, segments);
	}
	
	/**
	 * Get the description of the figure as a String
	 * @param figure JSONObject for the figure
	 * @return String value for the "Description" key
	 */
	private String parseDescription(String description)
	{
		return description;
	}
	
	/**
	 * Convert JSONObject points to PointNodes and add to PointNodeDatabase.
	 * @param arr JSONArray of points
	 * @return PointNodeDatabase of PointNode objects for the figure
	 */
	private PointNodeDatabase parsePoints(JSONArray arr)
	{
		PointNodeDatabase points = new PointNodeDatabase();
		
		for(int i = 0; i < arr.length(); i++)
		{
			JSONObject JSONpoint = arr.getJSONObject(i);
			PointNode point = new PointNode(JSONpoint.getString(JSON_Constants.JSON_NAME), JSONpoint.getInt(JSON_Constants.JSON_X),
											JSONpoint.getInt(JSON_Constants.JSON_Y));
			points.put(point);
		}
		
		return points;
	}
	
	/**
	 * Parses a JSONArray of segments in a JSON file and 
	 * puts them into a SegmentNodeDatabase. 
	 * @param arr JSONArray of JSONObject segments
	 * @param points PointNodeDataBase of PointNode s
	 * @return SegmentNodeDatabase for the figure
	 */
	private SegmentNodeDatabase parseSegments(JSONArray arr, PointNodeDatabase points)
	{
		SegmentNodeDatabase snd = new SegmentNodeDatabase();
		
		for(int i = 0; i < arr.length(); i++)
		{
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
	
	private List<PointNode> parseSegmentsHelper(JSONArray arr, PointNodeDatabase points)
	{
		List<PointNode> edgeEnds = new ArrayList<>();
		for(int i = 0; i < arr.length(); i++)
		{
			edgeEnds.add(points.getPoint(arr.getString(i)));
		}
		
		return edgeEnds;
	}
}