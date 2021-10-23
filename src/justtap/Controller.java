/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justtap;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author It'sOpen
 */
public class Controller implements Initializable 
{
    //************Main************************************************
     @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> userComboBox;

    @FXML
    void SignUp(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));        
        Scene scene = new Scene(root);
        Main.stage2.setResizable(false);
        Main.stage2.setScene(scene);
    }

    
     @FXML
    void userCombo(MouseEvent event) 
    {
        ObservableList<String> users = FXCollections.observableArrayList("Admin", "Customer", "Retailer");
        userComboBox.setItems(users);
        userComboBox.setValue(userComboBox.getItems().get(0));

    }

    
    @FXML
    void signin(ActionEvent event) throws SQLException, ClassNotFoundException, IOException 
    {
        String loginuser = new String(usernameTextField.getText());
        String loginpassword = new String(passwordField.getText());
        
        if(loginuser.equals("admin") && loginpassword.equals("admin"))
        {
            System.out.println("Password is correct for Username");            
            try
            {                          
                Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));        
                Scene scene = new Scene(root);
                Main.stage2.setResizable(false);
                Main.stage2.setScene(scene);
            }
            catch(Exception ee)
            {
                System.out.println(ee.getMessage());
            }
            
                       
        }
        //**************************************Retailer logins**********************************************************
        
         else if(loginuser.equals("cus") && loginpassword.equals("cus"))
        {
            System.out.println("Password is correct for Username");            
            try
            {                          
                Parent root = FXMLLoader.load(getClass().getResource("Buy.fxml"));        
                Scene scene = new Scene(root);
                Main.stage2.setResizable(false);
                Main.stage2.setScene(scene);
            }
            catch(Exception ee)
            {
                System.out.println(ee.getMessage());
            }
            
                       
        }
        
        //************************************************************************************************
      
        
        else
        {
            try(Statement st = DbConnect.dbConnect().createStatement())
            {
                try(ResultSet rst = st.executeQuery("select username,password from users where username = '"+loginuser+"';"))
                {
                    if(rst.next())
                    {
                        if(loginpassword.equals(rst.getString("password")))
                        {
                            System.out.println("Password is correct for Username");
                            Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));        
                            Scene scene = new Scene(root);
                            Main.stage2.setResizable(false);
                            Main.stage2.setScene(scene);
                            
                        }
                        else
                        {
                           
                            System.out.println("Password incorrect for Username");
                            return;
                        }
                        return;
                    }
                        
                }
                catch(SQLException ex){ex.printStackTrace();}
            }

        }
    }

   
    
    //***************************************Sign up***********************************************
    
    @FXML
    private TextField fnameTextField;

    @FXML
    private TextField lnameTextField;

    @FXML
    private TextField signupusernameTextField;

    @FXML
    private PasswordField signuppasswordField;

    @FXML
    private ComboBox<String> userCombobox;

    @FXML
    void signAs(MouseEvent event) 
    {
        ObservableList<String> users = FXCollections.observableArrayList( "Customer", "Retailer");
        userCombobox.setItems(users);
        userCombobox.setValue(userCombobox.getItems().get(0));

    }

   @FXML
    void signUpProceed(ActionEvent event)  throws SQLException, ClassNotFoundException 
    {    
        
        
        String queryThree = "insert into users(name,surname,username,password) values('"+fnameTextField.getText()+"','"+lnameTextField.getText()+"','"+signupusernameTextField.getText()+"','"+signuppasswordField.getText()+"');";
                
		//Connect to Database
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/justtap?autoReconnect=true&useSSL=false"
                        , "root", "ishmael");
                PreparedStatement statement = connection.prepareStatement(queryThree);                
              
                statement.execute();
        
        
        
        System.out.println("Im in");
       
    }
    //**********************************admin******************************************
        @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    void backToMenu(ActionEvent event) {
        
    }

    @FXML
    void okButton(MouseEvent event) {

    }
     
   

    //******************************View**************************************************
    @FXML
    private BorderPane GoodTextField;

    @FXML
    private TextField FindGoods;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField loadingField;

    @FXML
    private TextArea displaypane;

    @FXML
    void previous(ActionEvent event) {

    }

    @FXML
    void proceed(ActionEvent event) {

    }

    @FXML
    void searchingButton(ActionEvent event) {

    }
    
    //********************Retailer update database***********************************************
    
      @FXML
    private TextField proName;

    @FXML
    private TextField proSize;

    @FXML
    private TextField price;

    @FXML
    void updateProductsTable(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
        // retailer update the products on database 
        String queryThree = "insert into products(pname,psize,price) values('"+proName.getText()+"','"+proSize.getText()+"','"+price.getText()+"');";
                
		//Connect to Database
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/justtap?autoReconnect=true&useSSL=false"
                        , "root", "ishmael");
                PreparedStatement statement = connection.prepareStatement(queryThree);                
              
                statement.execute();
    }
     @FXML
    void doneCommit (ActionEvent event) throws ClassNotFoundException, SQLException 
    {
        String queryThree = "commit";
        
        Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/justtap?autoReconnect=true&useSSL=false"
                        , "root", "ishmael");
                PreparedStatement statement = connection.prepareStatement(queryThree);                
              
                statement.execute();
    }

    @FXML
    void logoff(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));        
                            Scene scene = new Scene(root);
                            Main.stage2.setResizable(false);
                            Main.stage2.setScene(scene);
    }
//********************************************************************************
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
      
    }
}    
    
     
    
