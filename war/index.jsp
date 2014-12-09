<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
 <head>
 <title>Upload</title>
 </head>
 <body>
 <center>
 	 <img src="img.jpg">
	 <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
		 <input type="file" name="imageFile">
		 <input type="submit" value="Submit">
	 </form>
 </body>
</html>