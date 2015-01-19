package com.key.tools.common;

public class ListRecord<T>
{
	private T	data;
	private int	pageNum		= 1;
	private int	pageSize	= 20;

	public int getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(int pageNum)
	{
		if (pageNum >= 0)
		{
			this.pageNum = pageNum;
		}

	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		if (pageSize >= 0)
		{
			this.pageSize = pageSize;
		}
	}

	public T getData()
	{
		return data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

}
