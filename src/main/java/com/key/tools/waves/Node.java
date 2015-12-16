package com.key.tools.waves;

public class Node
{
	private NodeType type=NodeType.NORMOL;
	
	private double value;

	public NodeType getType()
	{
		return type;
	}

	public void setType(NodeType type)
	{
		this.type = type;
	}

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}
	
	
}
