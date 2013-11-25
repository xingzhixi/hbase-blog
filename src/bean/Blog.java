package bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Blog {
	public static final String TABLENAME = "blog";
	public static final String COLUMN_INFO = "info";
	public static final String QUALIFIER_INFO_TITLE = "title";
	public static final String QUALIFIER_INFO_CONTENT = "content";
	public static final String QUALIFIER_INFO_AUTHOR = "author";

	public static final String COLUMN_COMMNET_REVIEWER = "comment_reviewer";
	public static final String COLUMN_COMMNET_CONTENT = "comment_content";

	private String rowKey;
	private String infoTitle;
	private String infoContent;
	private String infoAuthor;
	private List<Comment> comms = new ArrayList<Comment>();

	public static class Comment {
		private String reviewer;
		private String content;

		public String getReviewer() {
			return reviewer;
		}

		public void setReviewer(String reviewer) {
			this.reviewer = reviewer;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getInfoTitle() {
		return infoTitle;
	}

	public void setInfoTitle(String infoTitle) {
		this.infoTitle = infoTitle;
	}

	public String getInfoContent() {
		return infoContent;
	}

	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}

	public String getInfoAuthor() {
		return infoAuthor;
	}

	public void setInfoAuthor(String infoAuthor) {
		this.infoAuthor = infoAuthor;
	}

	public List<Comment> getComments() {
		return comms;
	}

	public void addComment(Comment comm) {
		comms.add(comm);
	}

	public void setComments(Map<String, Comment> commentMap) {
		for (Entry<String, Comment> entry : commentMap.entrySet()) {
			comms.add(entry.getValue());
		}
	}
}
