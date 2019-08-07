package variationlog;

public class varitionbean 
{
	String cname;
	String variationDate;
	float cowqty;
	float buffaloqty;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getVariationDate() {
		return variationDate;
	}
	public void setVariationDate(String variationDate) {
		this.variationDate = variationDate;
	}
	public float getCowqty() {
		return cowqty;
	}
	public void setCowqty(float cowqty) {
		this.cowqty = cowqty;
	}
	public float getBuffaloqty() {
		return buffaloqty;
	}
	public void setBuffaloqty(float buffaloqty) {
		this.buffaloqty = buffaloqty;
	}
	public varitionbean(){}
	
	public varitionbean(String cname, String variationDate, float cowqty, float buffaloqty) {
		super();
		this.cname = cname;
		this.variationDate = variationDate;
		this.cowqty = cowqty;
		this.buffaloqty = buffaloqty;
	}
	
}
