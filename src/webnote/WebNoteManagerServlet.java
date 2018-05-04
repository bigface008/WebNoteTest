package webnote;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

@WebServlet("/WebNoteManager")
public class WebNoteManagerServlet extends HttpServlet {

    public WebNoteManagerServlet() { super(); }

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            HibernateUntil.getSessionFactory().getCurrentSession().beginTransaction();
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");

            System.out.println("This is a webnote manager");

            List<UserEntity> user_result = HibernateUntil.getSessionFactory()
                    .getCurrentSession().createQuery("from UserEntity").list();
            List<ProblemEntity> problem_result = HibernateUntil.getSessionFactory()
                    .getCurrentSession().createQuery("from ProblemEntity").list();
            Iterator<UserEntity> user_it = user_result.iterator();
            Iterator<ProblemEntity> problem_it = problem_result.iterator();

            ArrayList<JSONArray> usersJson = new ArrayList<JSONArray>();
            ArrayList<JSONArray> problemsJson = new ArrayList<>();
            while (user_it.hasNext()) {
                UserEntity user = (UserEntity) user_it.next();
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(String.valueOf(user.getUserId()));
                arrayList.add(user.getUserName());
                arrayList.add(user.getUserPassword());
                arrayList.add(user.getUserType());
                arrayList.add(user.getUserEmail());
                arrayList.add(user.getUserPhone());
                usersJson.add(JSONArray.fromObject(arrayList));
            }
            JSONArray users = JSONArray.fromObject(usersJson.toArray());

            while (problem_it.hasNext()) {
                ProblemEntity problem = (ProblemEntity) problem_it.next();
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(String.valueOf(problem.getProblemId()));
                arrayList.add(problem.getUserName());
                arrayList.add(problem.getProblemName());
                arrayList.add(problem.getSubject());
                arrayList.add(problem.getDescription());
                arrayList.add(problem.getReason());
                arrayList.add(problem.getAddDate());
                arrayList.add(problem.getAnswer());
                arrayList.add(problem.getSemester());
                arrayList.add(String.valueOf(problem.getRedoTimes()));
                usersJson.add(JSONArray.fromObject(arrayList));
            }
            JSONArray problems = JSONArray.fromObject(problemsJson.toArray());

            System.out.println(user_result);
            System.out.println(problem_result);

            out.println(user_result);
            out.println(problem_result);
            out.flush();
            out.close();
            HibernateUntil.getSessionFactory().getCurrentSession().getTransaction().commit();
        } catch (Exception ex) {
            HibernateUntil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if (ServletException.class.isInstance(ex)) {
                throw (ServletException) ex;
            } else {
                throw new ServletException(ex);
            }
        }
    }
}
