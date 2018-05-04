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

@WebServlet("/ProblemManager")
public class ProblemManagerServlet extends HttpServlet {

    public ProblemManagerServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HibernateUntil.getSessionFactory().getCurrentSession().beginTransaction();
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=utf-8");

            System.out.println("This is a problem manager");

            List<ProblemEntity> result = HibernateUntil.getSessionFactory()
                    .getCurrentSession().createQuery("from ProblemEntity").list();
            Iterator<ProblemEntity> it = result.iterator();

            ArrayList<JSONArray> usersJson = new ArrayList<JSONArray>();
            while (it.hasNext()) {
                ProblemEntity problem = (ProblemEntity) it.next();
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
            JSONArray users = JSONArray.fromObject(usersJson.toArray());


            System.out.println(users);

            out.println(users);
            out.flush();
            out.close();
            HibernateUntil.getSessionFactory().getCurrentSession().getTransaction().commit();
        }
        catch (Exception ex) {
            HibernateUntil.getSessionFactory().getCurrentSession().getTransaction().rollback();
            if (ServletException.class.isInstance(ex)) {
                throw (ServletException) ex;
            } else {
                throw new ServletException(ex);
            }
        }
    }
}
