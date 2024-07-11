package servlets;

import auth.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.Util;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "register",value = "/register")
public class RegisterServlet extends HttpServlet {

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
    		req.getRequestDispatcher("/register.jsp").forward(req,resp);
    	}
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");

        String message= AuthenticationService.register(username,password);


        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();

        switch (message){
            case AuthenticationService.USER_EXISTS:{
                out.println("User already exists with name :"+username);
                break;
            }
            case AuthenticationService.FAILURE:{
                out.println("Error While registering user");
                break;
            }
            case AuthenticationService.SUCCESS:{
                out.println("User has been registered");
                out.println("You can now login");
                break;
            }
            default:{
                System.out.println("Unknown Error");
            }

        }
        req.getRequestDispatcher("/register.jsp").include(req,resp);
    }

    @Override
    public void destroy() {
        AuthenticationService.removeDBConnection();
        super.destroy();
    }
}
