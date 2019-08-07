package signup;

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

public class signupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCity;

    @FXML
    private PasswordField txtPassword;
    
    Connection con;
    PreparedStatement pst;
    String tableContact;

    @FXML
    void doSingup(ActionEvent event) 
    {
    	{
        	try{
        		pst=con.prepareStatement("select * from login");
            	ResultSet table=pst.executeQuery();
        			while(table.next())
        			{
        				tableContact=table.getString("UContact");
        				System.out.println("thik hai");
        			}
        			String contact=txtContact.getText();
        			if(contact.equals("tableContact")) 
        			{
        				System.out.println("yeh chal ra!!");
        				Alert alert=new Alert(Alert.AlertType.NONE);   //Alert
              	    	alert.setContentText("Contact already Exist");
            	    	alert.showAndWait();
        			}
        			else {
        				pst = con.prepareStatement("insert into login values(?,?,?,?)");
            			pst.setString(1,txtName.getText());
            	    	pst.setString(2,txtContact.getText());
            	    	pst.setString(3,txtCity.getText());
            	    	pst.setString(4,txtPassword.getText());
            	    	pst.executeUpdate();
               	    	
//            	    	Alert alert=new Alert(Alert.AlertType.NONE);   //Alert
//              	    alert.setContentText("saved");
//            	    	alert.showAndWait();
            	    	
            	    	Parent root;
            			root = FXMLLoader.load(getClass().getClassLoader().getResource("login/login.fxml"));
        				Scene scene = new Scene(root);
        				Stage stage=new Stage();
        				stage.setScene(scene);
        				stage.show();
        				((Node)event.getSource()).getScene().getWindow().hide();
        			}
        	} catch (Exception e) {
    			e.printStackTrace();
    		}	
        }
    }
    @FXML
    void initialize() 
    {
    	con=DBConnection.doConnect();
    }
}
