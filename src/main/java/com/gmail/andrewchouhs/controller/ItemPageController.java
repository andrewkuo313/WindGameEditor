package com.gmail.andrewchouhs.controller;

import com.gmail.andrewchouhs.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ItemPageController
{
	@FXML
	private Label nameLabel;
	@FXML
	private Label initialQtyLabel;
	@FXML
	private Button nameButton;
	@FXML
	private Button initialQtyButton;
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField initialQtyTextField;
	@FXML
	private TableView<Item> itemTableView;
	@FXML
	private TableColumn<Item, String> nameColumn;
	@FXML
	private TableColumn<Item, String> initialQtyColumn;
	
	private ObservableMap<String, Item> itemMap;
	private final ObservableList<Item> itemList = FXCollections.observableArrayList();
	private Item currentItem;
	
	public void init()
	{
		//https://stackoverflow.com/questions/38487797/javafx-populate-tableview-with-an-observablemap-that-has-a-custom-class-for-its
		itemMap.addListener((MapChangeListener.Change<? extends String, ? extends Item> change)->
		{
			boolean removed = change.wasRemoved();
		    if (removed != change.wasAdded())
		    {
		        if (removed)
		            // no put for existing key
		            // remove pair completely
		        	itemList.remove(change.getValueRemoved());
		        else
		            // add new entry
		        	itemList.add(change.getValueAdded());
		    }
		    else
		    {
		        // replace existing entry
		    }
		});
		
		itemTableView.getSelectionModel().selectedItemProperty().addListener(
	            (observable, oldValue, newValue) -> 
	            {	
	            	defocusAllComponent();
	            	currentItem = newValue;
	            	if(newValue == null)
	            	{
	            		nameLabel.setText("");
		            	initialQtyLabel.setText("");
	            	}
	            	else
	            	{
	            		nameLabel.setText(newValue.getNameProperty().get());
	            		initialQtyLabel.setText(newValue.getInitialQtyProperty().asString().get());
	            	}
	            });

		
		nameTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		initialQtyTextField.focusedProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
				defocusAllComponent();
		});
		nameTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
			{
				currentItem.getNameProperty().set(nameTextField.getText());
				nameLabel.setText(nameTextField.getText());
			}
		});
		initialQtyTextField.visibleProperty().addListener((ob, oldValue, newValue)->
		{
			if(oldValue && !newValue)
			{
				currentItem.getInitialQtyProperty().set(Integer.valueOf(initialQtyTextField.getText()));
				initialQtyLabel.setText(initialQtyTextField.getText());
			}
		});
		
		
		itemTableView.setItems(itemList);
		nameColumn.setCellValueFactory(data -> data.getValue().getNameProperty());
		initialQtyColumn.setCellValueFactory(data -> data.getValue().getInitialQtyProperty().asString());

	}
	
	@FXML
	private void createItem()
	{
		Item item = new Item();
		itemMap.put(item.getUIDProperty().get(), item);
	}
	
	@FXML
	private void deleteItem()
	{
		itemMap.remove(currentItem.getUIDProperty().get());
	}
	
	@FXML
	private void defocusAllComponent()
	{
		nameLabel.setVisible(true);
		nameTextField.setVisible(false);
		nameTextField.setPrefWidth(0);
		initialQtyLabel.setVisible(true);
		initialQtyTextField.setVisible(false);
		initialQtyTextField.setPrefWidth(0);
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
		nameTextField.setText(currentItem.getNameProperty().get());
		
	}
	
	@FXML
	private void initialQtyOnMouseEnter()
	{
		initialQtyButton.setVisible(true);
	}
	
	@FXML
	private void initialQtyOnMouseExited()
	{
		initialQtyButton.setVisible(false);
	}
	
	@FXML
	private void initialQtyOnMouseClicked()
	{
		initialQtyLabel.setVisible(false);
		initialQtyButton.setVisible(false);
		initialQtyTextField.setVisible(true);
		initialQtyTextField.setPrefWidth(TextField.USE_COMPUTED_SIZE);
		initialQtyTextField.requestFocus();
		initialQtyTextField.setText(currentItem.getInitialQtyProperty().asString().get());
	}
	
	
	public void setItemMap(ObservableMap<String, Item> itemMap)
	{
		this.itemMap = itemMap;
	}
}
