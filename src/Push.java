// File: Push.java
/* A servlet to display the contents of the MySQL FDA database */


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Push extends HttpServlet 
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET
    PrintWriter out;
    String fileToLoad, repo, team, image, label = "";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        out = response.getWriter();
	fileToLoad=request.getParameter("fileToLoad");
	repo=request.getParameter("repo");
	team=request.getParameter("team");
	image=request.getParameter("image");
	label=request.getParameter("label");

        out.println("<HTML><HEAD><TITLE>FDA Docker Images</TITLE></HEAD>");

        Command cmd = new Command();

        out.println("1. Loading tar file: " + fileToLoad + "<br>");
        String load = "docker load -i " + fileToLoad;
        out.println("&nbsp&nbsp&nbsp ---> " + load + "<br>");

        String result  =  cmd.execute(load);
        if ( result.contains("success") ) {

          out.println("&nbsp&nbsp&nbsp <font color='green'>ok<font color='black'><br><br>");

          String[] oldtag = result.split(",", 2);
          out.println("2. Tagging image: " + oldtag[1] + "<br>");
          String newtag = repo + "/" + team + "/" + image + ":" + label;
          String tag = "docker tag " + oldtag[1] + " " + newtag;
          out.println("&nbsp&nbsp&nbsp ---> " + tag + "<br>");
          if ( cmd.execute(tag).contains("success") ) {
            out.println("&nbsp&nbsp&nbsp <font color='green'>ok<font color='black'><br><br>");

            out.println("3. Pushing image to DTR: " + newtag + "<br>");
            String push = "docker push " + newtag;
            out.println("&nbsp&nbsp&nbsp ---> " + push + "<br>");

            if ( cmd.execute(push).contains("success") ) {
              out.println("&nbsp&nbsp&nbsp <font color='green'>ok<font color='black'><br><br>");
            } else {
              out.println("&nbsp&nbsp&nbsp <font color='red'>fail<font color='black'><br><br>");
            }
          } else {
            out.println("&nbsp&nbsp&nbsp <font color='red'>fail<font color='black'><br><br>");
          }
        } else {
          out.println("&nbsp&nbsp&nbsp <font color='red'>fail<font color='black'><br><br>");
        }

        //out.println("<BODY align='center'><H1>Docker Images - " + fileToLoad + "</H1>");
        out.println("</BODY></HTML>");
        out.close();
    }
}

