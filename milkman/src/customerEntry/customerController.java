package customerEntry;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class customerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgShow;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtCowqty;

    @FXML
    private TextField txtBuffaloqty;

    @FXML
    private TextField txtCowPrice;

    @FXML
    private TextField txtBuffaloPrice;

    @FXML
    private DatePicker txtDate;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private Label lblResult;
    
    Connection con;
    PreparedStatement pst;
    File selectedFile;
    String imageFile;

    @FXML
    void doBrowse(ActionEvent event) 
    {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(
    	        new FileChooser.ExtensionFilter("Image Files",
    	                "*.bmp", "*.png", "*.jpg", "*.gif")); // limit fileChooser options to image files
    	selectedFile = fileChooser.showOpenDialog(null);

    	if (selectedFile != null) {
    		try {
    		    lblResult.setText(selectedFile.getPath());
    			imageFile = selectedFile.toURI().toURL().toString();
    	        Image image = new Image(imageFile);
    	        imgShow.setImage(image);
    	    } catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
//    	else {
//    		Image image = new Image("user1.png");
//	        imgShow.setImage(image);
//    		//lblResult.setText(selectedFile.getPath());
//    	}
    }

    @FXML
    void doCoustmerLeft(ActionEvent event) 
    {
    	String name=comboName.getSelectionModel().getSelectedItem();
    	try {
			pst=con.prepareStatement("delete from customerEntry where cname=?");
	    	pst.setString(1,name);
	    	int count=pst.executeUpdate();
			lblResult.setText(count+" Records Deleted");
    	} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void doFetch(ActionEvent event) 
    {
    	try {
    		imgShow.setImage(null);
    		String fetchimageFile="";
        	String name=comboName.getSelectionModel().getSelectedItem();
        	PreparedStatement pst=con.prepareStatement("select * from customerEntry where cname=?");
        	pst.setString(1,name);
        	ResultSet table=pst.executeQuery();
    			while(table.next())
    			{
    				String number=table.getString("cnumber");
    				String address=table.getString("caddress");
    				float cowqty=table.getFloat("cowqty");
    				float cowprice=table.getFloat("cowprice");
    				float buffaloqty=table.getFloat("buffaloqty");
    				float buffaloprice=table.getFloat("buffaloprice");
    				java.sql.Date entrydate=table.getDate("entrydate");
    				String picpath=table.getString("cpic");        ////imageFile
    				try {
    					if(picpath.equals("nopic")){
    						imgShow.setImage(null);
    					}else {
    						fetchimageFile=new File(picpath).toURI().toURL().toString();
        					fetchimageFile=new File(picpath).toString();
        					Image fetchimage=new Image(fetchimageFile);
        					imgShow.setImage(fetchimage);
    					}
    					
    				} catch (Exception e) {
    				
    					e.printStackTrace();
    				}
    				txtNumber.setText(number);
    				txtAddress.setText(address);
    				txtCowqty.setText(cowqty+"");
    				txtCowPrice.setText(cowprice+"");
    				txtBuffaloqty.setText(buffaloqty+"");
    				txtBuffaloPrice.setText(buffaloprice+"");
    				txtDate.getEditor().setText(entrydate.toString());
    				
    			}
    	    } catch (Exception e) {
    			e.printStackTrace();
    	    }
    }
    @FXML
    void doNew(ActionEvent event) 
    {
    	comboName.getEditor().setText("");
    	txtNumber.setText("");
    	txtAddress.setText("");
    	txtCowqty.setText("");
    	txtCowPrice.setText("");
    	txtBuffaloqty.setText("");
    	txtBuffaloPrice.setText("");
    	txtDate.getEditor().setText("");
    	imgShow.setImage(null);
    	lblResult.setText("clear..");
    }

    @FXML
    void doSave(ActionEvent event) 
    {
    	try {
	    	float cowqty=0;
	    	float cowPrice = 0;
	    	float buffaloqty=0;
	    	float buffaloPrice=0;
	    	String name=comboName.getSelectionModel().getSelectedItem();
	    	cowqty=Float.parseFloat(txtCowqty.getText());
	    	cowPrice=Float.parseFloat(txtCowPrice.getText());
	    	buffaloqty=Float.parseFloat(txtBuffaloqty.getText());
	    	buffaloPrice=Float.parseFloat(txtBuffaloPrice.getText());
	    	LocalDate ldate=txtDate.getValue();            //ldate=local date
			java.sql.Date swdate= java.sql.Date.valueOf(ldate);  //sql date
	    	
				pst = con.prepareStatement("insert into customerEntry values(?,?,?,?,?,?,?,?,?)");
				pst.setString(1,name);
				pst.setString(2,txtNumber.getText());
				pst.setString(3,txtAddress.getText());
				pst.setFloat(4,cowqty);
				pst.setFloat(5,cowPrice);
				pst.setFloat(6,buffaloqty);
				pst.setFloat(7,buffaloPrice);
				pst.setDate(8,swdate);
				System.out.println(selectedFile.getPath());
				System.out.println("jsdhls");
				if(imgShow.equals(null)) {
					pst.setString(9,"nopic");
				}
				else {
					pst.setString(9,imageFile);
				}
				
				pst.executeUpdate();//fire query in table
				lblResult.setText("Saved..");
			
		} catch (Exception e) {
			e.printStackTrace();
			lblResult.setText("failed..");
		}
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {
    	float cowqty=0;
    	float cowPrice = 0;
    	float buffaloqty=0;
    	float buffaloPrice=0;
    	java.sql.Date swdate=null;
		String stwdate="";
    	String name=comboName.getSelectionModel().getSelectedItem();
    	cowqty=Float.parseFloat(txtCowqty.getText());
    	cowPrice=Float.parseFloat(txtCowPrice.getText());
    	buffaloqty=Float.parseFloat(txtBuffaloqty.getText());
    	buffaloPrice=Float.parseFloat(txtBuffaloPrice.getText());
    	LocalDate lwdate=txtDate.getValue();
		if(lwdate==null)
			{
				stwdate=txtDate.getEditor().getText();
				lwdate=LocalDate.parse(stwdate);
			}
			swdate= java.sql.Date.valueOf(lwdate);
		try {
			pst = con.prepareStatement("update customerEntry set cnumber=?,caddress=?,cowqty=?,cowprice=?,buffaloqty=?,buffaloprice=?,entrydate=?,cpic=? where cname=?");
	    	pst.setString(9,name);
			pst.setString(1,txtNumber.getText());
			pst.setString(2,txtAddress.getText());
			pst.setFloat(3,cowqty);
			pst.setFloat(4,cowPrice);
			pst.setFloat(5,buffaloqty);
			pst.setFloat(6,buffaloPrice);
			pst.setDate(7,swdate);
			if(imgShow.equals(null)) {
				pst.setString(8,"nopic");
			}
			else {
				pst.setString(8,imageFile);
			}
			
			pst.executeUpdate();//fire query in table
			lblResult.setText("Update..");
			} catch (Exception e) {
				e.printStackTrace();
			}
    }
   
    void fillCombo()
    {
    	ArrayList<String> nameArray=new ArrayList<>();
    	try {
			pst=con.prepareStatement("select distinct cname from customerEntry");
			ResultSet table=pst.executeQuery();
		
		while(table.next())
		{
			String name=table.getString("cname");
			nameArray.add(name+"");
		}
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    	comboName.getItems().addAll(nameArray);
    }


    @FXML
    void doback(ActionEvent event) 
    {
    	Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("Dashboard/dashboard.fxml"));
			Scene scene = new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
			((Node)event.getSource()).getScene().getWindow().hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() 
    {
    	con=DBConnection.doConnect();
    	fillCombo();
    }
}
