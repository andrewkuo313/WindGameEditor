package com.gmail.andrewchouhs.model;

import java.util.UUID;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item
{
	private final StringProperty uid = 
			new SimpleStringProperty(UUID.randomUUID().toString().split("-")[0]);
	private final StringProperty name = new SimpleStringProperty("新道具");
	private final IntegerProperty initialQty = new SimpleIntegerProperty(0);
	
	
	public StringProperty getUIDProperty()
	{
		return uid;
	}
	
	public StringProperty getNameProperty()
	{
		return name;
	}
	
	public IntegerProperty getInitialQtyProperty()
	{
		return initialQty;
	}
	
	public ItemPOJO exportPOJO()
	{
		ItemPOJO pojo = new ItemPOJO();
		pojo.uid = uid.get();
		pojo.name = name.get();
		pojo.initialQty = initialQty.get();
		return pojo;
	}
	
	public void importPOJO(ItemPOJO pojo)
	{
		uid.set(pojo.uid);
		name.set(pojo.name);
		initialQty.set(pojo.initialQty);
	}
	
	@Override
	public String toString()
	{
		return name.get();
	}
}
