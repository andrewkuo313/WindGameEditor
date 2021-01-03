package com.gmail.andrewchouhs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	private static Stage stage;
    @Override
    public void start(Stage stage)
    {
    	try
		{
    		this.stage = stage;
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/MainPage.fxml"))));
			stage.setTitle("大地遊戲編輯器");
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    public static Stage getStage()
    {
    	return stage;
    }
    
    public static void main(String[] args)
    {
        launch();
    }
}