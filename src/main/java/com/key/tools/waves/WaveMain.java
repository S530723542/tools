package com.key.tools.waves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.key.tools.waves.StockNode;

/**
 * Hello world!
 *
 */
public class WaveMain
{

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public static String getPossible(String args) throws Exception
	{
		File file = new File(args);

		List<StockNode> realList = getBuyAndSellListFromFile(file);
		if (realList == null)
		{
			return null;
		}
		StockNode node = realList.get(realList.size() - 1);
		if (node.getFirstDay().after(
				new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2)))
		{
			String result = sdf.format(node.getFirstDay()) + ","
					+ node.getBuyType() + "," + args;
			return result;
		}

		return null;

	}

	public static Buyer getScore(String args, int day) throws Exception
	{
		File file = new File(args);

		List<StockNode> realList = getBuyAndSellListFromFile(file, day);
		if (realList == null)
		{
			return null;
		}

		final double initMoney = 10000;
		Buyer buyer = new Buyer();
		buyer.init(initMoney);

		int N = realList.size();
		if (realList.get(realList.size() - 1).getBuyType()
				.equals(StockNodeType.BUY))
		{
			N = N - 1;
		}

		for (int i = 0; i < N; i++)
		{
			StockNode stockNode = realList.get(i);
			switch (stockNode.getBuyType())
			{
				case BUY:
					buyer.buy(stockNode.getValue());
					break;
				case SELL:
					buyer.sell(stockNode.getValue());
					break;

				default:
					break;
			}
		}
		// if (buyer.getMoney()==0)
		// {
		// for (int i = 0; i < realList.size(); i++)
		// {
		// System.out.println(realList.get(i).getBuyType());
		// }
		// System.exit(0);
		// }

		return buyer;
	}

	public static List<StockNode> getBuyAndSellListFromFile(File file)
			throws Exception
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		String s = null;
		String[] ss = null;
		List<StockNode> list = new ArrayList<StockNode>();
		br.readLine();
		while ((s = br.readLine()) != null)
		{
			ss = s.split(",");
			StockNode stockNode = new StockNode();
			if (ss.length != 7)
			{
				continue;
			}
			if (Double.parseDouble(ss[2])==Double.parseDouble(ss[3]))
			{
				continue;
			}
			stockNode.setValue(Double.parseDouble(ss[4]));
			stockNode.setCheckValue(Double.parseDouble(ss[4]));
			String dateString = ss[0];
			Date date = sdf.parse(dateString);

			stockNode.setFirstDay(date);
			stockNode.setLastDay(date);

				list.add(stockNode);
			

		}
		br.close();

		if (list.size() == 0)
		{
			return null;
		}
		list = Utils.reverseOrder(list);

		List<StockNode> weekList = Utils.transToWeekList(list);

		StockWave stockWave = new StockWave();
		stockWave.init(weekList, list);
		List<StockNode> buyAndSellNodes = stockWave.getBuyAndSellNodes();
		List<StockNode> realList = new ArrayList<StockNode>();
		if (buyAndSellNodes.size() == 0)
		{
			return null;
		}

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

		return realList;
	}

	public static List<StockNode> getBuyAndSellListFromFile(File file, long day)
			throws Exception
	{

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		String s = null;
		String[] ss = null;
		List<StockNode> list = new ArrayList<StockNode>();
		br.readLine();
		while ((s = br.readLine()) != null)
		{
			ss = s.split(",");
			StockNode stockNode = new StockNode();
			if (ss.length != 7)
			{
				continue;
			}
			if (Double.parseDouble(ss[2])==Double.parseDouble(ss[3]))
			{
//				System.out.println(s);
				continue;
			}
			stockNode.setValue(Double.parseDouble(ss[4]));
			stockNode.setCheckValue(Double.parseDouble(ss[4]));
			String dateString = ss[0];
			Date date = sdf.parse(dateString);
			if (day != -1)
			{
				if (date.before(new Date(System.currentTimeMillis() - 1000 * 60
						* 60 * 24 * day)))
				{
					continue;
				}
			}

			stockNode.setFirstDay(date);
			stockNode.setLastDay(date);
			list.add(stockNode);

		}
		br.close();

		if (list.size() == 0)
		{
			return null;
		}
		list = Utils.reverseOrder(list);

		List<StockNode> weekList = Utils.transToWeekList(list);

		StockWave stockWave = new StockWave();
		stockWave.init(weekList, list);
		List<StockNode> buyAndSellNodes = stockWave.getBuyAndSellNodes();
		List<StockNode> realList = new ArrayList<StockNode>();
		if (buyAndSellNodes.size() == 0)
		{
			return null;
		}

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

		return realList;
	}

	public static double getRealValue(List<StockNode> list)
	{

		return getRealValue(list, 0.5);
	}

	public static double getRealValue(List<StockNode> list, double k)
	{
		double value = 0;
		for (StockNode stockNode : list)
		{
			value = value * (1 - k) + stockNode.getValue() * k;
		}
		return value;
	}
}
