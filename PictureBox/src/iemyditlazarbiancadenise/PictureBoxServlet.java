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
		
		//2 Administrators
		String admin1 = "denyse08@gmail.com";
		String admin2 = "markdeegan@dit.ie";
		
		//Users
		String[] users = {"lroxana201@gmail.com", "MaximiliaMihoc@gmail.com"};
		String thisURL = req.getRequestURI();
		String loginURL = userService.createLoginURL(thisURL);
		String logoutURL = userService.createLogoutURL(thisURL);
		String uploadURL = "/index.jsp";
		String viewURL = "/serve";
		String deleteURL = "delete.jsp";
		resp.setContentType("text/html");
	
		if(myPrincipal == null) 
		{
			resp.getWriter().println("<p>You are not logged in! &#9785;</p>");
			resp.getWriter().println("<p>You can <a href=\""+loginURL+"\">sign in here</a>.&#9756;</p>");
		} 
		
		if(myPrincipal != null) 
		{
			emailAddress = myPrincipal.getName();
			//If Admin has been selected 
			if(userService.isUserAdmin() == true)
			{
				//Checking the possibilities of the 2 Admin
				if(emailAddress.equals(admin1) || emailAddress.equals(admin2))
				{
					resp.getWriter().println("<p><h2>Welcome Admin! &#9786;</h2></p>");
					resp.getWriter().println("<p>You are logged in as: "+emailAddress+"</p>");
					resp.getWriter().println("<p><b>Do you want to:</b><br/><ul><li><a href=\"" + uploadURL +"\">upload a photo?</a></p>");
					resp.getWriter().println("<p><br/><li><a href=\"" + deleteURL +"\">delete a photo?</a></p>");
					resp.getWriter().println("<p><br/><li><a href=\"" + viewURL +"\">view photos?</a></p>");
					resp.getWriter().println("<p><br/><li><a href=\"" + logoutURL +"\">sign out</a></ul></p>");
				
				}
				//Sign in as member
				else
				{
					resp.getWriter().println("<p>You are not Admin</p>");
					resp.getWriter().println("<p>To sign in as a member<a href=\""+loginURL+"\"> Click Here</a>.</p>");
				}
			}
			else if(userService.isUserAdmin() == false)
			{

				for(int i = 0; i < users.length;)
				{
					if(emailAddress.equals(users[i]))
					{
						resp.getWriter().println("<p><h2>Welcome User!</h2></p>");
						resp.getWriter().println("<p>You are logged in as: "+emailAddress+"</p>");
						resp.getWriter().println("<p><b>Do you want to:</b><br/><ul><li><a href=\"" + uploadURL +"\">upload a photo?</a></p>");
						resp.getWriter().println("<p><br/><li><a href=\"" + deleteURL +"\">delete a photo?</a></p>");
						resp.getWriter().println("<p><br/><li><a href=\"" + viewURL +"\">view photos?</a></p>");
						resp.getWriter().println("<p><br/><li><a href=\"" + logoutURL +"\">sign out</a></ul></p>");
						break;
					}
					
					else
					{
						resp.getWriter().println("<p>The username doesn't match the list of users</p>");
						resp.getWriter().println("<p>Would you like to <a href=\"" + logoutURL +"\">try again</a>.</p>");
						break;
					}
				}
			}
			//Regular user
			else
			{
				resp.getWriter().println("<p>You are logged in as: "+emailAddress+"</p>");
				resp.getWriter().println("<p>You can <a href=\"" + logoutURL +"\">sign out</a>.</p>");
			}	
		} 
	}
}
