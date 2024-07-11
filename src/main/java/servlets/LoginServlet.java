package servlets;


import auth.AuthenticationService;
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
import java.io.PrintWriter;


@WebServlet(name = "login",value = "")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        AuthenticationService.initDBConnection();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie cookie = Util.getCookie(req, "username");
		
		if(cookie!=null) {
			resp.sendRedirect("/profile");
		}else {
			req.getRequestDispatcher("/login.jsp").forward(req,resp);
		}

        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String message=AuthenticationService.login(username,password);

        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();

        switch (message){
            case AuthenticationService.USER_NOT_FOUND:{
                out.println("No user found with username: "+username);
                break;
            }
            case AuthenticationService.WRONG_PASSWORD:{
                out.println("Incorrect password , please enter a valid password");
                break;
            }
            case AuthenticationService.FAILURE:{
                out.println("Error occurred while attempting to login");
                break;
            }
            case AuthenticationService.SUCCESS:{
            	Cookie cookie = new Cookie("username", username);
            	cookie.setMaxAge(60*60);
				resp.addCookie(cookie);
                out.println("Login successful");
                resp.sendRedirect("/profile");
                break;
            }
            default:{
                System.out.println("Unknown Error");
            }
        }

        req.getRequestDispatcher("/login.jsp").include(req,resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        AuthenticationService.removeDBConnection();
    }
}
