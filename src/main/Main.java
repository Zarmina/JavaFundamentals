package main;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.model.User;
import main.service.LoginService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application
{


    private Label lblUserName;
    private TextField userNameTxt;
    private Label lblpassword;
    private PasswordField passwordField;
    private Button loginbtn;
    private Label lblVisiblePass;
    private Label lblLogin;

    private LoginService loginService = new LoginService();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception
    {
        // creating the login scene
        makeScene(window);

        // putting the textProp of the pass field in a variable to make it more readable
        StringProperty passwordFieldProperty = passwordField.textProperty();

        // adding the listener to the passfield
        passwordFieldProperty.addListener((observableValue, oldValue, newValue) ->
                                          {
                                              if (validPass(newValue))
                                              {
                                                  loginbtn.setVisible(true);
                                              } else loginbtn.setVisible(false);
                                          });

        // binding the value of the passfield's text to the label's text: this label will listen to the pass field and change with every change made there
        lblVisiblePass.textProperty()
                .bind(passwordFieldProperty);

        // the actual code of the loging button
        loginbtn.setOnAction(actionEvent ->
                             {
                                 User loggedInUser = loginService.login(userNameTxt.getText(), passwordField.getText());
                                 if (loggedInUser == null)
                                 {

                                     lblLogin.setText("whoops wrong creds");
                                 } else // if the user is not null it means the username and password was right so we will direct them to a new window (actually same window new scene :D )
                                 {
                                     Label lblWelcome = new Label("Welcome " + loggedInUser.getFullName());

                                     Label loginSuccess = new Label("You have successfully logged in! now go and be happy!");

                                     VBox vBox = new VBox();
                                     vBox.setSpacing(30);
                                     vBox.setPadding(new Insets(252));
                                     vBox.getChildren()
                                             .addAll(lblWelcome, loginSuccess);


                                     Scene dashboard = new Scene(vBox);
                                     window.setScene(dashboard);

                                 }
                             });

        // just to get the focus on the passfield once you enter on the username text field
        userNameTxt.setOnKeyPressed(evt ->
                                    {

                                        if (evt.getCode()
                                                .equals(KeyCode.ENTER))
                                        {

                                            passwordField.requestFocus();
                                        }
                                    });

        // to login once the enter is pressed on passfield
        passwordField.setOnKeyPressed(evt ->
                                      {

                                          if (evt.getCode()
                                                  .equals(KeyCode.ENTER))
                                          {

                                              loginbtn.fire();
                                          }
                                      });


        window.show();
    }

    private boolean validPass(String pass)
    {
        // I hate regex :)
        // regex makes life harder :)
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9.+]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pass);
        boolean specialChar = matcher.find();
                // length           // special char     // digit
        return pass.length() >= 8 && specialChar && pass.matches(".*\\d.*");
    }

    private void makeScene(Stage window)
    {
        lblVisiblePass = new Label();
        lblLogin = new Label();

        lblUserName = new Label("Username");
        lblUserName.setStyle("-fx-font-family: \"Arial Nova\";\n" + "-fx-font-weight: bold;\n" + "-fx-font-size: 16;");

        userNameTxt = new TextField();
        userNameTxt.setPromptText("username");

        lblpassword = new Label("Password");
        lblpassword.setStyle("-fx-font-family: \"Arial Nova\";\n" + "-fx-font-weight: bold;\n" + "-fx-font-size: 16;");

        passwordField = new PasswordField();
        passwordField.setPromptText("password");

        loginbtn = new Button("Login");
        loginbtn.setPadding(new Insets(10));
        loginbtn.setVisible(false);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(20);

        vBox.getChildren()
                .addAll(lblUserName, userNameTxt, lblpassword, passwordField, loginbtn, lblVisiblePass, lblLogin);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
    }
}
