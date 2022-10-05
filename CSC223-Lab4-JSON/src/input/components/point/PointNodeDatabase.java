package input.components.point;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import input.components.ComponentNode;
import utilities.math.MathUtilities;

/**
 * Stores Point Nodes in LinkedHashSet
 * @author georgelamb, taterosen, brycenaddison
 * @date Wed September 7 2022
 */
public class PointNodeDatabase implements ComponentNode {
	protected Set<PointNode> _points;

	/**
	 * create empty PointNodeDataBase
	 */
	public PointNodeDatabase() {

		_points = new LinkedHashSet<PointNode>();

	}
	
	/**
	 * create PointNodeDatabase with list of point nodes in it
	 * @param list of PointNodes 
	 */
	public PointNodeDatabase(List<PointNode> list) {

		_points = new LinkedHashSet<PointNode>(list);

	}
	
	/**
	 * @param node to put into database
	 */
	public void put(PointNode node) {
		_points.add(node);
	}

	/**
	 * 
	 * @param node to check 
	 * @return if node is contained
	 */
	public boolean contains(PointNode node) {
		return this.getPoint(node) != null;

	}
	
	/**
	 * 
	 * @param x value of node
	 * @param y value of node
	 * @return if node is contained
	 */
	public boolean contains(double x, double y) {
		return this.getPoint(x, y) != null;
	}
	
	/**
	 * 
	 * @param node to get name of
	 * @return name of node
	 */
	public String getName(PointNode node) {
		return this.getPoint(node).getName();
	}
	
	/**
	 * 
	 * @param x value of node to get name of 
	 * @param y value of node to get name of 
	 * @return name of node
	 */
	public String getName(double x, double y) {
		return this.getPoint(x, y).getName();

	}
	
	/**
	 * 
	 * @param node to get node of 
	 * @return node to get
	 */
	public PointNode getPoint(PointNode node) {
		return this.getPoint(node.getX(), node.getY());
	}

	/**
	 * 
	 * @param x value to get node of
	 * @param y value to get node of
	 * @return node to get
	 */
	public PointNode getPoint(double x, double y) {
		for (PointNode p: this._points) {
			if (MathUtilities.doubleEquals(x,p.getX()) && MathUtilities.doubleEquals(y, p.getY())) {
				return p;
			}
		}
		return null;

	}
	
	/**
	 * 
	 * @param x value to get node of
	 * @param y value to get node of
	 * @return node to get
	 */
	public PointNode getPoint(String name) {
		for (PointNode p: this._points) {
			if (p._name.equals(name)) return p;
		}
		return null;

	}
	
	/**
	 * Unparses the parts of a PointNodeDatabase and puts them into
	 * a given StringBuilder object.
	 */
	@Override
	public void unparse(StringBuilder sb, int level) {
		sb.append(indent(level) + "Points:\n" + indent(level) +  "{\n");
		
		for(PointNode p : _points) {
			p.unparse(sb, level + 1);
		}
		
		sb.append(indent(level) + "}\n");
	}


}
