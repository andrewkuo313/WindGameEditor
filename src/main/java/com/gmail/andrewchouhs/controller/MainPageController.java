package com.gmail.andrewchouhs.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainPageController
{
	private AnchorPane filePage;
	private AnchorPane gamePage;
	private AnchorPane itemPage;
	private AnchorPane taskPage;
	@FXML
	private BorderPane mainPage; 
	private FilePageController filePageController;
	private GamePageController gamePageController;
	private ItemPageController itemPageController;
	private TaskPageController taskPageController;
	
	@FXML
    private void initialize()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FilePage.fxml"));
			filePage = fxmlLoader.load();
			filePageController = (FilePageController) fxmlLoader.getController();

			
			fxmlLoader = new FXMLLoader(getClass().getResource("/ItemPage.fxml"));
			itemPage = fxmlLoader.load();
			itemPageController = (ItemPageController) fxmlLoader.getController();
			
			fxmlLoader = new FXMLLoader(getClass().getResource("/TaskPage.fxml"));
			taskPage = fxmlLoader.load();
			taskPageController = (TaskPageController) fxmlLoader.getController();
			
			fxmlLoader = new FXMLLoader(getClass().getResource("/GamePage.fxml"));
			gamePage = fxmlLoader.load();
			gamePageController = (GamePageController) fxmlLoader.getController();
			
			gamePageController.setGame(filePageController.getGame());
			gamePageController.setTaskMap(filePageController.getTaskMap());
			itemPageController.setItemMap(filePageController.getItemMap());
			taskPageController.setItemMap(filePageController.getItemMap());
			taskPageController.setTaskMap(filePageController.getTaskMap());

			itemPageController.init();
			taskPageController.init();
			gamePageController.init();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		mainPage.setCenter(gamePage);
    }
	
	@FXML
	private void toFilePage()
	{
		mainPage.setCenter(filePage);
	}
	
	@FXML
	private void toGamePage()
	{
		mainPage.setCenter(gamePage);
	}
	
	@FXML
	private void toItemPage()
	{
		mainPage.setCenter(itemPage);
	}
	
	@FXML
	private void toTaskPage()
	{
		mainPage.setCenter(taskPage);
	}
}
