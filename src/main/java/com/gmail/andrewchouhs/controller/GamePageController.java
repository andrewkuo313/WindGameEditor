package com.gmail.andrewchouhs.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import com.gmail.andrewchouhs.model.Game;
import com.gmail.andrewchouhs.model.Item;
import com.gmail.andrewchouhs.model.Task;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.util.converter.DateTimeStringConverter;

public class GamePageController
{
	@FXML
	private Label nameLabel;
	@FXML
	private Label gamePinLabel;
	@FXML
	private Label playerLimitLabel;
	@FXML
	private Label timeLimitLabel;
	@FXML
	private Label requirementLabel;
	@FXML
	private Button nameButton;
	@FXML
	private Button gamePinButton;
	@FXML
	private Button playerLimitButton;
	@FXML
	private Button timeLimitButton;
	@FXML
	private Button requirementButton;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField gamePinTextField;
	@FXML
	private TextField playerLimitTextField;
	@FXML
	private TextField timeLimitTextField;
	private Game game;
	private ObservableMap<String, Task> taskMap;
	
	//@FXML
	public void init()
	{
		nameLabel.textProperty().bind(game.getNameProperty());
		gamePinLabel.textProperty().bind(game.getGamePinProperty());
		playerLimitLabel.textProperty().bind(game.getPlayerLimitProperty().asString());
		timeLimitLabel.textProperty().bind(game.getTimeLimitProperty());
		game.getRequirementProperty().addListener((ob,n,v)->refreshRequirementLabel());
		
		nameTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		gamePinTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		playerLimitTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		timeLimitTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		nameTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				game.getNameProperty().set(nameTextField.getText());
		});
		gamePinTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				game.getGamePinProperty().set(gamePinTextField.getText());
		});
		playerLimitTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				game.getPlayerLimitProperty().set(Integer.valueOf(playerLimitTextField.getText()));
		});
		timeLimitTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				game.getTimeLimitProperty().set(timeLimitTextField.getText());
		});
		
		try
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 時 mm 分 ss 秒");
			
			timeLimitTextField.setTextFormatter(
					new TextFormatter<>(
							new DateTimeStringConverter(format), format.parse("2020 年 12 月 31 日 23 時 59 分 59 秒")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		refreshRequirementLabel();
	}
	
	@FXML
	private void nameOnMouseEnter()
	{
		nameButton.setVisible(true);
	}
	
	@FXML
	private void nameOnMouseExited()
	{
		nameButton.setVisible(false);
	}
	
	@FXML
	private void nameOnMouseClicked()
	{
		nameLabel.setVisible(false);
		nameButton.setVisible(false);
		nameTextField.setVisible(true);
		nameTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
		nameTextField.requestFocus();
		nameTextField.setText(game.getNameProperty().get());
		
	}
	
	@FXML
	private void gamePinOnMouseEnter()
	{
		gamePinButton.setVisible(true);
	}
	
	@FXML
	private void gamePinOnMouseExited()
	{
		gamePinButton.setVisible(false);
	}
	
	@FXML
	private void gamePinOnMouseClicked()
	{
		gamePinLabel.setVisible(false);
		gamePinButton.setVisible(false);
		gamePinTextField.setVisible(true);
		gamePinTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
		gamePinTextField.requestFocus();
		gamePinTextField.setText(game.getGamePinProperty().get());
	}
	
	@FXML
	private void playerLimitOnMouseEnter()
	{
		playerLimitButton.setVisible(true);
	}
	
	@FXML
	private void playerLimitOnMouseExited()
	{
		playerLimitButton.setVisible(false);
	}
	
	@FXML
	private void playerLimitOnMouseClicked()
	{
		playerLimitLabel.setVisible(false);
		playerLimitButton.setVisible(false);
		playerLimitTextField.setVisible(true);
		playerLimitTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
		playerLimitTextField.requestFocus();
		playerLimitTextField.setText(game.getPlayerLimitProperty().asString().get());
	}
	
	@FXML
	private void timeLimitOnMouseEnter()
	{
		timeLimitButton.setVisible(true);
	}
	
	@FXML
	private void timeLimitOnMouseExited()
	{
		timeLimitButton.setVisible(false);
	}
	
	@FXML
	private void timeLimitOnMouseClicked()
	{
		timeLimitLabel.setVisible(false);
		timeLimitButton.setVisible(false);
		timeLimitTextField.setVisible(true);
		timeLimitTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
		timeLimitTextField.requestFocus();
		timeLimitTextField.setText(game.getTimeLimitProperty().get());
	}
	
	@FXML
	private void requirementOnMouseEnter()
	{
		requirementButton.setVisible(true);
	}
	
	@FXML
	private void requirementOnMouseExited()
	{
		requirementButton.setVisible(false);
	}
	
	private void refreshRequirementLabel()
	{
		requirementLabel.textProperty().unbind();
		if(game.getRequirementProperty().isEmpty().get())
			requirementLabel.setText("請選擇一個任務");
		else 
			requirementLabel.textProperty().bind(taskMap.get(game.getRequirementProperty().get()).getNameProperty());
	}
	
	@FXML
	private void requirementOnMouseClicked()
	{
//		requirementLabel.setVisible(false);
//		requirementButton.setVisible(false);
		if(!taskMap.isEmpty())
		{
			Task task = taskMap.get(game.getRequirementProperty().get());
			ChoiceDialog<Task> choiceDialog= new ChoiceDialog<>(task, taskMap.values());
			choiceDialog.setTitle(("選擇過關條件"));
			choiceDialog.setHeaderText("請選擇通過大地遊戲的過關條件：");
			Optional<Task> taskOp = choiceDialog.showAndWait();
			if(taskOp.isPresent())
				game.getRequirementProperty().set(taskOp.get().getUIDProperty().get());
			refreshRequirementLabel();
		}
	}
	
	@FXML
	private void randomGamePin()
	{
		game.setGamePinRandomly();
	}
	
	@FXML
	private void defocusAllComponent()
	{
		nameLabel.setVisible(true);
		nameTextField.setVisible(false);
		nameTextField.setPrefWidth(0);
		gamePinLabel.setVisible(true);
		gamePinTextField.setVisible(false);
		gamePinTextField.setPrefWidth(0);
		playerLimitLabel.setVisible(true);
		playerLimitTextField.setVisible(false);
		playerLimitTextField.setPrefWidth(0);
		timeLimitLabel.setVisible(true);
		timeLimitTextField.setVisible(false);
		timeLimitTextField.setPrefWidth(0);
		requirementLabel.setVisible(true);
		//requirementTextField.setVisible(false);
	}
	
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	public void setTaskMap(ObservableMap<String, Task> taskMap)
	{
		this.taskMap = taskMap;
	}
}
