package com.gmail.andrewchouhs.controller;

import java.nio.charset.StandardCharsets;
import com.gmail.andrewchouhs.model.Task;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class PosPageController
{
	private Task currentTask;
	@FXML
	private WebView webView;
	private Stage stage;
	
	@FXML
	private void initialize()
	{
		webView.getEngine().load("http://map.google.com");
	}
	
	@FXML
	private void confirm()
	{
		String url = webView.getEngine().getLocation();
	
		String[] block = url.split("/");
		if(url.contains("/place/"))
		{
			int count = 0;
			for( ; count < block.length ; count++)
			{
				if(block[count].equals("place"))
				{
					count++;
					break;
				}
			}
			currentTask.getPosNameProperty().set(
					java.net.URLDecoder.decode(block[count], StandardCharsets.UTF_8));
		}
		else
		{
			currentTask.getPosNameProperty().set("");
		}
		
		int count = 0;
		for( ; count < block.length ; count++)
		{
			if(block[count].contains("@"))
			{
				String[] pos = block[count].replace("@", "").split(",");
				currentTask.getPosLngProperty().set(
						Double.valueOf(pos[1]));
				currentTask.getPosLatProperty().set(
						Double.valueOf(pos[0]));
				currentTask.getPosZoomProperty().set(
						Double.valueOf(pos[2].replace("z", "")));
			}
		}
		
//		System.out.println(webView.getEngine().getLocation());
//		System.out.println("Lng: " + currentTask.getPosLngProperty().get());
//		System.out.println("Lat: " + currentTask.getPosLatProperty().get());
//		System.out.println("Zoom: " + currentTask.getPosZoomProperty().get());
//		System.out.println("Name: " + currentTask.getPosNameProperty().get());
		stage.close();
	}
	
	@FXML
	private void cancel()
	{
		stage.close();
	}
	
	public void setCurrentTask(Task currentTtask)
	{
		this.currentTask = currentTtask;
	}
	
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
}
