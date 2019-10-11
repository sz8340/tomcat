// File: Images .java
/* A servlet to display the contents of the MySQL FDA database */


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Images extends HttpServlet 
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        String loginUser = "Dude1";
        String loginPasswd = "SuperSecret7@";
        String loginUrl = "jdbc:mysql://mysql1:3306/fda";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

	String server=request.getParameter("server");
        out.println("<HTML><HEAD><TITLE>FDA Docker Images</TITLE></HEAD>");
        out.println("<BODY align='center'><H1>Docker Images - " + server + "</H1>");

        String filter = ""; 

        if ( !server.equals("") ) {
           filter = "where server='" + server + "'";
        }

		
        // Load the mm.MySQL driver
        try
           {
              Class.forName("org.gjt.mm.mysql.Driver");
              //Class.forName("com.mysql.jdbc.Driver");
              //Class.forName("oracle.jdbc.driver.OracleDriver");
              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

              Statement statement2 = dbcon.createStatement();
              String query2 = "delete from images;";
              statement2.executeUpdate(query2);
              statement2.close();


              Statement statement1 = dbcon.createStatement();
              String query1 = "load data local infile '/data/images.csv' into table fda.images fields terminated by ',' lines terminated by '\n' ignore 1 lines (server, repository, tag, id, created, size)";
              ResultSet rs1 = statement1.executeQuery(query1);
              rs1.close();
              statement1.close();

              Statement statement = dbcon.createStatement();
              String query = "select repository, tag, id, created, size from fda.images " + filter;
              ResultSet rs = statement.executeQuery(query);

              out.println("<TABLE noborder align='center'>");
              out.println("<tr bgcolor='lightgray'>" +
                              "<td width='300'>REPOSITORY</td>" +
                              "<td width='180'>TAG</td>" +
                              "<td width='150'>ID</td>" +
                              "<td width='150'>CREATED</td>" +
                              "<td width='150'>SIZE</td>" +
                              "</tr>");

              // Iterate through each row of rs
              while (rs.next())
              {
                  String m_repository = rs.getString("repository");
                  String m_tag = rs.getString("tag");
                  String m_id = rs.getString("id");
                  String m_created = rs.getString("created");
                  String m_size = rs.getString("size");
                  out.println("<tr>" +
                              "<td>" + m_repository + "</td>" +
                              "<td>" + m_tag + "</td>" +
                              "<td align='middle'>" + m_id + "</td>" +
                              "<td align='right'>" + m_created + "</td>" +
                              "<td align='right'>" + m_size + "</td>" +
                              "</tr>");
              }


              rs.close();
              statement.close();
              dbcon.close();

            }
        catch (SQLException ex) {
              out.println(ex);
              while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
                out.println("<HTML>" +
                            "<HEAD><TITLE>" +
                            "Error" +
                            "</TITLE></HEAD>\n<BODY>" +
                            "<P>SQL error in doGet: " +
                            ex.getMessage() + "</P></BODY></HTML>");
                return;
            }
         out.close();
    }
}
