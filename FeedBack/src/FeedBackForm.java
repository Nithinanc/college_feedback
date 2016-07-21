

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/FeedBackForm")
public class FeedBackForm extends HttpServlet {
	public static boolean val;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		String UserId=request.getParameter("userId");
		String Year=request.getParameter("Year");
		String Branch=request.getParameter("Branch");
		String Section=request.getParameter("Section");
		String Password=request.getParameter("Password");
		String StudentTable="student_"+Year+"_"+Branch+"_"+Section;
		String FacultyTable="faculty_"+Year+"_"+Branch+"_"+Section;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/feedbackform","root","root");
			PreparedStatement ps=con.prepareStatement("select * from "+StudentTable+" where UId=? and Password=?");
			ps.setString(1,UserId);
			ps.setString(2, Password);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				if(rs.getString("Status").equalsIgnoreCase("N"))
				{
					ps=con.prepareStatement("select * from "+FacultyTable+"");
					ResultSet frs=ps.executeQuery();
					pw.println("<html><head><title>feedbackform</title>"
								+ "<style>"
									+ "form"
										+ "{ "
											+ "margin-left:20%;"
											+ "margin-top:5%;"
										+ "}"
										+ "#h"
										+ "{"
											+ "color:green;"
											+ "text-align:center;"
										+ "}"
										+ "#clr"
										+ "{"
											+ "color:red;"
										+ "}"
								+ "</style>"
							+ "</head>"
						+ "<body>");
					pw.println("<table><tr><td><img src='E:\\DEVELOPMENT FOLDER\\Eclipse\\Eclipse_Kepler\\logo.gif' width='300' height='90' alt='logo'></td><td><pre>    </pre></td><td><h1 id='h'><u><b>Feed Back Form</b></u></h1></td></tr>");
					pw.println("<form method='get' action='FBForm'>");
					pw.println("<input type='hidden' name='FTN' value="+FacultyTable+">");
					pw.println("<input type='hidden' name='STN' value="+StudentTable+">");
					pw.println("<input type='hidden' name='SID' value="+UserId+">");
					pw.println("<table><tr><td colspan='11' align='center'><h2 id='h'><b><u>Fill Form</u></b></h2></td></tr>");
					while(frs.next())
					{
						String facultyName=frs.getString("FacultyName");
						String subject=frs.getString("Subject");
						pw.println("<tr>");
						pw.println("<td><b>"+facultyName+"("+subject+"):</b></td>");
						pw.println("<td><input type='radio' name="+subject+" value='verypoor'></td><td>Very Poor</td>");
						pw.println("<td><input type='radio' name="+subject+" value='poor'></td><td>Poor</td>");
						pw.println("<td><input type='radio' name="+subject+" value='avg' checked='checked'></td><td>Average</td>");
						pw.println("<td><input type='radio' name="+subject+" value='good'></td><td>Good</td>");
						pw.println("<td><input type='radio' name="+subject+" value='verygood'></td><td>Very Good</td>");
						pw.println("</tr>");
					}
					con.close();
					pw.println("<tr><td colspan='11' align='center'><input type='submit' value='Submit Form'></td></tr>");
					pw.println("</table></form></body></html>");
				}
				else
				{
					pw.write(UserId+"have already have already Submitted feedbackform");
				}
			}
			else
	        {
	           RequestDispatcher rd = request.getRequestDispatcher("index.html");
	           pw.write("Username or Password incorrect");
	           rd.forward(request, response);
	        }
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
