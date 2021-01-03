package com.gmail.andrewchouhs.model;

import java.io.Serializable;

public class GamePOJO implements Serializable
{
	/*
	 * GamePOJO 類別：「一個大地遊戲」的屬性資料 (不包含任務、道具)
	 * 
	 * 
	 * name = 大地遊戲的名稱
	 *              例：某某營隊第三天的尋寶遊戲
	 * 
	 * gamePin = 大地遊戲的遊戲 PIN (類似 Kahoot)，輸入對應的PIN就可以進入遊戲
	 *                   gamePin 可以用編輯器亂數產生
	 *                   
	 * playerLimit = 玩家數量限制，只能是固定數字，
	 *                     例：1 = 只能單人遊玩，3 = 剛好 3 個人就開始
	 *                   
	 * timeAndDateLimit = 大地遊戲的截止日期 (時間限制)，使用 UNIX 時間，毫秒為單位
	 *                                例：1608309726000 = 2020-12-18 16:42:06
	 *                   
	 * requirement = 通關此大地遊戲必須解過的任務，值為任務的 uid
	 */
	
	private static final long serialVersionUID = 1L;
	public String name;
	public String gamePin;
	public int playerLimit;
	public long timeAndDateLimit;
	public String requirement;
	
	public boolean equals(GamePOJO pojo)
	{
		if(name != pojo.name)
			return false;
		if(gamePin != pojo.gamePin)
			return false;
		if(playerLimit != pojo.playerLimit)
			return false;
		if(requirement != pojo.requirement)
			return false;
		if(timeAndDateLimit != pojo.timeAndDateLimit)
			return false;
		return true;
	}
}


