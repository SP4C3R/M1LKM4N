package paymentlog;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class paymentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private TextField totalCowQty;

    @FXML
    private TextField totalBuffaloQty;

    @FXML
    private TextField totalAmount;

    @FXML
    private TextField amountReceived;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;
    

    Connection con;
    PreparedStatement pst;
    
    @FXML
    void toFetch(ActionEvent event) 
    {
    	try {
        	String name=comboName.getSelectionModel().getSelectedItem();
        	LocalDate sldate=dateFrom.getValue(); 
			java.sql.Date sswdate= java.sql.Date.valueOf(sldate);
			LocalDate eldate=dateTo.getValue(); 
			java.sql.Date eswdate= java.sql.Date.valueOf(eldate);
			PreparedStatement pst =con.prepareStatement("select * from Billing where cname=? and startDate>=? and EndDate<=?");
        	pst.setString(1,name);
        	pst.setDate(2,sswdate);
        	pst.setDate(3,eswdate);
        	ResultSet table=pst.executeQuery();
    			while(table.next())
    			{
    				float cowqty=table.getFloat("CQtotal");
    				float buffaloqty=table.getFloat("BQtotal");
    				float totalBill=table.getFloat("totalBill");
    				totalCowQty.setText(cowqty+"");
    				totalBuffaloQty.setText(buffaloqty+"");
    				totalAmount.setText(totalBill+"");
    			}
    	    } catch (Exception e) {
    			e.printStackTrace();
    	    }
    }

    @FXML
    void toReceived(ActionEvent event) 
    {
    	 try {
 	    	String name=comboName.getSelectionModel().getSelectedItem();
// 	    	float totalamt=Float.parseFloat(totalAmount.getText());
// 	    	float amtReceived=Float.parseFloat(amountReceived.getText());
 	    	LocalDate sldate=dateFrom.getValue();            //ldate=local date
 			java.sql.Date sswdate= java.sql.Date.valueOf(sldate);  //sql date
 			LocalDate eldate=dateTo.getValue();    
 			java.sql.Date eswdate= java.sql.Date.valueOf(eldate); 
// 			pst = con.prepareStatement("insert into payment values(?,?,?,?,?)");
// 			pst.setString(1,name);
// 			pst.setFloat(2,totalamt);
//			pst.setDate(3,sswdate);
//			pst.setDate(4,eswdate);
// 			pst.setFloat(5,amtReceived);
 			PreparedStatement pst1=con.prepareStatement("update Billing set status=1 where cname=? and startDate>=? and EndDate<=?");
 			pst1.setString(1,name);
 			pst1.setDate(2,sswdate);
			pst1.setDate(3,eswdate);
 			pst1.executeUpdate();
 			
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
    }

    void fillCombo()
    {
    	ArrayList<String> nameArray=new ArrayList<>();
    	try {
			pst=con.prepareStatement("select distinct cname from variation");
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
    void initialize()
    {
    	con=DBConnection.doConnect();
    	fillCombo();
    }
}
