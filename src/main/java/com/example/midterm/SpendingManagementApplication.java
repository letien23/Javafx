package com.example.midterm;

import com.example.midterm.data.DBConnection;
import com.example.midterm.data.models.Purpose;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SpendingManagementApplication extends Application {
    Stage window;
    Scene scene1;
    final DBConnection con = new DBConnection();
    @Override
    public void start(Stage stage){

        window = stage;
        VBox layout1 = new VBox();
        addPurpose(layout1,null);
        showPurpose(layout1);
        scene1 = new Scene(layout1, 500, 500);
        window.setScene(scene1);
        window.show();
    }
    void showPurpose(VBox vBox){
        ArrayList<com.example.midterm.data.models.Purpose> purList = con.getPurpose();
        System.out.println("Size: "+ purList.size());
        for(int i = 0 ; i < purList.size(); i++){
            HBox hBoxPurpose = new HBox();
            hBoxPurpose.setSpacing(20);
            Label lbId = new Label(""+(i+1));
            Label lbName = new Label(""+purList.get(i).purpose);
            Label lbCost = new Label(""+purList.get(i).cost);
            Label lbDate = new Label(""+purList.get(i).date);
            int index = i;
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(actionEvent -> {
                con.deletePurpose(purList.get(index).id);
                try {
                    start(window);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            Button btnUpdate = new Button("Update");
            btnUpdate.setOnAction(actionEvent -> {
                addPurpose(vBox,purList.get(index));
            });
            hBoxPurpose.getChildren().addAll(lbId, lbName, lbCost,lbDate,btnUpdate,btnDelete);
            vBox.getChildren().add(hBoxPurpose);
        }
    }
    void addPurpose(VBox vBox, Purpose pur){
        if(pur != null){
            vBox.getChildren().clear();
        }
        Label lbPur = new Label("Purpose:");
        Label lbCos = new Label("Cost:");
        Label lbDa = new Label("Date:");
        TextField inputPur = new TextField(pur == null? "":pur.purpose);
        TextField inputCost = new TextField(pur == null? "": String.valueOf(pur.cost));
        TextField inputDate = new TextField(pur == null? "":pur.date);
        Button btnSave = new Button("Save");
        btnSave.setOnAction(event -> {
            if(pur == null){
                String Pur = inputPur.getText();
                float Cos = Float.parseFloat(inputCost.getText());
                String Da = inputDate.getText();
                con.insertPurpose(new Purpose(Pur,Cos,Da));
            }else{
                pur.purpose = inputPur.getText();
                pur.cost= Float.parseFloat(inputCost.getText());
               pur.date = inputDate.getText();
                con.updatePurpose(pur);
            }
            try {
                start(window);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        HBox fieldPur = new HBox();
        fieldPur.getChildren().addAll(lbPur,inputPur);
        HBox fieldCos = new HBox();
        fieldCos.getChildren().addAll(lbCos,inputCost);
        HBox fieldDa = new HBox();
        fieldDa.getChildren().addAll( lbDa, inputDate);
        vBox.getChildren().addAll(fieldPur,fieldCos,fieldDa,btnSave);
    }


    public static void main(String[] args) {
        launch();
    }
}