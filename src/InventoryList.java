// File: InventoryList .java
/* A servlet to display the contents of the MySQL FDA database */


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class InventoryList extends HttpServlet 
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

        out.println("<HTML><HEAD><TITLE>FDA Inventory List</TITLE></HEAD>");
        out.println("<BODY align='center'><H1>Inventory List</H1>");

	String app=request.getParameter("application");
	String team=request.getParameter("team");
	//String user=request.getParameter("user");
        String filter = ""; 

        if ( !app.equals("") ) {
           filter = " and applications.application_name='" + app + "'";
        }

        if ( !team.equals("") ) {
          filter += " and teams.team_name='" + team + "'";
        }
        
		
        // Load the mm.MySQL driver
        try
           {
              Class.forName("org.gjt.mm.mysql.Driver");
              //Class.forName("com.mysql.jdbc.Driver");
              //Class.forName("oracle.jdbc.driver.OracleDriver");
              Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              Statement statement = dbcon.createStatement();

              String query = "select inventory.container_id, teams.team_name, containers.container_size, containers.container_price, applications.application_name from fda.inventory, fda.teams , fda.containers, fda.applications  where inventory.team_id=teams.id and inventory.container_size=containers.id and inventory.application_id=applications.id " + filter + ";";

              // Perform the query
              ResultSet rs = statement.executeQuery(query);

              out.println("<TABLE noborder align='center'>");
              out.println("<tr bgcolor='lightgray'>" +
                              "<td width='180'>APPLICATION</td>" +
                              "<td width='180'>TEAM</td>" +
                              "<td width='180'>CONTAINER ID</td>" +
                              "<td width='150'>CONTAINER SIZE</td>" +
                              "<td width='150'>CONTAINER PRICE</td>" +
                              "</tr>");

              // Iterate through each row of rs
              while (rs.next())
              {
                  String m_container_id = rs.getString("container_id");
                  String m_team_name = rs.getString("team_name");
                  String m_container_size = rs.getString("container_size");
                  String m_container_price = rs.getString("container_price");
                  String m_application_name = rs.getString("application_name");
                  out.println("<tr>" +
                              "<td>" + m_application_name + "</td>" +
                              "<td>" + m_team_name + "</td>" +
                              "<td>" + m_container_id + "</td>" +
                              "<td align='middle'>" + m_container_size + "</td>" +
                              "<td align='right'>" + m_container_price + "</td>" +
                              "</tr>");
              }


              rs.close();
              statement.close();
              //dbcon.close();
              //Connection dbcon2 = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
              // Declare our statement
              Statement statement2 = dbcon.createStatement();

              String query2 = "select sum(containers.container_price) from fda.inventory, fda.teams , fda.containers, fda.applications  where inventory.team_id=teams.id and inventory.container_size=containers.id and inventory.application_id=applications.id " + filter + ";";
              ResultSet rs2 = statement2.executeQuery(query2);
              while (rs2.next())
              {
                  String m_total = rs2.getString("SUM(containers.container_price)");
                  out.println("<tr bgcolor='lightgray'>" +
                              "<td colspan='4'>" + "TOTAL: " + "</td>" +
                              "<td align='right'>" + m_total + "</td>" +
                              "</tr>");
              }
              out.println("</TABLE>");
              out.println("<button onclick='history.back()'>Go Back</button>");
              rs2.close();
              statement2.close();
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
