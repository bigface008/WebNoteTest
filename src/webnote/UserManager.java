package webnote;

import org.hibernate.Session;
import java.util.*;

public class UserManager {

    public static void main(String[] args) {
        UserManager mgr = new UserManager();

        if (args[0].equals("list")) {
//            mgr.createAndStoreUser();
            List users = mgr.listUsers();
            for (int i = 0; i < users.size(); i++) {
                UserEntity user = (UserEntity) users.get(i);
                System.out.println(
                        "ID: " + user.getUserId() + " Name: " + user.getUserName() + " Password: " +
                        user.getUserPassword() + " Email: " + user.getUserEmail() + " Phone: " + user.getUserPhone()
                );
            }
        }

        HibernateUntil.getSessionFactory().close();
    }

    @SuppressWarnings("unchecked")
    private List<UserEntity> listUsers() {
        Session session = HibernateUntil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<UserEntity> result = session.createQuery("from UserEntity").list();
        session.getTransaction().commit();
        return result;
    }

    private void createAndStoreUser(Integer id, String name, String password, String type, String email, String phone) {
        Session session = HibernateUntil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        UserEntity user = new UserEntity();
        user.setUserId(id);
        user.setUserName(name);
        user.setUserPassword(password);
        user.setUserType(type);
        user.setUserEmail(email);
        user.setUserPhone(phone);
        session.save(user);

        session.getTransaction().commit();
    }
}
