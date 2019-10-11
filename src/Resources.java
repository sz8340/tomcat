// File: Resources .java
/* A servlet to display the contents of the MySQL FDA database */


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Resources extends HttpServlet 
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

        out.println("<HTML><HEAD><TITLE>FDA Container Sizes</TITLE></HEAD>");
        out.println("<BODY align='center'><H1>Container Sizes</H1>");

	//String server=request.getParameter("server");
        //String filter = ""; 

        //if ( !server.equals("") ) {
        //   filter = " server='" + server + "'";
        //}

		
        // Load the mm.MySQL driver
        try
           {
              Class.forName("org.gjt.mm.mysql.Driver");
              //Class.forName("com.mysql.jdbc.Driver");
              //Class.forName("oracle.jdbc.driver.OracleDriver");
              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement

              Statement statement2 = dbcon.createStatement();
              String query2 = "delete from resources;";
              //ResultSet rs2 = statement2.executeUpdate(query2);
              statement2.executeUpdate(query2);
              //rs2.close();
              statement2.close();


              Statement statement1 = dbcon.createStatement();
              String query1 = "load data local infile '/data/resources.csv' into table fda.resources fields terminated by ',' lines terminated by '\n' ignore 1 lines (application, size, requested, actual)";
              ResultSet rs1 = statement1.executeQuery(query1);
              rs1.close();
              statement1.close();


              Statement statement = dbcon.createStatement();
              String query = "select application, size, requested, actual from fda.resources";
              ResultSet rs = statement.executeQuery(query);

              out.println("<TABLE noborder align='center'>");
              out.println("<tr bgcolor='lightgray'>" +
                              "<td width='300'>APPLICATION</td>" +
                              "<td width='180'>SIZE</td>" +
                              "<td width='150'>REQUESTED</td>" +
                              "<td width='150'>ACTUAL</td>" +
                              "</tr>");

              // Iterate through each row of rs
              while (rs.next())
              {
                  String m_app = rs.getString("application");
                  String m_size = rs.getString("size");
                  String m_req = rs.getString("requested");
                  String m_actual = rs.getString("actual");
                  out.println("<tr>" +
                              "<td>" + m_app + "</td>" +
                              "<td>" + m_size + "</td>" +
                              "<td align='middle'>" + m_req + "</td>" +
                              "<td align='right'>" + m_actual + "</td>" +
                              "</tr>");
              }



              rs.close();
              statement.close();


              Statement statement4 = dbcon.createStatement();
              String query4 = "select size,sum(requested),sum(actual) from fda.resources group by size;";
              ResultSet rs4 = statement4.executeQuery(query4);

              out.println("<TABLE noborder align='center'>");
              out.println("<tr><td>&nbsp</tr>");
              out.println("<tr bgcolor='lightgray'>" +
                              "<td width='180'>SIZE</td>" +
                              "<td width='150'>TOTAL REQUESTED</td>" +
                              "<td width='150'>TOTAL ACTUAL</td>" +
                              "</tr>");

              // Iterate through each row of rs
              while (rs4.next())
              {
                  String m_size = rs4.getString("size");
                  String m_req = rs4.getString("sum(requested)");
                  String m_actual = rs4.getString("sum(actual)");
                  out.println("<tr>" +
                              "<td>" + m_size + "</td>" +
                              "<td align='middle'>" + m_req + "</td>" +
                              "<td align='right'>" + m_actual + "</td>" +
                              "</tr>");
              }

              rs4.close();
              statement4.close();
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
