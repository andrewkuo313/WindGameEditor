package com.gmail.andrewchouhs.model;

import java.io.Serializable;
import java.util.HashSet;

public class TaskPOJO implements Serializable
{
	/*
	 * TaskPOJO 類別：每個大地遊戲中「任務」的屬性資料
	 * 
	 * 
	 * uid = 任務的 ID，目的是辨識不同任務，編輯器會自動產生
	 * 
	 * name = 任務的名稱
	 *              例：前往中央大學宵夜街！
	 * 
	 * description = 任務說明，也可以當作提示使用，
	 *                      例：中央大學宵夜街是#%*#&%(*#&(*，請前往中央大學宵夜街並掃描QRCode。
	 * 
	 * postCondition = 必須先解完 Set 內的前置任務，此任務才會顯示，Set 內的值為任務的 uid
	 * 
	 * reward = 解完任務可以獲得的獎勵，值為道具的 uid
	 * 
	 * rewardQty = 解完任務可以獲得的獎勵數量
	 * 
	 * 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 * Condition.POSITION:
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 * posLat = 目的地根據 Zoom 值產生的緯度
	 * 
	 * posLng = 目的地根據 Zoom 值產生的精度
	 * 
	 * posZoom = 目的地在 Google 地圖上的 Zoom 值
	 * 
	 * posName = 目的地在 Google 地圖上的名稱
	 * 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 * Condition.ITEM:
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 * item = 任務要求的道具，值為道具的 uid
	 * 
	 * itemQty = 任務要求的道具數量
	 * 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 * Condition.CIPHER:
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 * cipher = 任務要求輸入的密碼
	 * 
	 * 
	 * 
	 * condition = 一個任務有三種型態，三種擇一：
	 *                   一種是 POSITION，達成方式是抵達任務指示經緯度的座標
	 *                   一種是 ITEM，達成方式是湊到指定數量的道具
	 *                   一種是 CIPHER，達成方式是輸入指定的密碼
	 *                    
	 */
	
	private static final long serialVersionUID = 1L;
	
	public String uid;
	public String name;
	public String description;
	
	public HashSet<String> postCondition = new HashSet<>();
	public String reward;
	public int rewardQty;
	
	public double posLat;
	public double posLng;
	public double posZoom;
	public String posName;
	
	public String item;
	public int itemQty;
	public String cipher;

	public Condition condition;

	@Override
	public boolean equals(Object obj)
	{
		if(! (obj instanceof TaskPOJO))
			return false;
		TaskPOJO pojo = (TaskPOJO)obj;
		if(!uid.equals(pojo.uid))
			return false;
		if(!name.equals(pojo.name))
			return false;
		if(!description.equals(pojo.description))
			return false;
		if(!postCondition.equals(pojo.postCondition))
			return false;
		RewardBlock:
		{
			if(!(reward == null && pojo.reward == null))
			{
				if(reward != null && pojo.reward != null)
				{
					if(!reward.equals(pojo.reward))
						return false;
					break RewardBlock;
				}
				return false;
			}
		}
		if(rewardQty != pojo.rewardQty)
			return false;
		if(posLat != pojo.posLat)
			return false;
		if(posLng != pojo.posLng)
			return false;
		if(posZoom != pojo.posZoom)
			return false;
		if(!posName.equals(pojo.posName))
			return false;
		ItemBlock:
		{
			if(!(item == null && pojo.item == null))
			{
				if(item != null && pojo.item != null)
				{
					if(!item.equals(pojo.item))
						return false;
					break ItemBlock;
				}
				return false;
			}
		}
		if(itemQty != pojo.itemQty)
			return false;
		if(!cipher.equals(pojo.cipher))
			return false;
		if(condition != pojo.condition)
			return false;
		return true;
	}
	
	public enum Condition
	{
		POSITION, ITEM,  CIPHER
	}
}
