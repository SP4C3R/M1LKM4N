/**
 * Sample Skeleton for 'table.fxml' Controller Class
 */

package customertableview;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import customerEntry.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class tableController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tableView"
    private TableView<CoustmerBean> tableView; // Value injected by FXMLLoader

    @FXML // fx:id="chooseDate"
    private DatePicker chooseDate; // Value injected by FXMLLoader
    
    Connection con;
    ResultSet table;
    PreparedStatement pst;

    @FXML
    void dofByDateetch(ActionEvent event) 
    {
    	try {
			LocalDate ldate=chooseDate.getValue(); 
			java.sql.Date swdate= java.sql.Date.valueOf(ldate);
			pst = con.prepareStatement("select * from customerEntry where entrydate=?");
			pst.setDate(1,swdate);
			getAllRecordsFromTable(pst);
			tableView.setItems(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void dofetch(ActionEvent event) 
    {
    	try {
			pst=con.prepareStatement("select * from customerEntry");
			getAllRecordsFromTable(pst);
			tableView.setItems(list);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void radBuffalo(MouseEvent event) 
    {
			try {
				pst=con.prepareStatement("select * from customerEntry where buffaloqty");
			getAllRecordsFromTable(pst);
			tableView.setItems(list);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    }

    @FXML
    void radCow(MouseEvent event)
    {
    	try {
			pst=con.prepareStatement("select * from customerEntry where cowqty");
		getAllRecordsFromTable(pst);
		tableView.setItems(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    ObservableList<CoustmerBean> list;
    void getAllRecordsFromTable(PreparedStatement pst) 
    {
    	list=FXCollections.observableArrayList();
    try {
    	table=pst.executeQuery();
    	while(table.next())
    	{
    		String name=table.getString("cname");
			String number=table.getString("cnumber");
			String address=table.getString("caddress");
			float cowqty=table.getFloat("cowqty");
			float cowprice=table.getFloat("cowprice");
			float buffaloqty=table.getFloat("buffaloqty");
			float buffaloprice = table.getFloat("buffaloprice");
			String entrydate=table.getString("entrydate");
			CoustmerBean obj=new CoustmerBean(name, number, address, cowqty, cowprice, buffaloqty, buffaloprice, entrydate);
			list.add(obj);
			}
    	} 
    	catch (Exception e) {
				e.printStackTrace();
			}
   }

    @SuppressWarnings("unchecked")
	void addcolumn()
    {
    	TableColumn<CoustmerBean, String>name=new TableColumn<CoustmerBean, String>("name");//Dikhava Title
    	name.setCellValueFactory(new PropertyValueFactory<>("cname"));//bean field name not table col. name
    	
    	TableColumn<CoustmerBean, String>number=new TableColumn<CoustmerBean, String>("number");//Dikhava Title
    	number.setCellValueFactory(new PropertyValueFactory<>("cnumber"));//bean field name not table col. name
    	
    	TableColumn<CoustmerBean, String>address=new TableColumn<CoustmerBean, String>("address");//Dikhava Title
    	address.setCellValueFactory(new PropertyValueFactory<>("caddress"));//bean field name not table col. name
    	
    	TableColumn<CoustmerBean, Float> cowqty=new TableColumn<CoustmerBean, Float>("cowqty");//Dikhava Title
    	cowqty.setCellValueFactory(new PropertyValueFactory<>("cowqty"));//bean field name not table col. name
    	
    	TableColumn<CoustmerBean, Float>cowprice=new TableColumn<CoustmerBean, Float>("cowprice");//Dikhava Title
    	cowprice.setCellValueFactory(new PropertyValueFactory<>("cowprice"));//bean field name not table col. name
    	
    	TableColumn<CoustmerBean, Float>buffaloqty=new TableColumn<CoustmerBean, Float>("buffaloqty");//Dikhava Title
    	buffaloqty.setCellValueFactory(new PropertyValueFactory<>("buffaloqty"));//bean field name not table col. name
    	
    	TableColumn<CoustmerBean, Float>buffaloprice=new TableColumn<CoustmerBean, Float>("buffaloprice");//Dikhava Title
    	buffaloprice.setCellValueFactory(new PropertyValueFactory<>("buffaloprice"));//bean field name not table col. name
    	
    	TableColumn<CoustmerBean, String>entrydate=new TableColumn<CoustmerBean, String>("cname");//Dikhava Title
    	entrydate.setCellValueFactory(new PropertyValueFactory<>("entrydate"));//bean field name not table col. name
    	
    	tableView.getColumns().clear();
    	tableView.getColumns().addAll(name, number, address, cowqty, cowprice, buffaloqty, buffaloprice, entrydate);
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	con= DBConnection.doConnect();
    	addcolumn();

    }
}
