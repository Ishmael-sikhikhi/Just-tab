/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justtap;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Ricardo
 */
public class ProductView implements Initializable
{
    double parsedItem = 0;
    double sum = 0;
    String parsedItemName = "";
    ObservableList items = FXCollections.observableArrayList();
    
    @FXML
    private ListView<String> productlist;
    
     @FXML
    private TextArea showitemsdisplay;
    
    @FXML
    private TextField loadingField;
    
      @FXML
    void addItem(ActionEvent event) 
    {
        loadingField.setText("");
        showitemsdisplay.appendText(""+parsedItemName+"\n");
        if(parsedItem == 0){
            sum += parsedItem;
            loadingField.appendText(""+sum);
        }
        else{
            sum += parsedItem;
            loadingField.appendText("Total amount is : "+ sum);
        }
        
    }

    @FXML
    void clearButton(ActionEvent event) 
    {
        loadingField.setText("");
        showitemsdisplay.setText("");
        sum = 0;
        //parsedItem = 0;
    }
    
    private void productload()
    {
        items.removeAll(items);
        try(Statement st1 = DbConnect.dbConnect().createStatement())
        {
            try(ResultSet rst = st1.executeQuery("select * from products;"))
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
        
        productlist.getItems().addAll(items);
        
    }
    
    @FXML
    private TextArea selectedproductdisplay;
      
    @FXML
    void display(MouseEvent event) throws ClassNotFoundException, SQLException 
    {
        selectedproductdisplay.setText(null);
        String selected = productlist.getSelectionModel().getSelectedItem();
        
        try(Statement st1 = DbConnect.dbConnect().createStatement())
        {
            try(ResultSet rst = st1.executeQuery("select * from products where pname = '"+selected+"';"))
            {
                while(rst.next())
                {
                   selectedproductdisplay.appendText("product name :"+ rst.getString(1)+"\n");
                   selectedproductdisplay.appendText("price :"+ rst.getString(2)+"\n");
                   selectedproductdisplay.appendText("product size :"+ rst.getDouble(3)+"\n");
                   
                   parsedItemName = rst.getString(1);
                   parsedItem = rst.getDouble(3);
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
                
    }
    
    @FXML
    void previous(ActionEvent event) throws IOException 
    {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));        
        Scene scene = new Scene(root);
        Main.stage2.setResizable(false);
        Main.stage2.setScene(scene);
    }

    @FXML
    void proceed(ActionEvent event) {
        
          
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("It was a pleasur to work with you!");
             alert.setContentText("Thank for using Just Tap shopping App!!!");
             alert.showAndWait();
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       productload();
    }
}
