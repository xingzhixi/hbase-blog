package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BlogService;

/**
 * Servlet implementation class BlogCommentAddAction
 */
public class BlogCommentAddAction extends BaseAction {
	BlogService blogService = BlogService.create();

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String rowKey = request.getParameter("blogId");
		String commentContent = request.getParameter("comment");
		String uname = (String) request.getSession().getAttribute("uname");

		boolean rlt = blogService.addComment(rowKey, uname, commentContent);

		if (rlt) {
			request.getRequestDispatcher("/BlogViewAction")
					.forward(request, response);
		} else {
			request.setAttribute("msg", "修改失败");
			request.getRequestDispatcher("/error.jsp").forward(request,
					response);
		}
	}
}
