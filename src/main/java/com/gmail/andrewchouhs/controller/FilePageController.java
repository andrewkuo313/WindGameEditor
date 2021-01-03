package com.gmail.andrewchouhs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import com.gmail.andrewchouhs.Main;
import com.gmail.andrewchouhs.model.Game;
import com.gmail.andrewchouhs.model.GamePOJO;
import com.gmail.andrewchouhs.model.Item;
import com.gmail.andrewchouhs.model.ItemPOJO;
import com.gmail.andrewchouhs.model.Task;
import com.gmail.andrewchouhs.model.TaskPOJO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class FilePageController
{
	//https://stackoverflow.com/questions/2358886/how-can-i-deserialize-the-object-if-it-was-moved-to-another-package-or-renamed
	private final Game game = new Game();
	private final ObservableMap<String, Task> taskMap = FXCollections.observableHashMap();
	private final ObservableMap<String, Item> itemMap = FXCollections.observableHashMap();
	private final FileChooser fileChooser = new FileChooser();
	private File currentFile = null;
	private Dialog<ButtonType> saveChangeDialog = new Dialog<>();
	
	private GamePOJO gameCheck;
	private Map<String, ItemPOJO> itemCheck;
	private Map<String, TaskPOJO> taskCheck;
	
	public FilePageController()
	{
		fileChooser.getExtensionFilters().add(new ExtensionFilter("大地遊戲檔案", "*.wg"));
		Main.getStage().setOnCloseRequest((e)->
		{
			
			if(openSaveChangeDialog())
				Platform.exit();
			else
				e.consume();
		});
		gameCheck = game.exportPOJO();
		
		//initialize save change dialog
		saveChangeDialog.getDialogPane().getButtonTypes().addAll
		(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		saveChangeDialog.setTitle(("儲存請求"));
		saveChangeDialog.setHeaderText("是否儲存變更？");
		
		// initialize task check or will cause save change bug
		Task task = new Task();
		taskMap.put(task.getUIDProperty().get(), task);
		taskCheck = taskMap.entrySet()
		.stream()
		.collect(
				Collectors.toMap(Map.Entry::getKey ,
						entry -> entry.getValue().exportPOJO()));
	}
	
	private boolean isDataChanged()
	{
		return !(gameCheck.equals(game.exportPOJO()) && taskEquals() && itemEquals());
	}
	
	private boolean itemEquals()
	{
		if(itemCheck == null)
		{
			if(itemMap.isEmpty())
				return true;
			return false;
		}
		return 
				itemMap.entrySet()
				.stream()
				.collect(
						Collectors.toMap(Map.Entry::getKey ,
								entry -> entry.getValue().exportPOJO())).equals(itemCheck);
	}
	
	private boolean taskEquals()
	{
		if(taskCheck == null)
		{
			if(taskMap.isEmpty())
				return true;
			return false;
		}
		return 
				taskMap.entrySet()
				.stream()
				.collect(
						Collectors.toMap(Map.Entry::getKey ,
								entry -> entry.getValue().exportPOJO())).equals(taskCheck);
	}
	
	private boolean openSaveChangeDialog()
	{
		if(isDataChanged())
		{
			Optional<ButtonType> result = saveChangeDialog.showAndWait();
			if(result.isPresent())
			{
				if(result.get() == ButtonType.YES)
				{
					save();
					if(isDataChanged())
						return false;
				}
				if(result.get() == ButtonType.CANCEL)
				{	
					return false;
				}
			}
		} 
		return true;
	}
	
	@FXML
	private void create()
	{
		if(openSaveChangeDialog())
		{
			currentFile = null;
			game.resetToDefault();
			taskMap.clear();
			Task task = new Task();
			taskMap.put(task.getUIDProperty().get(), task);
			taskCheck = taskMap.entrySet()
					.stream()
					.collect(
							Collectors.toMap(Map.Entry::getKey ,
									entry -> entry.getValue().exportPOJO()));
			
			
			itemMap.clear();
			gameCheck = game.exportPOJO();
			itemCheck = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	private void open()
	{
		if(openSaveChangeDialog())
		{
			File file = fileChooser.showOpenDialog(Main.getStage());
		    if(file != null)
		    {
		    	currentFile = file;
		    	try
				(
						FileInputStream fileIn = new FileInputStream(currentFile);
						ObjectInputStream objectIn = new ObjectInputStream(fileIn)
				)
				{
		    		
		    		/*
		    		 * 每個 *.wg 檔代表不同的大地遊戲
		    		 * 編輯器產生的 *.wg 檔內依序會有三個不同類別大物件
		    		 * POJO 物件用途請參考各類別所附說明
		    		 * 
		    		 * 
		    		 * 1. Map<String, ItemPOJO> 物件
		    		 * key (String) 所對應的是道具(Item) 的 uid，用於辨識道具
		    		 * 
		    		 * 2. Map<String, TaskPOJO> 物件
		    		 * key (String) 所對應的是任務(Task) 的 uid，用於辨識任務 
		    		 * 
		    		 * 3. GamePOJO 物件
		    		 */
		    		
		    		// Item
					itemCheck = (Map<String, ItemPOJO>) objectIn.readObject();
					
					itemMap.clear();
					itemMap.putAll(
					itemCheck.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey ,
								entry -> 
					{
						Item item = new Item();
						item.importPOJO(entry.getValue());
						return item;
					})));
		    		
		    		// Task
					taskCheck = (Map<String, TaskPOJO>) objectIn.readObject();
					
					taskMap.clear();
					taskMap.putAll(
					taskCheck.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey ,
								entry -> 
					{
						Task task = new Task();
						task.importPOJO(entry.getValue());
						return task;
					})));
		    		
		    		// Game
					GamePOJO gamePOJO = (GamePOJO) objectIn.readObject();
					gameCheck = gamePOJO;
					game.importPOJO(gamePOJO);
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
		    }
		}
	    
	}
	
	@FXML
	private void save()
	{
		if(currentFile == null)
			saveAs();
		else
		{
			try
			(
					FileOutputStream fileOut = new FileOutputStream(currentFile);
					ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)
			)
			{
				// Item
				itemCheck = 
				itemMap.entrySet()
				.stream()
				.collect(
						Collectors.toMap(Map.Entry::getKey ,
								entry -> entry.getValue().exportPOJO()));
				objectOut.writeObject(itemCheck);
				
				// Task
				taskCheck = 
				taskMap.entrySet()
				.stream()
				.collect(
						Collectors.toMap(Map.Entry::getKey ,
								entry -> entry.getValue().exportPOJO()));
				objectOut.writeObject(taskCheck);
				
				// Game
				gameCheck = game.exportPOJO();
				objectOut.writeObject(game.exportPOJO());
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void saveAs()
	{
		File file = fileChooser.showSaveDialog(Main.getStage());
		
	    if(file != null)
	    {
	    	currentFile = file;
	    	save();
	    }
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public ObservableMap<String, Task> getTaskMap()
	{
		return taskMap;
	}
	
	public ObservableMap<String, Item> getItemMap()
	{
		return itemMap;
	}
}
