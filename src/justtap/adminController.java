/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justtap;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author It'sOpen
 */
public class adminController implements Initializable
{
    ObservableList items = FXCollections.observableArrayList();
    
    @FXML
    private ListView<String> adminlist;    
     
    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));        
                Scene scene = new Scene(root);
                Main.stage2.setResizable(false);
                Main.stage2.setScene(scene);
    }
    
    @FXML
    void updateDatabase(ActionEvent event) throws ClassNotFoundException, SQLException 
    {
        String selected = adminlist.getSelectionModel().getSelectedItem();
        System.out.println(selected);
        String queryThree = "Delete from users where username = '"+selected+"';";

        //Connect to Database
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/justtap?autoReconnect=true&useSSL=false"
                , "root", "ishmael");
        PreparedStatement statement = connection.prepareStatement(queryThree);                

        statement.execute();
        
        adminlist.getItems().removeAll(items);
        
        items.removeAll(items);
        try(Statement st1 = DbConnect.dbConnect().createStatement())
        {
            try(ResultSet rst = st1.executeQuery("select username, password from users;"))
            {
                while(rst.next())
                {
                   items.add(rst.getString(1));
                }

            }
            catch(SQLException ex){ex.printStackTrace();}
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        adminlist.getItems().addAll(items);
                
                
    }
    
//    @FXML
//    void removeclick(MouseEvent event) throws ClassNotFoundException, SQLException 
//    {           
//                //adminlist.getItems().addAll(items);
//                //displaying();
//    }

  
    
    private void displaying()
    {
        items.removeAll(items);
        try(Statement st1 = DbConnect.dbConnect().createStatement())
        {
            try(ResultSet rst = st1.executeQuery("select username from users;"))
            {
                while(rst.next())
                {
                   items.add(rst.getString(1));
                }

            }
            catch(SQLException ex){ex.printStackTrace();}
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        adminlist.getItems().addAll(items);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        displaying();
    }
}
