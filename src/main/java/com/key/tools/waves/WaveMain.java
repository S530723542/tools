package com.key.tools.waves;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.key.tools.stock.pojo.ExCode;
import com.key.tools.waves.StockNode;

/**
 * Hello world!
 *
 */
public class WaveMain
{

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception
	{
		String file = "D:\\test\\table (1).csv";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String s = null;
		String[] ss = null;
		List<StockNode> list = new ArrayList<StockNode>();
		br.readLine();
		while ((s = br.readLine()) != null)
		{
			ss = s.split(",");
			StockNode stockNode = new StockNode();
			stockNode.setValue(Double.parseDouble(ss[4]));
			stockNode.setCheckValue(Double.parseDouble(ss[4]));
			String dateString = ss[0];
			Date date = sdf.parse(dateString);
			stockNode.setFirstDay(date);
			stockNode.setLastDay(date);
			list.add(stockNode);

		}
		br.close();

		list = Utils.reverseOrder(list);
		List<StockNode> weekList = Utils.transToWeekList(list);

		// System.out.println("list:" + list.size());
		// System.out.println("week list:" + weekList.size());
		//
		// for (int i = 0; i < weekList.size(); i++)
		// {
		// System.out.println(sdf.format(weekList.get(i).getFirstDay()) + ","
		// + sdf.format(weekList.get(i).getLastDay()));
		// }

		StockWave stockWave = new StockWave();
		stockWave.init(weekList, list);
		List<StockNode> buyAndSellNodes = stockWave.getBuyAndSellNodes();
		List<StockNode> realList = new ArrayList<StockNode>();

		StockNode stockNode = buyAndSellNodes.get(0);
		realList.add(stockNode);
		StockNodeType type = stockNode.getBuyType();
		for (int i = 1; i < buyAndSellNodes.size(); i++)
		{
			if (!buyAndSellNodes.get(i).getBuyType().equals(type))
			{
				realList.add(buyAndSellNodes.get(i));
				type = buyAndSellNodes.get(i).getBuyType();
			}
		}
		Utils.printList(realList);
		System.out.println(realList.size());
		System.out.println("Hello World!");
	}
}
