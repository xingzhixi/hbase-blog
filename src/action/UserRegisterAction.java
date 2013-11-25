package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

public class UserRegisterAction extends BaseAction{
	private UserService user = UserService.create();

	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("uname");
		String upwd = request.getParameter("upwd");
		boolean result = user.register(uname,upwd);
		
		if(result){
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else{
			request.setAttribute("msg", "注册失败");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}

}
