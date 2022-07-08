package com.example.midterm;

import com.example.midterm.data.DBConnection;
import com.example.midterm.data.models.Purpose;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SpendingManagementApplication extends Application {
    Stage window;
    Scene admin,login;
    VBox layoutAdmin, layoutLogin;
    final DBConnection con = new DBConnection();
    @Override
    public void start(Stage stage){

        window = stage;
        layoutAdmin = new VBox();
        layoutLogin = new VBox();
        admin = new Scene(layoutAdmin, 800, 800);
        login = new Scene(layoutLogin, 300,  300);
        showLogin();
        window.setScene(login);
        window.show();
    }

    // Hàm dùng hiển thị list các danh mục
    void showPurpose(){
        ArrayList<com.example.midterm.data.models.Purpose> purList = con.getPurpose();
        Button btnDell = new Button("Delete all");
        btnDell.setOnAction(event -> {
            con.deleteAll();
            layoutAdmin.getChildren().clear();
            addPurpose(null);
            showPurpose();
            window.setScene(admin);
            window.show();
        });
        HBox hbBtnDell = new HBox();
        hbBtnDell.getChildren().addAll(btnDell);
        layoutAdmin.getChildren().add(hbBtnDell);
        System.out.println("Size: "+ purList.size());
        Label idTitle = new Label("Stt     ");
        Label nameTitle = new Label("Mục đích           ");
        Label costTitle = new Label("Chi tiêu        ");
        Label dateTitle = new Label("Ngày ");
        HBox hbTitle = new HBox();
        hbTitle.getChildren().addAll(idTitle,nameTitle,costTitle,dateTitle);
        layoutAdmin.getChildren().add(hbTitle);

        float sum = 0;
        for(int i = 0 ; i < purList.size(); i++){
            HBox hBoxPurpose = new HBox();
            hBoxPurpose.setSpacing(20);
            Label lbId = new Label(""+(i+1));
            Label lbName = new Label(""+purList.get(i).purpose);
            Label lbCost = new Label(""+purList.get(i).cost);
            Label lbDate = new Label(""+purList.get(i).date);
            sum = sum + purList.get(i).cost;
            int index = i;
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(actionEvent -> {
                con.deletePurpose(purList.get(index).id);
                layoutAdmin.getChildren().clear();
                addPurpose(null);
                showPurpose();
                window.setScene(admin);
                window.show();
            });
            Button btnUpdate = new Button("Update");
            btnUpdate.setOnAction(actionEvent -> {
                addPurpose(purList.get(index));
            });

            hBoxPurpose.getChildren().addAll(lbId, lbName, lbCost,lbDate,btnUpdate,btnDelete);
            layoutAdmin.getChildren().add(hBoxPurpose);
        }
        Label lbSum = new Label("Tổng tiền đã dùng là: "+sum);
        HBox hbSum = new HBox();
        hbSum.getChildren().addAll(lbSum);
        layoutAdmin.getChildren().add(hbSum);

    }

    //Hàm dùng để hiển thị form thêm và sửa danh mục
    void addPurpose( Purpose pur){
        if(pur != null){
            layoutAdmin.getChildren().clear();
        }
        Button btnLogout = new Button("Log out");
        btnLogout.setOnAction(event -> {
            showLogin();
            window.setScene(login);
            window.show();
        });
        Label lbPur = new Label("Purpose:");
        Label lbCos = new Label("Cost:      ");
        Label lbDa = new Label("Date:      ");
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
            layoutAdmin.getChildren().clear();
            addPurpose(null);
            showPurpose();
            window.setScene(admin);
            window.show();

        });
        HBox fieldPur = new HBox();
        fieldPur.getChildren().addAll(lbPur,inputPur);
        HBox fieldCos = new HBox();
        fieldCos.getChildren().addAll(lbCos,inputCost);
        HBox fieldDa = new HBox();
        fieldDa.getChildren().addAll( lbDa, inputDate);
        layoutAdmin.getChildren().addAll(btnLogout,fieldPur,fieldCos,fieldDa,btnSave);
    }

    //Hàm hiển thị phần login
    void  showLogin(){
        layoutLogin.getChildren().clear();
        Label labelLogin =new Label("LOGIN");
        Label lbName = new Label("Name: ");
        Label lbPass = new Label("Password: ");
        TextField name = new TextField();
        PasswordField pass = new PasswordField();
        HBox fieldName = new HBox();
        fieldName.getChildren().addAll(lbName,name);
        fieldName.setSpacing(10);
        fieldName.setAlignment(Pos.BASELINE_CENTER);
        HBox fieldPass = new HBox();
        fieldPass.getChildren().addAll(lbPass,pass);
        fieldPass.setSpacing(10);
        fieldPass.setAlignment(Pos.BASELINE_CENTER);

        Button btnLogin = new Button("LOGIN");
        btnLogin.setOnAction(actionEvent -> {
            String na = name.getText();
            String pa = pass.getText();
            if( na.equals("tien") && pa.equals("123")){
                layoutAdmin.getChildren().clear();
                addPurpose(null);
                showPurpose();
                window.setScene(admin);
                window.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                name.setText("");
                pass.setText("");
                alert.setTitle("ERROR");
                alert.setContentText("Name or Password was wrong");
                alert.show();
            }
        });
        HBox btnLoginPage = new HBox();
        btnLoginPage.getChildren().addAll(btnLogin);
        btnLoginPage.setSpacing(10);
        btnLoginPage.setAlignment(Pos.BASELINE_CENTER);
        layoutLogin.getChildren().addAll(labelLogin,fieldName,fieldPass,btnLoginPage);
        layoutLogin.setSpacing(15);
        layoutLogin.setAlignment(Pos.BASELINE_CENTER);
    }
    public static void main(String[] args) {
        launch();
    }
}