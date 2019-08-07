package customertableview;

public class CoustmerBean 
{
	String cname;
	String cnumber;
	String caddress;
	float cowqty;
	float cowprice;
	float buffaloqty;
	float buffaloprice;
	String entrydate;
	
	public CoustmerBean(){}
	
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCnumber() {
		return cnumber;
	}
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
	public String getCaddress() {
		return caddress;
	}
	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	public float getCowqty() {
		return cowqty;
	}
	public void setCowqty(float cowqty) {
		this.cowqty = cowqty;
	}
	public float getCowprice() {
		return cowprice;
	}
	public void setCowprice(float cowprice) {
		this.cowprice = cowprice;
	}
	public float getBuffaloqty() {
		return buffaloqty;
	}
	public void setBuffaloqty(float buffaloqty) {
		this.buffaloqty = buffaloqty;
	}
	public float getBuffaloprice() {
		return buffaloprice;
	}
	public void setBuffaloprice(float buffaloprice) {
		this.buffaloprice = buffaloprice;
	}
	public String getEntrydate() {
		return entrydate;
	}
	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}
	public CoustmerBean(String cname, String cnumber, String caddress, float cowqty, float cowprice, float buffaloqty,
			float buffaloprice, String entrydate) {
		super();
		this.cname = cname;
		this.cnumber = cnumber;
		this.caddress = caddress;
		this.cowqty = cowqty;
		this.cowprice = cowprice;
		this.buffaloqty = buffaloqty;
		this.buffaloprice = buffaloprice;
		this.entrydate = entrydate;
	}
	
	
}
