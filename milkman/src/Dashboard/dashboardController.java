package Dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class dashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
   	AudioClip audio;
       void playSound(){
       	url=getClass().getResource("crash.wav");
   		audio=new AudioClip(url.toString());
   		audio.play();
       }

       @FXML
       void doSignout(ActionEvent event) 
       {
    	   try{
       		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("login/login.fxml")); 
   			Scene scene = new Scene(root);
   			Stage stage=new Stage();
   			stage.setScene(scene);
   			stage.show();
   			((Node)event.getSource()).getScene().getWindow().hide();
   		}
   		catch(Exception e)
   		{
   			e.printStackTrace();
   		}
       }

    @FXML
    void doBilling(ActionEvent event) 
    {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billing/billing.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doBillingHistory(ActionEvent event) 
    {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billinghistory/billing.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doCoustmerEntry(ActionEvent event) 
    {
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customerEntry/coustmer.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			//to hide the opened window
			/* 
		   	Scene scene1=(Scene)btnComboApp.getScene();
		    scene1.getWindow().hide();
		 */

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doCoustmerTableView(ActionEvent event) 
    {
    	playSound();
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("coustmertableview/table.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doPaymentLog(ActionEvent event) 
    {
    	playSound();
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("paymentlog/payment.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doVariationConsole(ActionEvent event) 
    {
    	playSound();
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("variationConsole/variation.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doVariationlog(ActionEvent event) 
    {
    	playSound();
    	try{
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("variationlog/variation.fxml")); 
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {}
}

