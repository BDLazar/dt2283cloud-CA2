<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"  %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="com.google.appengine.api.datastore.Query.Filter"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterPredicate"%>
<%@ page import="com.google.appengine.api.datastore.Query.FilterOperator"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>

<%
	// Create a new blob store service
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<html>
 	<head>
 		<title>Upload</title>
 	</head>
 	
 	<body>
 	<center>
 		<h1>Upload Photos</h1>
		<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            <input type="file" name="imageFile">
            <input type="submit" value="Submit">
		</form>
		
		<%
			String uploadImageFoo = "foo";
			// Create a new datastore service to provide storage for images
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			// Create a primary Key for datastore Entity
			Key uploadImageKey = KeyFactory.createKey("image", uploadImageFoo); 
			// A Query which sorts images by descending order
			Query ImageQuery = new Query("UploadImage", uploadImageKey).addSort("date", Query.SortDirection.DESCENDING);
			// Create a collection of image entities
			List<Entity> UploadImageEntitys = datastore.prepare(ImageQuery).asList(FetchOptions.Builder.withLimit(5)); 
			// If we have no images there is nothing to display
			if (UploadImageEntitys.isEmpty() == true) 
			{
		        %>
		        <p>Nothing to display</p>
		        <%
			} 
			else 
			{
				// otherwise iterate through our Entity list
				for (Entity UploadImage : UploadImageEntitys)  
				{
					pageContext.setAttribute("UploadImageImageURL",	UploadImage.getProperty("imageUrl")); // set the URL of image	
					pageContext.setAttribute("imageBlobkey", UploadImage.getProperty("blobkey")); // gets the blobkey by its name
					UserService userService = UserServiceFactory.getUserService(); // create userService for logging out
		        		User user = userService.getCurrentUser();
						
		%>
				${fn:escapeXml(imagekey)}
				<%-- Display image to user by its URL --%>		
				<img src=${fn:escapeXml(UploadImageImageURL) } /> 
				
	        		<%                     
				
						} // End of Entity Loop							
						
					}// End of else statement
				%>
 	</body>
</html>
