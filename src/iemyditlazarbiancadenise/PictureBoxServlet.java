package iemyditlazarbiancadenise;

import java.io.IOException;
import java.security.Principal;
import javax.servlet.http.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class PictureBoxServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		resp.setContentType("text/plain");
		
		UserService userService = UserServiceFactory.getUserService();
		Principal myPrincipal = req.getUserPrincipal();
		String emailAddress = null;
		
		//Declaring 2 Administrators
		String admin1 = "denyse08@gmail.com";
		String admin2 = "markdeegan@dit.ie";
		
		String thisURL = req.getRequestURI();
		String loginURL = userService.createLoginURL(thisURL);
		String logoutURL = userService.createLogoutURL(thisURL);
		String uploadURL = "/index.jsp";
		resp.setContentType("text/html");

	
		if(myPrincipal == null) 
		{
			resp.getWriter().println("<p>You are not logged in! &#9785;</p>");
			resp.getWriter().println("<p>You can <a href=\""+loginURL+"\">sign in here</a>.&#9756;</p>");
		} // end if not logged in
		
		if(myPrincipal != null) 
		{
			emailAddress = myPrincipal.getName();
			//Checking if Admin has been selected 
			if(userService.isUserAdmin() == true)
			{
				//Checking the possibilities of the 2 Admin
				if(emailAddress.equals(admin1) || emailAddress.equals(admin2))
				{
					resp.getWriter().println("<p>Welcome Admin &#9786;</p>");
					resp.getWriter().println("<p>You are logged in as: "+emailAddress+"</p>");
					resp.getWriter().println("<p>Do you want to <a href=\"" + uploadURL +"\">upload a photo?</a>.</p>");
					resp.getWriter().println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>");
				
				}
				//Sign in as member
				else
				{
					resp.getWriter().println("<p>You are not Admin</p>");
					resp.getWriter().println("<p>To sign in as a member<a href=\""+loginURL+"\"> Click Here</a>.</p>");
				}
			}
			//Regular user
			else
			{
				resp.getWriter().println("<p>You are logged in as: "+emailAddress+"</p>");
				resp.getWriter().println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>");
			}
		} // end if logged in
	}
}