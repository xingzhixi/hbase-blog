package service;

import hbase.BlogHbase;
import bean.Blog;

public class BlogService {
	private BlogHbase hbase = BlogHbase.create();

	private static BlogService blogService;

	private BlogService() {
	}

	public static BlogService create() {
		if (blogService == null)
			blogService = new BlogService();
		return blogService;
	}

	public Blog GetBlog(String rowKey) {
		return hbase.getBlogInfoByRowKey(rowKey);
	}

	public boolean createBlog(String author, String title, String content) {
		return hbase.createBlog(author, title, content);
	}

	public boolean updateBlog(String rowKey, String title, String content) {
		return hbase.updateBlog(rowKey, title, content);
	}

	public boolean deleteBlog(String rowKey) {
		return hbase.deleteblog(rowKey);
	}

	public boolean addComment(String rowKey, String uname, String commentContent) {
		return hbase.addComment(rowKey, uname, commentContent);
	}
}
