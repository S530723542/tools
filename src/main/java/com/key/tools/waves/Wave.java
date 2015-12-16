package com.key.tools.waves;

import java.util.ArrayList;
import java.util.List;

public class Wave<T extends Node>
{

	protected List<T> originList;

	public void init(List<T> list)
	{
		originList = new ArrayList<T>(list.size());

		for (T w : list)
		{
			originList.add(w);
		}

		setTypeOfList(originList);

	}

	private void setTypeOfList(List<T> list)
	{
		if (list.size() < 3)
		{
			return;
		}

		for (int i = 2; i < list.size(); i++)
		{
			T firstNode = list.get(i - 2);
			T secondNode = list.get(i - 1);
			T thirdNode = list.get(i);

			if (secondNode.getValue() >= thirdNode.getValue()
					&& secondNode.getValue() >= firstNode.getValue())
			{
				secondNode.setType(NodeType.TOP);
			} else if (secondNode.getValue() <= thirdNode.getValue()
					&& secondNode.getValue() <= firstNode.getValue())
			{
				secondNode.setType(NodeType.LOW);
			}
		}
	}

	public List<T> getTopNodes()
	{

		List<T> nodes = new ArrayList<T>();
		for (T w : originList)
		{
			if (w.getType().equals(NodeType.TOP))
			{
				nodes.add(w);
			}
		}

		return nodes;
	}
	
	public List<T> getLowNodes()
	{
		List<T> nodes = new ArrayList<T>();
		for (T w : originList)
		{
			if (w.getType().equals(NodeType.LOW))
			{
				nodes.add(w);
			}
		}

		return nodes;
	}
	
	public List<T> getSpecielNodes()
	{
		List<T> nodes = new ArrayList<T>();
		for (T w : originList)
		{
			if (!w.getType().equals(NodeType.NORMOL))
			{
				nodes.add(w);
			}
		}

		return nodes;
	}
}
