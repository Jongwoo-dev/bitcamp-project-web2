package bitcamp.java89.ems2.servlet.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bitcamp.java89.ems2.dao.impl.StudentMysqlDao;
import bitcamp.java89.ems2.domain.Student;



@WebServlet("/student/list")
public class StudentListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws ServletException, IOException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<title>학생관리-목록</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>학생 정보</h1>");
    try {
      StudentMysqlDao studentDao = StudentMysqlDao.getInstance();
      ArrayList<Student> list = studentDao.getList();

      out.println("<a href='form.html'>추가</a><br>");
      out.println("<table border='1'>");
      out.println("<tr>");
      out.println("  <th>등록번호</th><th>이름</th><th>전화</th><th>이메일</th><th>재직여부</th>");
      out.println("</tr>");

      for (Student student : list) {
        out.println("<tr> ");
        //* 링크 이따가 수정
        out.printf("  <td>%s</a></td><td><a href='detail?memberNo=%1$s'>%s</td><td>%s</td><td>%s</td><td>%s</td>\n",
            student.getMemberNo(),
            student.getName(),
            student.getTel(),
            student.getEmail(),
            student.isWorking() ? "재직중" : "휴직중");
        out.println("</tr>");
      }

      out.println("</table>");
      out.println("</body>");
      out.println("</html>");

    } catch (Exception e) {
      throw new ServletException(e);
    }

  }
}
