package login;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import customerEntry.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtID;

    @FXML
    private PasswordField txtpassword;
    
    Connection con;
    PreparedStatement pst;
    String Dname;
    String Dpassword;
    String name;
    String password;
    String Dcontact;

    @FXML
    void dologin(ActionEvent event) 
    {
    	password=txtpassword.getText();
    	try {
			pst = con.prepareStatement("select * from login");
	    	ResultSet table=pst.executeQuery();
			while(table.next())
			{
				Dcontact=table.getString("UContact");
				Dpassword=table.getString("Upassword");
			}
			name=txtID.getText();
			password=txtpassword.getText();
			if((name.equals(Dcontact)) && password.equals(Dpassword))
			{
				Parent root;
    			root = FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/dashboard.fxml"));
				Scene scene = new Scene(root);
				Stage stage=new Stage();
				stage.setScene(scene);
				stage.show();
				((Node)event.getSource()).getScene().getWindow().hide();
	    	}
			else { 
				Alert alert=new Alert(Alert.AlertType.NONE);   //Alert
      	    	alert.setContentText("Wrong ID or Password");
    	    	alert.showAndWait();
			}
    	}catch(Exception e) {}
    }
    @FXML
    void dosignup(ActionEvent event) 
    {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("signUp/signup.fxml"));
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			((Node)event.getSource()).getScene().getWindow().hide();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void initialize() 
    {
    	con=DBConnection.doConnect();
    }
}
