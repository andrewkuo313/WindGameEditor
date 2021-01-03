package com.gmail.andrewchouhs.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import com.gmail.andrewchouhs.model.Item;
import com.gmail.andrewchouhs.model.Task;
import com.gmail.andrewchouhs.model.TaskPOJO.Condition;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class TaskPageController
{
	@FXML
	private Label nameLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label posLabel;
	@FXML
	private Label rewardLabel;
	@FXML
	private Button nameButton;
	@FXML
	private Button descriptionButton;
	@FXML
	private Button posButton;
	@FXML
	private Button conditionButton;
	@FXML
	private Button rewardButton;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField descriptionTextField;
	
	@FXML
	private ListView<Task> taskListView;
	
	@FXML
	private ListView<Task> postConditionListView;
	@FXML
	private ChoiceBox<Task> postConditionChoiceBox;
	@FXML
	private ChoiceBox<Condition> conditionChoiceBox;
	
	private ObservableMap<String, Item> itemMap;
	private ObservableMap<String, Task> taskMap;
	private final ObservableList<Task> taskList = FXCollections.observableArrayList();

	private Task currentTask;
	private final ObservableList<Task> currentPostCondition = FXCollections.observableArrayList();
	
	public void init()
	{
//		//https://stackoverflow.com/questions/38487797/javafx-populate-tableview-with-an-observablemap-that-has-a-custom-class-for-its
		taskMap.addListener((MapChangeListener.Change<? extends String, ? extends Task> change)->
		{
			boolean removed = change.wasRemoved();
		    if (removed != change.wasAdded())
		    {
		        if (removed)
		            // no put for existing key
		            // remove pair completely
		        	taskList.remove(change.getValueRemoved());
		        else
		            // add new entry
		        	taskList.add(change.getValueAdded());
		    }
		    else
		    {
		        // replace existing entry
		    }
		});
		
		
		taskListView.setCellFactory(param->new ListCell<Task>()
		{
            @Override
            protected void updateItem(Task task, boolean empty)
            {
                super.updateItem(task, empty);

                if (empty || task == null || task.getNameProperty().get() == null) {
                    setText(null);
                } else {
                    setText(task.getNameProperty().get());
                }
            }
        });
		
		
		taskListView.setItems(taskList);
//		createTask();
		taskList.add(taskMap.values().stream().findFirst().get());
		
		defocusAllComponent();
		
		taskListView.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> 
	            {	
	            	
	            	defocusAllComponent();
	            	currentTask = newValue;
	            	
	            	if(newValue == null)
	            	{
	            		nameLabel.setText("");
		            	descriptionLabel.setText("");
	            	}
	            	else
	            	{
	            		nameLabel.setText(newValue.getNameProperty().get());
	            		descriptionLabel.setText(newValue.getDescriptionProperty().get());
	            		posLabel.textProperty().bind(
	            				newValue.getPosNameProperty().concat(
	            						new SimpleStringProperty(" ").concat(
	            						newValue.getPosLatProperty().asString().concat(
	            								new SimpleStringProperty(" ").concat(
	            								newValue.getPosLngProperty().asString())))));
	            		
	            		currentPostCondition.clear();
	            		currentPostCondition.addAll(newValue.getPostCondition().stream()
	            		.filter(taskMap::containsKey)
	            		.map(taskMap::get).collect(Collectors.toList()));
	            		
	            		postConditionChoiceBox.setItems(FXCollections.emptyObservableList());
	            		postConditionChoiceBox.setItems(taskList);
	            		
	            		conditionChoiceBox.setItems(FXCollections.emptyObservableList());
	            		conditionChoiceBox.setItems(
	            				FXCollections.observableArrayList(Condition.POSITION, Condition.ITEM, Condition.CIPHER));
	            		conditionChoiceBox.getSelectionModel().select(newValue.getConditionProperty().get());
	            		
	            		refreshRewardLabel();
	            	}
	            });
		
		nameTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		descriptionTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		nameTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
			{
				currentTask.getNameProperty().set(nameTextField.getText());
				nameLabel.setText(nameTextField.getText());
			}
		});
		descriptionTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
			{
				currentTask.getDescriptionProperty().set(descriptionTextField.getText());
				descriptionLabel.setText(descriptionTextField.getText());
			}
		});
		
		
		postConditionChoiceBox.setItems(taskList);
		postConditionChoiceBox.setConverter(new StringConverter<Task>() 
		{
			@Override
			public String toString(Task object)
			{
				return object.getNameProperty().get();
			}
			@Override
			public Task fromString(String string)
			{
				return null;
			}
			
		});
		
		postConditionListView.setItems(currentPostCondition);
		postConditionListView.setCellFactory(param->new ListCell<Task>()
		{
            @Override
            protected void updateItem(Task task, boolean empty)
            {
                super.updateItem(task, empty);

                if (empty || task == null || task.getNameProperty().get() == null) {
                    setText(null);
                } else {
                    setText(task.getNameProperty().get());
                }
            }
        });
		
//		conditionChoiceBox.setItems(
//				FXCollections.observableArrayList(Condition.CIPHER, Condition.ITEM, Condition.CIPHER));
		conditionChoiceBox.valueProperty().addListener((ob, oldValue, newValue) ->
		{
			if(newValue != null)
				currentTask.getConditionProperty().set(newValue);
		});
		
		conditionChoiceBox.setConverter(new StringConverter<Condition>() 
		{
			@Override
			public String toString(Condition object)
			{
				switch(object)
				{
					case POSITION:
						return "前往 " + currentTask.getPosNameProperty().get() +
        						"(" + currentTask.getPosLatProperty().get() + "," + 
        										currentTask.getPosLngProperty().get() + ")";
					case ITEM:
						if(currentTask.getItemProperty().get() != null)
							return "給予 " + currentTask.getItemQtyProperty().get() + " 個 "
									+ itemMap.get(currentTask.getItemProperty().get()).getNameProperty().get();
						else
							return "給予道具";
					case CIPHER:
						return "輸入密碼 " + currentTask.getCipherProperty().get();
				}
				return null;
			}
			@Override
			public Condition fromString(String string)
			{
				return null;
			}
			
		});
		
	
		
	}
	
	@FXML
	private Task createTask()
	{
		Task task = new Task();
		taskMap.put(task.getUIDProperty().get(), task);
		return task;
	}
	
	@FXML
	private void deleteTask()
	{
		taskMap.remove(currentTask.getUIDProperty().get());
	}
	
	@FXML
	private void defocusAllComponent()
	{
		taskListView.refresh();
		
		nameLabel.setVisible(true);
		nameTextField.setVisible(false);
		nameTextField.setPrefWidth(0);
		descriptionLabel.setVisible(true);
		descriptionTextField.setVisible(false);
		descriptionTextField.setPrefWidth(0);
		
		
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
		nameTextField.setText(currentTask.getNameProperty().get());
		
	}
	
	@FXML
	private void descriptionOnMouseEnter()
	{
		descriptionButton.setVisible(true);
	}
	
	@FXML
	private void descriptionOnMouseExited()
	{
		descriptionButton.setVisible(false);
	}
	
	@FXML
	private void descriptionOnMouseClicked()
	{
		descriptionLabel.setVisible(false);
		descriptionButton.setVisible(false);
		descriptionTextField.setVisible(true);
		descriptionTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
		descriptionTextField.requestFocus();
		descriptionTextField.setText(currentTask.getDescriptionProperty().get());
	}
	
	@FXML
	private void posOnMouseEnter()
	{
		posButton.setVisible(true);
	}
	
	@FXML
	private void posOnMouseExited()
	{
		posButton.setVisible(false);
	}
	
	@FXML
	private void posOnMouseClicked()
	{
		try
		{
			//must select task first or it will cause null this need to be fixed
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PosPage.fxml"));
			stage.setScene(new Scene(fxmlLoader.load()));
			stage.setTitle("選取目的地");
			PosPageController posPageController = (PosPageController) fxmlLoader.getController();
			posPageController.setCurrentTask(currentTask);
			posPageController.setStage(stage);
			stage.showAndWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@FXML
	private void addPostCondition()
	{
		if(!postConditionChoiceBox.getSelectionModel().isEmpty())
		{
			Task selected = postConditionChoiceBox.getSelectionModel().getSelectedItem();
			if(!currentPostCondition.contains(selected))
			{
				currentPostCondition.add(selected);
				currentTask.getPostCondition().add(selected.getUIDProperty().get());
			}
		}
	}
	
	@FXML
	private void removePostCondition()
	{
		if(!postConditionListView.getSelectionModel().isEmpty())
		{
			Task selected = postConditionListView.getSelectionModel().getSelectedItem();
			currentPostCondition.remove(selected);
			currentTask.getPostCondition().remove(selected.getUIDProperty().get());
		}
	}
	
	@FXML
	private void conditionOnMouseEnter()
	{
		if(currentTask != null 
				&& !(currentTask.getConditionProperty().get() == Condition.POSITION))
		conditionButton.setVisible(true);
	}
	
	@FXML
	private void conditionOnMouseExited()
	{
		conditionButton.setVisible(false);
	}
	
	@FXML
	private void conditionOnMouseClicked()
	{
		switch(currentTask.getConditionProperty().get())
		{
			case POSITION:
				break;
			case ITEM:
				if(!itemMap.isEmpty())
				{
					Item item = itemMap.get(currentTask.getItemProperty().get());
					ChoiceDialog<Item> choiceDialog= new ChoiceDialog<>(item, itemMap.values());
					choiceDialog.setTitle(("編輯任務達成條件：道具"));
					choiceDialog.setHeaderText("請選擇達成任務必須給予的道具：");
					Optional<Item> itemOp = choiceDialog.showAndWait();
					if(itemOp.isPresent())
					{
						currentTask.getItemProperty().set(itemOp.get().getUIDProperty().get());
						
						TextInputDialog textDialog = new TextInputDialog(currentTask.getItemQtyProperty().asString().get());
						textDialog.setTitle(("編輯任務達成條件：道具數量"));
						textDialog.setHeaderText("請給定達成任務必須給予的道具數量：");
						Optional<String> textOp = textDialog.showAndWait();
						if(textOp.isPresent() && textOp.get().matches("\\d+"))
							currentTask.getItemQtyProperty().set(Integer.valueOf(textOp.get()));
					}
				}
				break;
			case CIPHER:
				TextInputDialog textDialog = new TextInputDialog(currentTask.getCipherProperty().get());
				textDialog.setTitle(("編輯任務達成條件：密碼"));
				textDialog.setHeaderText("請給定達成任務必須輸入的密碼：");
				currentTask.getCipherProperty().set(textDialog.showAndWait().orElse(currentTask.getCipherProperty().get()));
				break;
		}
		conditionChoiceBox.setItems(FXCollections.emptyObservableList());
		conditionChoiceBox.setItems(
				FXCollections.observableArrayList(Condition.POSITION, Condition.ITEM, Condition.CIPHER));
		conditionChoiceBox.getSelectionModel().select(currentTask.getConditionProperty().get());
	}
	
	@FXML
	private void rewardOnMouseEnter()
	{
		rewardButton.setVisible(true);
	}
	
	@FXML
	private void rewardOnMouseExited()
	{
		rewardButton.setVisible(false);
	}
	
	private void refreshRewardLabel()
	{
		if(currentTask.getRewardProperty().isEmpty().get())
		{
			rewardLabel.setText("無獎勵");
		}
		else 
			rewardLabel.setText(currentTask.getRewardQtyProperty().get() + " 個 " +
					 itemMap.get(currentTask.getRewardProperty().get()).getNameProperty().get());
	}
	
	@FXML
	private void rewardOnMouseClicked()
	{
		if(!itemMap.isEmpty())
		{
			Item item = itemMap.get(currentTask.getRewardProperty().get());
			ChoiceDialog<Item> choiceDialog= new ChoiceDialog<>(item, itemMap.values());
			choiceDialog.setTitle(("編輯任務獎勵"));
			choiceDialog.setHeaderText("請選擇完成任務給予的獎勵：");
			Optional<Item> itemOp = choiceDialog.showAndWait();
			if(itemOp.isPresent())
			{
				currentTask.getRewardProperty().set(itemOp.get().getUIDProperty().get());
				
				TextInputDialog textDialog = new TextInputDialog(currentTask.getRewardQtyProperty().asString().get());
				textDialog.setTitle(("編輯任務獎勵數量"));
				textDialog.setHeaderText("請給定完成任務給予的獎勵數量，若要取消獎勵則輸入 0：");
				Optional<String> textOp = textDialog.showAndWait();
				if(textOp.isPresent() && textOp.get().matches("\\d+"))
					currentTask.getRewardQtyProperty().set(Integer.valueOf(textOp.get()));
				if(currentTask.getRewardQtyProperty().get() == 0)
				{
					currentTask.getRewardProperty().set(null);
					currentTask.getRewardQtyProperty().set(1);
				}
			}
			refreshRewardLabel();
		}
	}
	
	public void setItemMap(ObservableMap<String, Item> itemMap)
	{
		this.itemMap = itemMap;
	}
	
	public void setTaskMap(ObservableMap<String, Task> taskMap)
	{
		this.taskMap = taskMap;
	}
}
