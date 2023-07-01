package com.example.demo;

import Client.Client;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    public AnchorPane parent;
    @FXML
    private TextField searchChoice;
    @FXML
    private Button searchButton;
    @FXML
    private Label error;
    @FXML
    private Pane searchBox;
    @FXML
    private Label name;
    @FXML
    private Label username;
    @FXML
    private VBox searchOptions = new VBox();
    private User user;
    public void search(ActionEvent event) {
        try {
            String word = searchChoice.getText();
            Client.out.writeObject("4");
            Client.out.writeObject(word);
            ArrayList<User> foundUsers = (ArrayList<User>) Client.in.readObject();
            if (foundUsers.size() == 0) {
                error.setText("Not Found!");
            }
            showSearchOptions(foundUsers);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void handleSearch() {
        searchButton.setOnAction(this::search);
    }
    public Pane showSearch(User serverUser){
        user = serverUser;
        name.setText(user.getFirstName());
        searchBox.getChildren().add(name);
        username.setText(user.getUsername());
        searchBox.getChildren().add(username);
        return searchBox;
    }

    public void showSearchOptions(ArrayList<User> serverUser){
        for (User user : serverUser){
            try {
                Node node = FXMLLoader.load(Client.class.getResource("BriefProfile.fxml"));
                showSearch(user);
                searchOptions.getChildren().add(node);
            } catch (Exception ignore){
                System.out.println("toye show search error dari");
            }
        }
    }

    public void switchToSearchOptions(MouseEvent event) throws IOException, ClassNotFoundException {
        ImageView imageView = (ImageView) event.getSource();
        FXMLLoader loader = new FXMLLoader(Client.class.getResource("SearchOptions.fxml"));
        Parent root = null;
        try {
            root=loader.load();
        }catch (IOException e){
            System.out.println("KOMAK!");
        }
        Stage stage = (Stage) imageView.getScene().getWindow();
        Scene scene = null;
        if (root != null) {
            scene = new Scene(root);
        }
        stage.setScene(scene);
        stage.show();
    }

    public void backToMainPage(MouseEvent event) throws IOException, ClassNotFoundException {
        Client.out.writeObject("0");
        Parent root = FXMLLoader.load(Objects.requireNonNull(Client.class.getResource("MainPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        try {
            ((MainPageController) root.getUserData()).showTimeline(Client.timelineReceiver());
        } catch (NullPointerException e) {
            System.out.println("koomak !");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    public void backToMainPage(MouseEvent event) throws IOException, ClassNotFoundException {
        Client.out.writeObject("0");
        Parent root = FXMLLoader.load(Objects.requireNonNull(Client.class.getResource("MainPage.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        try {
            ((MainPageController) root.getUserData()).showTimeline(Client.timelineReceiver());
        } catch (NullPointerException e) {
            System.out.println("koomak !");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        parent.setUserData(this);
    }
}
