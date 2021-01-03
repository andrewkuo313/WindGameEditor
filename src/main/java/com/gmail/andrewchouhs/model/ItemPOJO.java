package com.gmail.andrewchouhs.model;

import java.io.Serializable;

public class ItemPOJO implements Serializable
{
	/*
	 * ItemPOJO 類別：「可使用道具」的屬性資料
	 * 
	 * 
	 * uid = 道具的 ID，目的是在任務(Task) 使用 uid 辨識道具，編輯器會自動產生
	 * 
	 * name = 道具的名稱，例子：遺失的鑰匙
	 * 
	 * initialQty = 遊戲一開始時，每一隊的這項道具的初始數量
	 *                  例：2 = 一開場每一隊道具欄都會有 2 個此道具
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	public String uid;
	public String name;
	public int initialQty;
	
	@Override
	public boolean equals(Object obj)
	{
		if(! (obj instanceof ItemPOJO))
			return false;
		ItemPOJO pojo = (ItemPOJO)obj;
		if(uid != pojo.uid)
			return false;
		if(name != pojo.name)
			return false;
		if(initialQty != pojo.initialQty)
			return false;
		return true;
	}
}
