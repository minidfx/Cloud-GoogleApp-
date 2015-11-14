package ch.hes_so.mse.cloud;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class DatastoreWriteServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		// Define the output type
		resp.setContentType("text/plain");
		
		PrintWriter pw = resp.getWriter();
		
		if(!req.getParameterNames().hasMoreElements())
		{
			pw.println("ERROR: No argument specified for writing a new book entity.");
			return;
		}
		
		pw.println("Writing entity to datastore.");
		
		// Gets the entity name, this is mandatory argument.
		String entityName = req.getParameter("entityName");
		
		// Retrieve the store service from Google.
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        // Create a new book entity
        Entity newEntity = new Entity(entityName);
        
		// Gets the parameters of the page passed in the URL
        Enumeration<String> paramerterNames = req.getParameterNames();
        while(paramerterNames.hasMoreElements())
        {
        	String parameterName = paramerterNames.nextElement();
        	String parameterValue = req.getParameter(parameterName);
        	
        	newEntity.setProperty(parameterName, parameterValue);
        }
        
        // Save the entity
        datastore.put(newEntity);
        
        pw.println("Entity saved.");
	}
}
