package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.BlogService;
import bean.Blog;

public class BlogViewAction extends BaseAction {
	private BlogService blogService = BlogService.create();

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String rowKey = request.getParameter("blogId");
		Blog blog = blogService.GetBlog(rowKey);

		request.setAttribute("blog", blog);

		request.getRequestDispatcher("/blogView.jsp")
				.forward(request, response);
	}

}
