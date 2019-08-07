package variationlog;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import customerEntry.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class variationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<varitionbean> tableview;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker EndDate;

    @FXML
    private ComboBox<String> comboName;
    
    Connection con;
    ResultSet table;
    PreparedStatement pst;

    @FXML
    void doFetchAll(ActionEvent event) 
    {
    	try {
        	PreparedStatement pst = con.prepareStatement("select * from variation");
        	getAllRecordsFromTable(pst);
        	tableview.setItems(list);
        	} catch (SQLException e) {
    			e.printStackTrace();
    		}
    }
    
    @FXML
    void doComboFetch(ActionEvent event) 
    {
    	try {
        	String name=comboName.getSelectionModel().getSelectedItem();	
        	PreparedStatement pst = con.prepareStatement("select * from variation where cname=?");
        	pst.setString(1,name);
        	getAllRecordsFromTable(pst);
        	tableview.setItems(list);
        	} catch (SQLException e) {
    			e.printStackTrace();
    		}
    }


    @FXML
    void doFetchByDate(ActionEvent event) 
    {
    	try {
        	String name=comboName.getSelectionModel().getSelectedItem();
        	LocalDate sldate=startDate.getValue(); 
			java.sql.Date sswdate= java.sql.Date.valueOf(sldate);
			LocalDate eldate=EndDate.getValue(); 
			java.sql.Date eswdate= java.sql.Date.valueOf(eldate);
        	PreparedStatement pst = con.prepareStatement("select * from variation where cname=? and variationDate>=? and variationDate<=?");
        	pst.setString(1,name);
        	pst.setDate(2,sswdate);
        	pst.setDate(3,eswdate);
        	getAllRecordsFromTable(pst);
        	tableview.setItems(list);
        	} catch (SQLException e) {
    			e.printStackTrace();
    		}
    }
    
    ObservableList<varitionbean> list;
    void getAllRecordsFromTable(PreparedStatement pst) 
    {
    	list=FXCollections.observableArrayList();
    try {
    	table=pst.executeQuery();
    	while(table.next())
    	{
    		String name=table.getString("cname");
			String date=table.getString("variationDate");
			float cowqty=table.getFloat("cowqty");
			float buffaloqty=table.getFloat("buffaloqty");
			varitionbean obj=new varitionbean(name, date, cowqty, buffaloqty);
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
    @SuppressWarnings("unchecked")
   	void addcolumn()
       {
       	TableColumn<varitionbean, String>name=new TableColumn<varitionbean, String>("name");//Dikhava Title
       	name.setCellValueFactory(new PropertyValueFactory<>("cname"));//bean field name not table col. name
       	
       	TableColumn<varitionbean, String>date=new TableColumn<varitionbean, String>("date");//Dikhava Title
       	date.setCellValueFactory(new PropertyValueFactory<>("variationDate"));//bean field name not table col. name
       	
       	TableColumn<varitionbean, Float> cowqty=new TableColumn<varitionbean, Float>("cowqty");//Dikhava Title
       	cowqty.setCellValueFactory(new PropertyValueFactory<>("cowqty"));//bean field name not table col. name
       	
    	TableColumn<varitionbean, Float> buffaloqty=new TableColumn<varitionbean, Float>("buffaloqty");//Dikhava Title
        buffaloqty.setCellValueFactory(new PropertyValueFactory<>("buffaloqty"));//bean field name not table col. name
       	
        tableview.getColumns().clear();
        tableview.getColumns().addAll(name,date,cowqty,buffaloqty);
       }

    @FXML
    void initialize() {
        con=DBConnection.doConnect();
        fillCombo();
        addcolumn();

    }
}
