package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

/**
 * Servlet implementation class UserLoginAction
 */
public class UserLoginAction extends BaseAction {
	private UserService service = UserService.create();

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String upwd = request.getParameter("upwd");
		boolean rlt = service.login(uname, upwd);

		if (rlt) {
			request.getSession().setAttribute("uname", uname);
			request.getRequestDispatcher("/blog.jsp")
					.forward(request, response);
		} else {
			request.setAttribute("msg", "登录失败");
			request.getRequestDispatcher("/login.jsp").forward(request,
					response);
		}

	}
}
