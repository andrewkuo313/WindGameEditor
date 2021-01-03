package com.gmail.andrewchouhs.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Game
{
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty gamePin = new SimpleStringProperty();
	private final IntegerProperty playerLimit = new SimpleIntegerProperty();
	private final StringProperty timeLimit = new SimpleStringProperty();
//	private final SimpleObjectProperty<LocalDate> dateLimit = new SimpleObjectProperty<>();
	private final StringProperty requirement = new SimpleStringProperty();
	//requirement
	
	public Game()
	{
		resetToDefault();
	}
	
	public StringProperty getNameProperty()
	{
		return name;
	}
	public StringProperty getGamePinProperty()
	{
		return gamePin;
	}
	public IntegerProperty getPlayerLimitProperty()
	{
		return playerLimit;
	}
	public StringProperty getTimeLimitProperty()
	{
		return timeLimit;
	}
//	public ObjectProperty<LocalDate> getDateLimitProperty()
//	{
//		return dateLimit;
//	}
	
	public String setGamePinRandomly()
	{
		String pin = UUID.randomUUID().toString().split("-")[0];
		gamePin.set(pin);
		return pin;
	}
	
	public StringProperty getRequirementProperty()
	{
		return requirement;
	}
	
	public GamePOJO exportPOJO()
	{
		GamePOJO pojo = new GamePOJO();
		pojo.name = name.get();
		pojo.gamePin = gamePin.get();
		pojo.playerLimit = playerLimit.get();
		pojo.requirement = requirement.get();
		
		try
		{
			
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 時 mm 分 ss 秒");
			Date date = dateFormat.parse(timeLimit.get());
			
			calendar.setTime(date);
			pojo.timeAndDateLimit = calendar.toInstant().toEpochMilli();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//buggy
//		pojo.timeLimit = timeLimit.get().toEpochSecond(ZoneOffset.UTC);
		
		return pojo;
	}

	public void importPOJO(GamePOJO pojo)
	{
		name.set(pojo.name);
		gamePin.set(pojo.gamePin);
		playerLimit.set(pojo.playerLimit);
		requirement.set(pojo.requirement);
		
		try
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 時 mm 分 ss 秒");
			
			Date date = Date.from(Instant.ofEpochMilli(pojo.timeAndDateLimit));
			
			timeLimit.set(dateFormat.format(date));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		dateLimit.set(Instant.ofEpochSecond(pojo.timeLimit).atZone(ZoneId.of("Asia/Taipei")).toLocalDateTime());
	}
	
	public void resetToDefault()
	{
		name.set("遊戲名稱");
		gamePin.set("邀請碼");
		playerLimit.set(1);
		timeLimit.set("2020 年 12 月 31 日 23 時 59 分 59 秒");
		requirement.set(null);
//		dateLimit.set(LocalDateTime.now());
	}
}
