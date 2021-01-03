package com.gmail.andrewchouhs.model;

import java.util.HashSet;
import java.util.UUID;
import com.gmail.andrewchouhs.model.TaskPOJO.Condition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class Task
{
	private final StringProperty uid = 
			new SimpleStringProperty(UUID.randomUUID().toString().split("-")[0]);
	//新任務
	private final StringProperty name = new SimpleStringProperty("新任務");
	//任務描述
	private final StringProperty description = new SimpleStringProperty("任務描述");
	
	//some property object or just uid
	private final HashSet<String> postCondition = new HashSet<>();
	private final StringProperty reward = new SimpleStringProperty();
	private final IntegerProperty rewardQty = new SimpleIntegerProperty(1);
	private final DoubleProperty posLat = new SimpleDoubleProperty(24.9676724);
	private final DoubleProperty posLng = new SimpleDoubleProperty(121.1926125);
	private final DoubleProperty posZoom = new SimpleDoubleProperty(17.58);
	private final StringProperty posName = new SimpleStringProperty("國立中央大學");
	private final StringProperty item = new SimpleStringProperty();
	private final IntegerProperty itemQty = new SimpleIntegerProperty(1);
	private final StringProperty  cipher = new SimpleStringProperty("password");
	private final ObjectProperty<Condition> condition = new SimpleObjectProperty<Condition>(Condition.POSITION);
	
	public StringProperty getUIDProperty()
	{
		return uid;
	}
	
	public StringProperty getNameProperty()
	{
		return name;
	}
	
	public StringProperty getDescriptionProperty()
	{
		return description;
	}
	
	public HashSet<String> getPostCondition()
	{
		return postCondition;
	}
	
	public StringProperty getRewardProperty()
	{
		return reward;
	}
	
	public IntegerProperty getRewardQtyProperty()
	{
		return rewardQty;
	}
	
	public DoubleProperty getPosLatProperty()
	{
		return posLat;
	}
	
	public DoubleProperty getPosLngProperty()
	{
		return posLng;
	}
	
	public DoubleProperty getPosZoomProperty()
	{
		return posZoom;
	}
	
	public StringProperty getPosNameProperty()
	{
		return posName;
	}
	
	public StringProperty getItemProperty()
	{
		return item;
	}
	
	public IntegerProperty getItemQtyProperty()
	{
		return itemQty;
	}
	
	public StringProperty getCipherProperty()
	{
		return cipher;
	}
	
	public ObjectProperty<Condition> getConditionProperty()
	{
		return condition;
	}
	public TaskPOJO exportPOJO()
	{
		TaskPOJO pojo = new TaskPOJO();
		pojo.uid = uid.get();
		pojo.name = name.get();
		pojo.description = description.get();
		pojo.postCondition.addAll(postCondition);
		pojo.reward = reward.get();
		pojo.rewardQty = rewardQty.get();
		pojo.posLat = posLat.get();
		pojo.posLng = posLng.get();
		pojo.posZoom = posZoom.get();
		pojo.posName = posName.get();
		pojo.item = item.get();
		pojo.itemQty = itemQty.get();
		pojo.cipher = cipher.get();
		pojo.condition = condition.get();
		return pojo;
	}
	
	public void importPOJO(TaskPOJO pojo)
	{
		uid.set(pojo.uid);
		name.set(pojo.name);
		description.set(pojo.description);
		postCondition.clear();
		postCondition.addAll(pojo.postCondition);
		reward.set(pojo.reward);
		rewardQty.set(pojo.rewardQty);
		posLat.set(pojo.posLat);
		posLng.set(pojo.posLng);
		posZoom.set(pojo.posZoom);
		posName.set(pojo.posName);
		item.set(pojo.item);
		itemQty.set(pojo.itemQty);
		cipher.set(pojo.cipher);
		condition.set(pojo.condition);
	}
	
	@Override
	public String toString()
	{
		return name.get();
	}
}
