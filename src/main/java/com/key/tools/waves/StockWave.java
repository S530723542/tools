package com.key.tools.waves;

import java.util.ArrayList;
import java.util.List;

public class StockWave extends Wave<StockNode>
{
	protected List<StockNode> allNodes;

	public void init(List<StockNode> list, List<StockNode> allNodes)
	{
		super.init(list);
		initAll(allNodes);
		initAllId();
		setReadNode();
		setCheckValue();
		setToBuyNode();
		setToSellNode();
		setBuyNode();
		setSellNodes();

	}

	public List<StockNode> getBuyAndSellNodes()
	{
		List<StockNode> list = new ArrayList<StockNode>();
		for (int i = 0; i < allNodes.size(); i++)
		{
			if (allNodes.get(i).getBuyType().equals(StockNodeType.BUY)
					|| allNodes.get(i).getBuyType().equals(StockNodeType.SELL))
			{
				list.add(allNodes.get(i));
			}
		}

		return list;
	}

	private void setSellNodes()
	{
//		double value = Double.MAX_VALUE;
		List<StockNode> list = getToSellNodes();
		int j = 0;
		for (int i = 0; i < allNodes.size(); i++)
		{
			if (j>=list.size())
			{
				break;
			}
			StockNode toSellNode = list.get(j);
//			if (allNodes.get(i).getBuyType().equals(StockNodeType.BUY))
//			{
//				value = allNodes.get(i).getCheckValue();
//			}
//			if (allNodes.get(i).getValue() > value)
//			{
//				allNodes.get(i).setBuyType(StockNodeType.SELL);
//				value = Double.MAX_VALUE;
//			}
			if (allNodes.get(i).getFirstDay().equals(toSellNode.getLastDay()))
			{
				allNodes.get(i).setBuyType(StockNodeType.SELL);
				j++;
//				value = Double.MAX_VALUE;
			}
		}
	}

	private List<StockNode> getToSellNodes()
	{
		List<StockNode> list = new ArrayList<StockNode>();
		for (int i = 0; i < originList.size(); i++)
		{
			if (originList.get(i).getBuyType().equals(StockNodeType.TOSELL))
			{
				list.add(originList.get(i));
			}
		}
		return list;
	}

	private void setToSellNode()
	{
		List<StockNode> list = getSpecielNodes();

		for (int i = 1; i < list.size(); i++)
		{
			StockNode firstNode = list.get(i - 1);
			StockNode secondNode = list.get(i);
			if (firstNode.getBuyType().equals(StockNodeType.READ))
			{
				if (secondNode.getBuyType().equals(StockNodeType.TOBUY))
				{
					secondNode.setBuyType(StockNodeType.POINT);
				} else
				{
					secondNode.setBuyType(StockNodeType.TOSELL);
				}
			}

		}
	}

	private void setBuyNode()
	{
		int j = 0;
		List<StockNode> dates = getToBuyNodes();
		for (int i = 0; i < allNodes.size(); i++)
		{
			if (j >= dates.size())
			{
				break;
			}
			StockNode date = dates.get(j);

			if (allNodes.get(i).getFirstDay().equals(date.getLastDay()))
			{
				j++;
				if (allNodes.get(i).getValue() < date.getCheckValue())
				{
					allNodes.get(i).setBuyType(StockNodeType.BUY);
					allNodes.get(i).setCheckValue(date.getCheckValue());
				}

			}
		}
	}

	private List<StockNode> getToBuyNodes()
	{
		List<StockNode> list = new ArrayList<StockNode>();
		for (int i = 0; i < originList.size(); i++)
		{
			if (originList.get(i).getBuyType().equals(StockNodeType.TOBUY))
			{
				list.add(originList.get(i));
			}
		}
		return list;
	}

	private void initAll(List<StockNode> allNodes)
	{
		this.allNodes = new ArrayList<StockNode>(allNodes.size());

		for (StockNode w : allNodes)
		{
			this.allNodes.add(w);
		}
	}

	private void setToBuyNode()
	{
		for (int i = 0; i < originList.size(); i++)
		{
			if (originList.get(i).getBuyType().equals(StockNodeType.READ))
			{
				if (originList.get(i + 1).getValue() < originList.get(i).getCheckValue())
				{

					originList.get(i + 1).setBuyType(StockNodeType.TOBUY);

					originList.get(i + 1).setCheckValue(originList.get(i).getCheckValue());

				}

			}
		}
	}

	private void setCheckValue()
	{
		List<StockNode> list = getSpecielNodes();
		if (list.size() < 2)
		{
			return;
		}
		for (int i = 1; i < list.size(); i++)
		{
			if (list.get(i).getBuyType().equals(StockNodeType.READ))
			{
				list.get(i).setCheckValue(list.get(i - 1).getValue());
			}
		}
	}

	private void setReadNode()
	{
		List<StockNode> list = getLowNodes();
		if (list.size() < 2)
		{
			return;
		}

		for (int i = 1; i < list.size(); i++)
		{
			StockNode firstNode = list.get(i - 1);
			StockNode secondNode = list.get(i);
			if (secondNode.getValue() > firstNode.getValue())
			{
				secondNode.setBuyType(StockNodeType.READ);
			}
		}
	}

	private void initAllId()
	{
		for (int i = 0; i < allNodes.size(); i++)
		{
			allNodes.get(i).setId(i);
		}
	}
}
