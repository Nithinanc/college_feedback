

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/FBForm")

public class FBForm extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		
		System.out.println("entered FBForm.java");
		String STable=request.getParameter("STN");
		String FTable=request.getParameter("FTN");
		String SId=request.getParameter("SID");
		String sub[]={request.getParameter("SubjectA"),request.getParameter("SubjectB"),request.getParameter("SubjectC")};
		System.out.println("all paramaters taken");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("after class.forName stmt");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/feedbackform","root","root");
			System.out.println("after establishing connection");
			PreparedStatement ps=con.prepareStatement("update "+STable+" set Status='Y' where UId=?");
			ps.setString(1,SId);
			int r=ps.executeUpdate();
			System.out.println("after executing STable update");
			int i=0;
			if(r==1)
			{
				int res=0;
				ps=con.prepareStatement("select * from "+FTable+"");
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					int n=Integer.parseInt(rs.getString(sub[i]))+1;
					String str=rs.getString("FacultyName");
					System.out.println(i+" "+n+" "+str+" "+sub[i]);
					PreparedStatement cps=con.prepareStatement("update "+FTable+" set "+sub[i++]+" =? where FacultyName= ?");
					cps.setLong(1, n);
					cps.setString(2, str);
					res=cps.executeUpdate();
					System.out.println(res);
				}
				if(res==1)
					pw.println("You have Successfully submitted feed back form");
				con.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
