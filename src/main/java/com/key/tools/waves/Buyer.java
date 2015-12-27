package com.key.tools.waves;

public class Buyer
{

	private Type type;

	private double money;

	private double num;

	private double lastPrice;

	private int rightCount = 0;

	private int wrongCount = 0;
	
	private double initMoney;
	
	public double getScore()
	{
		return money/initMoney;
	}
	

	public double getLastPrice()
	{
		return lastPrice;
	}

	public void setLastPrice(double lastPrice)
	{
		this.lastPrice = lastPrice;
	}

	public double getInitMoney()
	{
		return initMoney;
	}

	public void setInitMoney(double initMoney)
	{
		this.initMoney = initMoney;
	}

	public int getRightCount()
	{
		return rightCount;
	}

	public void setRightCount(int rightCount)
	{
		this.rightCount = rightCount;
	}

	public int getWrongCount()
	{
		return wrongCount;
	}

	public void setWrongCount(int wrongCount)
	{
		this.wrongCount = wrongCount;
	}

	public void init(double money)
	{
		this.money = money;
		this.initMoney=money;
	}

	public void buy(double price)
	{
		if (money != 0)
		{
			lastPrice = price;
		}
		num = num + money / price;
		money = 0;
	}

	public void sell(double price)
	{

		if (num != 0)
		{
			if (lastPrice >= price)
			{
				wrongCount++;
			} else
			{
				rightCount++;
			}
		}
		money = money + num * price;
		num = 0;

	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public double getNum()
	{
		return num;
	}

	public void setNum(double num)
	{
		this.num = num;
	}

	public enum Type
	{
		BUY, SELL
	}
}
