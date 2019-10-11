// File: Upload.java

/* A servlet to display the contents of the MySQL FDA database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.swing.JFileChooser;

public class Upload extends HttpServlet 
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("/media/sf_Public/images"));
        chooser.setDialogTitle("chooser");
        //chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        out.println("<HTML><HEAD><TITLE>Upload</TITLE></HEAD>");
        //out.println("<BODY><H1>Choose an image to upload " + chooser.getSelectedFile() + " </H1>");
        out.println("<form action='Push' method='get'>");
    
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

          out.println("File to load: <input type='text' value=" + chooser.getSelectedFile() + " name='fileToLoad' size='40'><br>");
          out.println("Repository: <input type='text' name='repo' value='dcaas-dtr.dev.fda.gov'><br>");
          out.println("Team: <input type='text' name='team'><br>");
          out.println("Image: <input type='text' name='image'><br>");
          out.println("Image Label: <input type='text' name='label'><br>");
          out.println("<input type='submit' value='Send'><br>");

        } else {
          out.println("No selection");
        }


        out.println("</BODY></HTML>");

    }
}
