// File: InventoryList .java
/* A servlet to display the contents of the MySQL FDA database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ExecuteCmd extends HttpServlet 
{
    public String getServletInfo() {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        RuntimeExample re = new RuntimeExample();
        re.execute();

        out.println("<HTML><HEAD><TITLE>FDA Inventory List</TITLE></HEAD>");
        out.println("<BODY align='center'><H1>Inventory List</H1>");
        out.close();
    }
}

