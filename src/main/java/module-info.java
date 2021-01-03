module com.gmail.andrewchouhs
{
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	requires transitive javafx.graphics;
	opens com.gmail.andrewchouhs.controller to javafx.fxml;
	exports com.gmail.andrewchouhs;
}