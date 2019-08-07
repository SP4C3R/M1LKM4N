package billinghistory;

public class billingbean {
		String cname;
		float totalBill; 
		String startDate;
		String EndDate;
		public String getCname() {
			return cname;
		}

		public void setCname(String cname) {
			this.cname = cname;
		}

		public float getTotalBill() {
			return totalBill;
		}

		public void setTotalBill(float totalBill) {
			this.totalBill = totalBill;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return EndDate;
		}

		public void setEndDate(String endDate) {
			EndDate = endDate;
		}

		public billingbean(){}

		public billingbean(String cname, float totalBill, String startDate, String endDate) {
			super();
			this.cname = cname;
			this.totalBill = totalBill;
			this.startDate = startDate;
			EndDate = endDate;
		}
}
