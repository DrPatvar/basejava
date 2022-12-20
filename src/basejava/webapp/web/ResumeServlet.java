package basejava.webapp.web;


import basejava.webapp.Config;
import basejava.webapp.model.Resume;
import basejava.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/resumes")
public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    @Override
    public void init() throws ServletException {
        sqlStorage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "root");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");


        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<H3> Resume list </H3>");
        out.println("<table width=59% border=1><tr>" + "<td>UUID</td>" + "<td>FULLNAME</td>" + "</tr>" );
        List<Resume> list = sqlStorage.getAllSorted();
        for (int i = 0; i <10 ; i++) {
            out.println("<tr>");
            out.println("<td>" + i + "</td>");
            out.println("<td>" + i+3 + "</td>");
            out.println("</tr>");
        }
       /* for (Resume resume: list
        ) {
            out.println("<tr>");
            out.println("<td>" + resume.getUuid() + "</td>" );
            out.println("<td>" + resume.getFullName() + "</td>");
            out.println("</tr>");
        }*/
        out.println("</table>");
        out.println("</body></html>");

    }


}