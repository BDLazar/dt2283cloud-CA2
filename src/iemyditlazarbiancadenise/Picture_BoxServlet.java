package iemyditlazarbiancadenise;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class Picture_BoxServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		resp.setContentType("text/plain");
		resp.getWriter().println("Welcome,user &#9786;");
		
		UserService userService = UserServiceFactory.getUserService(); // create instance of UserService
		
		Principal myPrincipal = req.getUserPrincipal(); 
		String emailAddress = null;
		
		String thisURL = req.getRequestURI();
		String loginURL = userService.createLoginURL(thisURL);
		String logoutURL = userService.createLogoutURL(thisURL);
		
		resp.setContentType("text/html"); // set type to text
		
		if(myPrincipal == null) 
		{ // if null then not logged in
			resp.getWriter().println("<p>You are not logged</p>"); // error message written to screen
			resp.getWriter().println("<p>You can <a href=\""+loginURL+"\">sign in here</a>.</p>"); // link to sign in
		} // end if	not logged in
		
		if(myPrincipal !=null) 
		{ // if not null then logged in
			emailAddress = myPrincipal.getName(); // get name for email address
			resp.getWriter().println("<p>You are logged in as (email):"+emailAddress+"</p>"); // reference to email address on screen
			resp.getWriter().println("<p>You can <a href=\"" + logoutURL + "\">sign out</a>.</p>"); // link to sign out
		}// end if logged in
	}// end class	
		
}
