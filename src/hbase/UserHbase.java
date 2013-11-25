package hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.KeyValue;

import bean.Account;

public class UserHbase {
	private BaseHbase base;

	private static UserHbase userHbase;

	private UserHbase() {
		base = BaseHbase.createHbase();
	}

	public static UserHbase create() {
		if (userHbase == null)
			userHbase = new UserHbase();
		return userHbase;
	}

	public boolean register(String uname, String upwd) {
		try {
			if (isUserExist(uname))
				return false;
			base.writeOrUpdateRow(Account.TABLE_NAME, uname,
					Account.COLUMN_UPWD, "", upwd);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean isUserExist(String uname) {
		try {
			KeyValue[] kvs = base.getRow(Account.TABLE_NAME, uname);
			if (kvs == null || kvs.length == 0)
				return false;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean login(String uname, String upwd) {
		try {
			KeyValue[] kvs = base.getRow(Account.TABLE_NAME, uname,
					Account.COLUMN_UPWD);
			if (kvs == null || kvs.length != 1)
				return false;
			String userPwd = new String(kvs[0].getValue());
			if (upwd.equals(userPwd))
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
