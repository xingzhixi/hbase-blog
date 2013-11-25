package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BlogService;

public class BlogEditAction extends BaseAction {
	BlogService blogService = BlogService.create();

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String rowKey = request.getParameter("rowKey");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		boolean rlt = blogService.updateBlog(rowKey, title, content);

		if (rlt) {
			request.getRequestDispatcher("/blogView.jsp")
					.forward(request, response);
		} else {
			request.setAttribute("msg", "修改失败");
			request.getRequestDispatcher("/error.jsp").forward(request,
					response);
		}

	}

}
