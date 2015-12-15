package com.key.tools.waves;

import java.util.Date;

public class StockNode extends Node
{
	private Date firstDay;

	private Date lastDay;

	private StockNodeType buyType=StockNodeType.NORMAL;

	private int id;

	private double checkValue;

	public double getCheckValue()
	{
		return checkValue;
	}

	public void setCheckValue(double checkValue)
	{
		this.checkValue = checkValue;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getFirstDay()
	{
		return firstDay;
	}

	public void setFirstDay(Date firstDay)
	{
		this.firstDay = firstDay;
	}

	public Date getLastDay()
	{
		return lastDay;
	}

	public void setLastDay(Date lastDay)
	{
		this.lastDay = lastDay;
	}

	public StockNodeType getBuyType()
	{
		return buyType;
	}

	public void setBuyType(StockNodeType buyType)
	{
		this.buyType = buyType;
	}

}
