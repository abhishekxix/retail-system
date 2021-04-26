package com.superretail;

import com.superretail.models.ItemData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  
  @Override
  public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
    primaryStage.setTitle("Retail Management System");
    primaryStage.setScene(new Scene(root, 1280, 720));
    primaryStage.setResizable(false);
    
    primaryStage.show();
  }
  
  
  public static void main(String[] args) {
    launch(args);
  }
  
  @Override
  public void init() {
    ItemData.getInstance().load();
  }
  
  @Override
  public void stop() {
    ItemData.getInstance().save();
  }
}
