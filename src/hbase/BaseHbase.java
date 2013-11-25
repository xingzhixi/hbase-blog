package hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;

import bean.Blog;

public class BaseHbase {
	Configuration conf;
	private static BaseHbase base;

	private BaseHbase() {
		conf = HBaseConfiguration.create();
	}

	/**
	 * 单例模式创建对象，省去了对象的重复初始化
	 * 
	 * @return
	 */
	public static BaseHbase createHbase() {
		if (base == null)
			base = new BaseHbase();
		return base;
	}

	public void writeOrUpdateRow(String tablename, String rowkey,
			String family, String qualifier, String value) throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Put put = new Put(rowkey.getBytes());
		put.add(family.getBytes(), qualifier.getBytes(), value.getBytes());
		table.put(put);
	}

	public KeyValue[] getRow(String tablename, String rowkey)
			throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Get get = new Get(rowkey.getBytes());
		Result res = table.get(get);
		KeyValue[] kvs = res.raw();

		return kvs;
	}

	public KeyValue[] getRow(String tablename, String rowkey, String family,
			String qualifier) throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Get get = new Get(rowkey.getBytes());
		get.addColumn(family.getBytes(), qualifier.getBytes());
		Result res = table.get(get);
		KeyValue[] kvs = res.raw();
		return kvs;
	}

	public KeyValue[] getRow(String tablename, String rowkey, String[][] cols)
			throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Get get = new Get(rowkey.getBytes());
		for (String[] col : cols)
			get.addColumn(col[0].getBytes(), col[1].getBytes());
		Result res = table.get(get);
		KeyValue[] kvs = res.raw();
		return kvs;
	}

	public KeyValue[] getRow(String tablename, String rowkey, String family)
			throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Get get = new Get(rowkey.getBytes());
		get.addFamily(family.getBytes());
		Result res = table.get(get);
		KeyValue[] kvs = res.raw();
		return kvs;
	}

	public void deleteRow(String tablename, String rowkey) throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Delete del = new Delete(rowkey.getBytes());
		table.delete(del);
	}

	/**
	 * 按照列值进行查询
	 * @param tablename
	 * @param family
	 * @param qualifier
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public ResultScanner scanRows(String tablename, String family,
			String qualifier, String value) throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Scan scan = new Scan();

		scan.addColumn(family.getBytes(), qualifier.getBytes());

		Filter filter = new ValueFilter(CompareOp.EQUAL, new BinaryComparator(
				value.getBytes()));
		scan.setFilter(filter);

		ResultScanner rs = table.getScanner(scan);
		return rs;
	}

	/**
	 * 查询指定的列
	 * @param tablename
	 * @param cols
	 * @return
	 * @throws IOException
	 */
	public ResultScanner scanRows(String tablename, String[][] cols) throws IOException {
		HTable table = new HTable(conf, tablename.getBytes());
		Scan scan = new Scan();
		for (String[] col : cols) {
			String family = col[0];
			String qualifier = col[1];
			scan.addColumn(family.getBytes(), qualifier.getBytes());
		}
		
		ResultScanner rs = table.getScanner(scan);
		return rs;
	}

	public static void main(String[] args) throws IOException {
		BaseHbase base = BaseHbase.createHbase();
		// String[][] cols = new String[][] { new String[] { "info", "title" },
		// new String[] { "info", "content" },
		// new String[] { "info", "author" } };
		// Filter filter = new ValueFilter(CompareOp.EQUAL,
		// new RegexStringComparator("a"));
		// ResultScanner rs = base.scanRows("blog", cols, filter);

		ResultScanner rs = base.scanRows(Blog.TABLENAME, Blog.COLUMN_INFO,
				Blog.QUALIFIER_INFO_AUTHOR, "pb");

		for (Result result : rs) {
			KeyValue[] kvs = result.raw();
			for (KeyValue kv : kvs) {
				String rowKey = new String(kv.getRow());
				KeyValue[] temp = base.getRow(Blog.TABLENAME, rowKey,
						Blog.COLUMN_INFO, Blog.QUALIFIER_INFO_CONTENT);
				for (int i = 0; i < temp.length; i++) {
					System.out.print(new String(temp[i].getRow()) + "\t");
					System.out.print(new String(temp[i].getFamily()) + ":");
					System.out.print(new String(temp[i].getQualifier()) + "\t");
					System.out.print(temp[i].getTimestamp() + "\t");
					System.out.println(new String(temp[i].getValue()));

				}
			}
		}

	}
}
