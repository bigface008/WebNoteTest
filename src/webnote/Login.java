package webnote;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

@WebServlet("/checkuser")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();

            String username = request.getParameter("name");
            String password = request.getParameter("pwd");
            Boolean isValid = login(username, password);

            out.println(isValid);
            out.flush();
            out.close();
        } catch (Exception ex) {
            if (ServletException.class.isInstance(ex)) {
                throw (ServletException) ex;
            } else {
                throw new ServletException(ex);
            }
        }
    }


    private Boolean login(String username, String password) {
        Boolean isValid = false;
        Session session = HibernateUntil.getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<UserEntity> result = (List<UserEntity>) session
                .createQuery("from UserEntity where userName = :name and userPassword = :pwd")
                .setParameter("name", username).setParameter("pwd", password).list();
        tx.commit();
        if (result.size() > 0)
            isValid = true;
        else
            isValid = false;
        session.close();
        return isValid;
    }
}
