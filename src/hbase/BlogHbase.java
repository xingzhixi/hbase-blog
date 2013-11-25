package hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;

import bean.Blog;
import bean.Blog.Comment;

public class BlogHbase {
	private BaseHbase base;
	private static BlogHbase blogHbase;

	private BlogHbase() {
		base = BaseHbase.createHbase();
	}

	public static BlogHbase create() {
		if (blogHbase == null)
			blogHbase = new BlogHbase();
		return blogHbase;
	}

	/**
	 * 得到博客的信息，不包括评论内容
	 * 
	 * @return
	 */
	public List<Blog> getAllBlogInfo() {
		List<Blog> list = new ArrayList<Blog>();
		// 获取博文信息
		String[][] cols = new String[][] {
				new String[] { Blog.COLUMN_INFO, Blog.QUALIFIER_INFO_AUTHOR },
				new String[] { Blog.COLUMN_INFO, Blog.QUALIFIER_INFO_TITLE } };
		ResultScanner rs = null;
		try {
			rs = base.scanRows(Blog.TABLENAME, cols);
			if (rs == null)
				return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Result rlt : rs) {
			KeyValue[] kvs = rlt.raw();
			Blog blog = new Blog();
			list.add(blog);
			blog.setRowKey(new String(kvs[0].getRow()));
			for (KeyValue kv : kvs)
				setBlogInfoColumn(blog, kv);
		}
		return list;
	}

	public List<Blog> getBlogInfoByUser(String uname) {
		List<Blog> blogList = new ArrayList<Blog>();

		try {
			// 获取用户的博文rowkey
			ResultScanner rs = base.scanRows(Blog.TABLENAME, Blog.COLUMN_INFO,
					Blog.QUALIFIER_INFO_AUTHOR, uname);
			List<String> rowKeys = new ArrayList<String>();

			for (Result result : rs) {
				KeyValue[] kvs = result.raw();
				rowKeys.add(new String(kvs[0].getRow()));
			}
			if (rowKeys.size() == 0)// 该用户还没有博文
				return null;
			// 获取博文信息
			String[][] cols = new String[][] {
					new String[] { Blog.COLUMN_INFO, Blog.QUALIFIER_INFO_AUTHOR },
					new String[] { Blog.COLUMN_INFO, Blog.QUALIFIER_INFO_TITLE },
					new String[] { Blog.COLUMN_INFO,
							Blog.QUALIFIER_INFO_CONTENT } };
			for (String rowKey : rowKeys) {
				KeyValue[] kvs = base.getRow(Blog.TABLENAME, rowKey, cols);
				Blog blog = new Blog();
				blogList.add(blog);
				blog.setRowKey(rowKey);
				for (KeyValue kv : kvs) {
					setBlogInfoColumn(blog, kv);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return blogList;
	}

	/**
	 * 将博客的信息封装到blog对象中
	 * 
	 * @param blog
	 * @param kv
	 */
	private void setBlogInfoColumn(Blog blog, KeyValue kv) {
		String qualifier = new String(kv.getQualifier());
		if (Blog.QUALIFIER_INFO_AUTHOR.equals(qualifier)) {
			blog.setInfoAuthor(new String(kv.getValue()));
		} else if (Blog.QUALIFIER_INFO_TITLE.equals(qualifier)) {
			blog.setInfoTitle(new String(kv.getValue()));
		} else if (Blog.QUALIFIER_INFO_CONTENT.equals(qualifier)) {
			blog.setInfoContent(new String(kv.getValue()));
		}
	}

	public Blog getBlogInfoByRowKey(String rowKey) {
		Blog blog = new Blog();
		blog.setRowKey(rowKey);
		Map<String, Comment> commentMap = new HashMap<String, Comment>();
		KeyValue kvs[] = null;
		try {
			kvs = base.getRow(Blog.TABLENAME, rowKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (kvs == null || kvs.length == 0)
			return null;
		// 将查询结果封装到blog对象中
		for (KeyValue kv : kvs) {
			String family = new String(kv.getFamily());
			String qualifier = new String(kv.getQualifier());

			if (Blog.COLUMN_INFO.equals(family)) { // 如果是博客信息列
				setBlogInfoColumn(blog, kv);

			} else if (Blog.COLUMN_COMMNET_REVIEWER.equals(family)) { // 如果是博客评论者列
				Comment comm = commentMap.get(qualifier);
				if (comm == null) {
					comm = new Comment();
					comm.setReviewer(new String(kv.getValue()));
					commentMap.put(qualifier, comm);
				} else {
					comm.setReviewer(new String(kv.getValue()));
				}
			} else if (Blog.COLUMN_COMMNET_CONTENT.equals(family)) { // 如果是博客评论内容列
				Comment comm = commentMap.get(qualifier);
				if (comm == null) {
					comm = new Comment();
					comm.setContent(new String(kv.getValue()));
					commentMap.put(qualifier, comm);
				} else {
					comm.setContent(new String(kv.getValue()));
				}
			}
		}
		blog.setComments(commentMap);

		return blog;
	}

	public static void main(String[] args) {
		Blog blog = BlogHbase.create().getBlogInfoByRowKey("b1");
		List<Comment> comm = blog.getComments();

		System.out.println(blog.getInfoAuthor() + "\t" + blog.getInfoTitle()
				+ "\t" + blog.getInfoContent());

		for (Comment c : comm)
			System.out.println(c.getContent() + "\t" + c.getReviewer());
	}

	public boolean createBlog(String author, String title, String content) {
		String rowKey = System.currentTimeMillis() + "";
		try {
			base.writeOrUpdateRow(Blog.TABLENAME, rowKey, Blog.COLUMN_INFO,
					Blog.QUALIFIER_INFO_AUTHOR, author);
			base.writeOrUpdateRow(Blog.TABLENAME, rowKey, Blog.COLUMN_INFO,
					Blog.QUALIFIER_INFO_TITLE, title);
			base.writeOrUpdateRow(Blog.TABLENAME, rowKey, Blog.COLUMN_INFO,
					Blog.QUALIFIER_INFO_CONTENT, content);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateBlog(String rowKey, String title, String content) {
		try {
			base.writeOrUpdateRow(Blog.TABLENAME, rowKey, Blog.COLUMN_INFO,
					Blog.QUALIFIER_INFO_TITLE, title);
			base.writeOrUpdateRow(Blog.TABLENAME, rowKey, Blog.COLUMN_INFO,
					Blog.QUALIFIER_INFO_CONTENT, content);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteblog(String rowKey) {
		try {
			base.deleteRow(Blog.TABLENAME, rowKey);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addComment(String rowKey, String uname, String commentContent) {
		String comment_qualifier = System.currentTimeMillis() + "";
		try {
			base.writeOrUpdateRow(Blog.TABLENAME, rowKey,
					Blog.COLUMN_COMMNET_REVIEWER, comment_qualifier, uname);
			base.writeOrUpdateRow(Blog.TABLENAME, rowKey,
					Blog.COLUMN_COMMNET_CONTENT, comment_qualifier, commentContent);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
