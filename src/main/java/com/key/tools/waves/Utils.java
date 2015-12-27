package com.key.tools.waves;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;

public class Utils
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static Gson gson = new Gson();

	public static void printList(List<StockNode> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			System.out.println(list.get(i).getType() + ","
					+ list.get(i).getBuyType() + ","
					+ sdf.format(list.get(i).getFirstDay()) + ","
					+ sdf.format(list.get(i).getLastDay()) + ","
					+ list.get(i).getValue() + ","
					+ list.get(i).getCheckValue());
		}
	}

	public static List<StockNode> reverseOrder(List<StockNode> list)
	{
		Collections.sort(list, new Comparator<StockNode>()
		{

			@Override
			public int compare(StockNode arg0, StockNode arg1)
			{
				if (arg0.getFirstDay().after(arg1.getFirstDay()))
				{
					return 1;
				} else if (arg0.getFirstDay().before(arg1.getFirstDay()))
				{
					return -1;
				}

				return 0;
			}
		});
		return list;
	}

	public static List<StockNode> transToWeekList(List<StockNode> list)
	{

		List<StockNode> weekList = new ArrayList<StockNode>();

		StockNode newNode = new StockNode();
		weekList.add(newNode);
		StockNode node = list.get(0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(node.getFirstDay());
		int beginWeekDay = calendar.get(Calendar.DAY_OF_WEEK);

		newNode.setFirstDay(node.getFirstDay());
		newNode.setLastDay(node.getFirstDay());
		newNode.setValue(node.getValue());

		for (int i = 1; i < list.size(); i++)
		{
			node = list.get(i);
			calendar.setTime(node.getFirstDay());
			int weekDay = calendar.get(Calendar.DAY_OF_WEEK);

			if (weekDay <= beginWeekDay)
			{
				newNode = new StockNode();
				weekList.add(newNode);
				newNode.setFirstDay(node.getFirstDay());
				newNode.setLastDay(node.getFirstDay());
				newNode.setValue(node.getValue());
				beginWeekDay = weekDay;
			} else
			{
				newNode.setLastDay(node.getFirstDay());
				newNode.setValue(node.getValue());

			}
		}

		return weekList;
	}
}
