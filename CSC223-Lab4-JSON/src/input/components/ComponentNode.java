package input.components;

public interface ComponentNode
{
	void unparse(StringBuilder sb, int level);
	
	default String indent(int level) {
		String indents = "";
		for(int i = 0; i < level; i++) 
			indents = indents + "\t";
		
		return indents;
	}
}
