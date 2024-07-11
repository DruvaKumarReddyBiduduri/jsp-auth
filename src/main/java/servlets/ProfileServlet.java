package servlets;

import db.User;
import db.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Util;

import java.io.IOException;
//import java.util.Arrays;

@WebServlet(name = "profile",value = "/profile")
public class ProfileServlet extends HttpServlet {

    UserService userService;
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = null;
		
		Cookie cookie = Util.getCookie(req, "username");
		
		if(cookie!=null) {
			username = cookie.getValue();
			User user = userService.findByName(username);
			if(user==null) {
				req.getRequestDispatcher("/login.jsp").forward(req,resp);
			}else {
				req.setAttribute("user", user);
		        req.getRequestDispatcher("/profile.jsp").forward(req,resp);
			}
		}else {
			req.getRequestDispatcher("/login.jsp").forward(req,resp);
		}
		
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req,resp);
    }

    @Override
    public void destroy() {
        userService = null;
        super.destroy();
    }
}
