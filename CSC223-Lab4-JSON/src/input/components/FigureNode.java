package input.components;

import java.util.Set;

import input.components.point.PointNode;
import input.components.point.PointNodeDatabase;
import input.components.segment.SegmentNodeDatabase;

/**
 * A basic figure consists of points, segments, and an optional description
 * 
 * Each figure has distinct points and segments (thus unique database objects).
 * 
 */
public class FigureNode implements ComponentNode
{
	protected String              _description;
	protected PointNodeDatabase   _points;
	protected SegmentNodeDatabase _segments;

	public String              getDescription()    { return _description; }
	public PointNodeDatabase   getPointsDatabase() { return _points; }
	public SegmentNodeDatabase getSegments()       { return _segments; }
	
	public FigureNode(String description, PointNodeDatabase points, SegmentNodeDatabase segments)
	{
		_description = description;
		_points = points;
		_segments = segments;
	}

	/**
	 * Unparses the parts of a FigureNode and puts them into
	 * a given StringBuilder object.
	 * @param sb Stringbuilder to hold final result
	 * @param level int indent level
	 */
	@Override
	public void unparse(StringBuilder sb, int level)
	{
		sb.append(indent(level) + "Figure\n{\n");
		
        // unparse description
		sb.append(indent(level + 1) + "Description : \"" + _description + "\"\n");
		
		//unparse points
		this._points.unparse(sb, level + 1);
		
		//unparse segments
		this._segments.unparse(sb, level + 1);
		
		sb.append(indent(level) + "}\n");
		
	}
	
}