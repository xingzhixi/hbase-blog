package bean;

public class Account {
	public static final String TABLE_NAME = "account";
	public static final String COLUMN_UPWD = "upwd";
	private static final String COLUMN_UPWD_QUALIFER = "";

	private String uname; // rowKey
	private String upwd;

	public String getRowKey() {
		return uname;
	}

	public void setRowKey(String rowkey) {
		uname = rowkey;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}
}
