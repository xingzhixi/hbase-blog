package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BlogService;

/**
 * Servlet implementation class BlogCreateAction
 */
public class BlogCreateAction extends BaseAction {
	BlogService blogService = BlogService.create();

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String uname = (String) request.getSession().getAttribute("uname");

		boolean rlt = blogService.createBlog(uname, title, content);
		if (rlt) {
			request.getRequestDispatcher("/blog.jsp")
					.forward(request, response);
		} else {
			request.setAttribute("msg", "添加失败");
			request.getRequestDispatcher("/error.jsp").forward(request,
					response);
		}

	}

}
