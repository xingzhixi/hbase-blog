package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BlogService;

public class BlogDeleteAction extends BaseAction {
	BlogService blogService = BlogService.create();
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String rowKey = request.getParameter("blogId");
		
		boolean rlt = blogService.deleteBlog(rowKey);
		if (rlt) {
			request.getRequestDispatcher("/blog.jsp")
					.forward(request, response);
		} else {
			request.setAttribute("msg", "删除失败");
			request.getRequestDispatcher("/error.jsp").forward(request,
					response);
		}
	}

}
