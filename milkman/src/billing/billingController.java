package billing;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customerEntry.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sms.SST_SMS;

public class billingController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> lstCoustmer;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Label lblDays;

    @FXML
    private TextField qtyCow;

    @FXML
    private TextField qtyBuffalo;

    @FXML
    private TextField priceCow;

    @FXML
    private TextField PriceBuffalo;

    @FXML
    private TextField couostmerNumber;

    @FXML
    private TextField variationCowQty;

    @FXML
    private TextField VariationBuffaloQty;

    @FXML
    private TextField showTotalBill;

    @FXML
    private Label lblStatus;
    Connection con;
    float cowqty;
    float buffaloqty;
    float cowprice;
    float buffaloprice;
    int totaldays;
    float Vcqty;
	float Vbqty;
	String name;
	float BCP=0;
	float BBP=0;
	float total;
	long days;
	float totalCowQty;
	float totalBuffaloQty;
    @FXML
    void DoBilling(ActionEvent event) 
    {
    	showTotalBill.clear();
    	BCP=cowqty*cowprice*days+Vcqty*cowprice;
    	BBP=buffaloqty*buffaloprice*days+Vbqty*buffaloprice;
    	total=BCP+BBP;
    	showTotalBill.setText(total+"");
    }
    
    @FXML
    void doDouble(MouseEvent event) 
    {
    	qtyCow.clear();
    	qtyBuffalo.clear();
    	priceCow.clear();
    	PriceBuffalo.clear();
 		try {
 			String lst=lstCoustmer.getSelectionModel().getSelectedItem();
 			PreparedStatement pst=con.prepareStatement("select cowqty,cowprice,buffaloqty,buffaloprice,cnumber from customerEntry where cname=?");
 			pst.setString(1,lst);
 			ResultSet table=pst.executeQuery();
 		
 		while(table.next())
 		{
 			cowqty=table.getFloat("cowqty");
 			buffaloqty=table.getFloat("buffaloqty");
 			cowprice=table.getFloat("cowprice");
 			buffaloprice=table.getFloat("buffaloprice");
 			String coustmerNumber=table.getString("cnumber");
 			couostmerNumber.setText(coustmerNumber);
 			priceCow.setText(cowprice+"");
 			PriceBuffalo.setText(buffaloprice+"");
 			qtyCow.setText((cowqty*days)+"");
 			qtyBuffalo.setText((buffaloqty*days)+"");
 			
 		}
 	}catch (Exception e) 
 		{
 			e.printStackTrace();
 		}
    }

    @FXML
    void doGetDays(ActionEvent event) 
    {
    	LocalDate ldos=startDate.getValue();
    	LocalDate ldoe=endDate.getValue();
    	days=ChronoUnit.DAYS.between(ldos,ldoe);
    	lblDays.setText(String.valueOf(days)+"days");
    	
    }

    @FXML
    void doSaveSms(ActionEvent event) 
    {
    	try {
    		name=lstCoustmer.getSelectionModel().getSelectedItem();
    		LocalDate ldate=startDate.getValue();            //ldate=local date
			java.sql.Date swdate= java.sql.Date.valueOf(ldate);  //sql date
			LocalDate ldate1=endDate.getValue();            //ldate=local date
			java.sql.Date swdate1= java.sql.Date.valueOf(ldate1);  //sql date
			totalCowQty=(cowqty*days)+Vcqty;
			totalBuffaloQty=(buffaloqty*days)+Vbqty;
			System.out.println(BCP+" "+BBP+" "+total);
			PreparedStatement pst = con.prepareStatement("insert into Billing values(?,?,?,?,?,?,?,?,?)");
			pst.setString(1,name);
			pst.setDate(2,swdate);
			pst.setDate(3,swdate1);
			pst.setFloat(4,totalCowQty);
			pst.setFloat(5,totalBuffaloQty);
			pst.setFloat(6,BCP);
			pst.setFloat(7,BBP);
			pst.setFloat(8,total);
			pst.setInt(9,0);
			pst.executeUpdate();
		//************************* Send SMS **************************************
			String msg="your total billing of milk is"+total;
			String number=couostmerNumber.getText();
			System.out.println(number);
			String resp=SST_SMS.bceSunSoftSend(number,msg);
			System.out.println(number);
		if(resp.contains("successfully"))
			System.out.println("sent..");
		else if(resp.contains("unknown"))
			System.out.println("Check internet Connection");
		else
			System.out.println("Invalid Mobile number");
		
		lblStatus.setText("saved and message send");
			
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void doVariationCalc(ActionEvent event) 
    {
    	try {
    		String name=lstCoustmer.getSelectionModel().getSelectedItem();
			PreparedStatement pst=con.prepareStatement("Select sum(cowqty) as SCQ,sum(buffaloqty) as SBQ from variation where cname=?");
			pst.setString(1,name);
			ResultSet table=pst.executeQuery();
			while(table.next()) {
				Vcqty=table.getFloat("SCQ");
				Vbqty=table.getFloat("SBQ");
				variationCowQty.setText(Vcqty+"");
				VariationBuffaloQty.setText(Vbqty+"");
				
			}
		
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    void filllist()
    {
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
    	lstCoustmer.getItems().addAll(nameArray);
    	lstCoustmer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void initialize() 
    {   
    	con=DBConnection.doConnect();
        filllist();
    }
}
