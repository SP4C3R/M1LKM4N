package variationConsole;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customerEntry.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class variationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> lstcoustmer;

    @FXML
    private TextField txtCowQty;

    @FXML
    private TextField txtBuffaloQty;

    @FXML
    private CheckBox checkAbsent;

    @FXML
    private DatePicker variationDate;

    @FXML
    private Label lblResult;

    @FXML
    private Label lblcow;

    @FXML
    private Label lblbuffalo;
    Connection con;
    float per=0;
    float per1=0;
    float cowqty=0;
    float buffaloqty=0;
    
    @FXML
    void doDeleteOther(ActionEvent event) {
    	lstcoustmer.getItems().retainAll(lstcoustmer.getSelectionModel().getSelectedItems());
    }

    @FXML
    void doSave(ActionEvent event) 
    {
    	try {
    		String lst=lstcoustmer.getSelectionModel().getSelectedItem();
    		LocalDate ldate=variationDate.getValue();            //ldate=local date
			java.sql.Date swdate= java.sql.Date.valueOf(ldate);  //sql date
			PreparedStatement pst = con.prepareStatement("insert into variation values(?,?,?,?)");
			pst.setString(1,lst);
			pst.setDate(2,swdate);
			if(checkAbsent.isSelected()) 
			{
				//txtCowQty.setText(per*(-1));
				//txtBuffaloQty.setText(per1*(-1));
				//txtCowQty.setDisable(true);
				//txtBuffaloQty.setDisable(true);
				pst.setFloat(3,per*(-1));
				pst.setFloat(4,per1*(-1));
				lblResult.setText("saved..");
			}
			else {
				cowqty=Float.parseFloat(txtCowQty.getText());
	    		buffaloqty=Float.parseFloat(txtBuffaloQty.getText());
				pst.setFloat(3,cowqty);
				pst.setFloat(4,buffaloqty);
				lblResult.setText("saved..");
			}
			lstcoustmer.getItems().remove(lstcoustmer.getSelectionModel().getSelectedItem());
			txtCowQty.setText("");
			txtBuffaloQty.setText("");
			variationDate.getEditor().setText("");
			lblResult.setText("status..");
			pst.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    void filllist(){
    	ArrayList<String> nameArray=new ArrayList<>();
    	try {
			PreparedStatement pst=con.prepareStatement("select distinct cname from customerEntry");
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
    	lstcoustmer.getItems().addAll(nameArray);
    	lstcoustmer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    @FXML
    void doDouble(MouseEvent event) {
    	 txtCowQty.clear();
    		txtBuffaloQty.clear();
    		
    		try {
    			String lst=lstcoustmer.getSelectionModel().getSelectedItem();
    			PreparedStatement pst=con.prepareStatement("select cowqty,buffaloqty from customerEntry where cname=?");
    			pst.setString(1,lst);
    			ResultSet table=pst.executeQuery();
    		
    		while(table.next())
    		{
    			per=table.getFloat("cowqty");
    			per1=table.getFloat("buffaloqty");
    			lblcow.setText(per+"");
    			lblbuffalo.setText(per1+"");
    			
    		}
    	}catch (Exception e) 
    		{
    			e.printStackTrace();
    		}
    }

    @FXML
    void initialize() {
    	con=DBConnection.doConnect();
        filllist();   
      }
}