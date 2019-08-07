package billinghistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customerEntry.DBConnection;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
public class billingController {

	   @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	   @FXML
	   private TableView<billingbean> tableview;

	   @FXML
	   private ComboBox<String> comboName;
	   
	   @FXML
	    private ToggleGroup ab;
	   
    Connection con;
    ResultSet table;
    PreparedStatement pst;

    @FXML
    void doComboFetch(ActionEvent event) 
    {
    	try {
        	String name=comboName.getSelectionModel().getSelectedItem();	
        	PreparedStatement pst = con.prepareStatement("select * from Billing where cname=?");
        	pst.setString(1,name);
        	getAllRecordsFromTable(pst);
        	tableview.setItems(list);
        	} catch (SQLException e) {
    			e.printStackTrace();
    		}
    }

    @FXML
    void doExportExcel(ActionEvent event) 
    {
    	
    }
    @FXML
    void doPaid(MouseEvent event) 
    {
    	try {
    		if(comboName.getValue()==null) {	
        	PreparedStatement pst = con.prepareStatement("select * from billing where status=1");
        	getAllRecordsFromTable(pst);
    		}else {
        		String name=comboName.getSelectionModel().getSelectedItem();	
            	PreparedStatement pst = con.prepareStatement("select * from billing where cname=? and status=1");
            	pst.setString(1,name);
            	getAllRecordsFromTable(pst);
        	}
        	tableview.setItems(list);
    	} catch (SQLException e) {
			e.printStackTrace();
		}

    }

    @FXML
    void doUnpaid(MouseEvent event) 
    {
    	try {
    		if(comboName.getValue()==null) {	
        	PreparedStatement pst = con.prepareStatement("select * from billing where status=0");
        	getAllRecordsFromTable(pst);
    		}else {
        		String name=comboName.getSelectionModel().getSelectedItem();	
            	PreparedStatement pst = con.prepareStatement("select * from billing where cname=? and status=0");
            	pst.setString(1,name);
            	getAllRecordsFromTable(pst);
        	}
        	tableview.setItems(list);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    ObservableList<billingbean> list;
    void getAllRecordsFromTable(PreparedStatement pst) 
    {
    	list=FXCollections.observableArrayList();
    try {
    	table=pst.executeQuery();
    	while(table.next())
    	{
    		String name=table.getString("cname");
			float totalBill=table.getFloat("totalBill");
			String startdate=table.getString("startDate");
			String enddate=table.getString("EndDate");
			billingbean obj=new billingbean(name, totalBill, startdate, enddate);
			list.add(obj);
			}
    	} 
    	catch (Exception e) {
				e.printStackTrace();
			}
   }
    
    void fillCombo()
    {
    	ArrayList<String> nameArray=new ArrayList<>();
    	try {
			pst=con.prepareStatement("select distinct cname from Billing");
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

    @SuppressWarnings("unchecked")
   	void addcolumn()
       {
       	TableColumn<billingbean, String>name=new TableColumn<billingbean, String>("Name");//Dikhava Title
       	name.setCellValueFactory(new PropertyValueFactory<>("cname"));//bean field name not table col. name
       	
       	TableColumn<billingbean, Float>Total_Amount=new TableColumn<billingbean, Float>("TotalAmount");//Dikhava Title
       	Total_Amount.setCellValueFactory(new PropertyValueFactory<>("totalBill"));//bean field name not table col. name
       	
       	TableColumn<billingbean, String> Start_Date=new TableColumn<billingbean, String>("StartDate");//Dikhava Title
       	Start_Date.setCellValueFactory(new PropertyValueFactory<>("startDate"));//bean field name not table col. name
       	
    	TableColumn<billingbean, String> End_Date=new TableColumn<billingbean, String>("EndDate");//Dikhava Title
    	End_Date.setCellValueFactory(new PropertyValueFactory<>("EndDate"));//bean field name not table col. name
       	
        tableview.getColumns().clear();
        tableview.getColumns().addAll(name,Total_Amount,Start_Date,End_Date);
       }
    
    @FXML
    void initialize() 
    {
    	con=DBConnection.doConnect();
    	fillCombo();
    	addcolumn();
    }
}
