package com.key.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.key.tools.waves.Node;
import com.key.tools.waves.StockNode;
import com.key.tools.waves.Utils;

public class Test
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public static void main(String[] args) throws Exception
	{
		List<StockNode> list = new ArrayList<StockNode>();

		StockNode node1 = new StockNode();
		node1.setFirstDay(new Date(System.currentTimeMillis()));
		list.add(node1);

		StockNode node2 = new StockNode();
		node2.setFirstDay(new Date(System.currentTimeMillis() - 1000));
		list.add(node2);

		list = Utils.reverseOrder(list);

		System.out.println(Utils.gson.toJson(list));
	}
}
