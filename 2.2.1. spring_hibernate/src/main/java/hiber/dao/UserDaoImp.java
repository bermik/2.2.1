package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUser(String model, int series) {
      Car car = new Car(model, series);
      String HQL="FROM Car c WHERE c.model=:model AND c.series=:series";
     // Query q = sessionFactory.getCurrentSession().createQuery(HQL);
      return sessionFactory.getCurrentSession().createQuery(HQL, Car.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .uniqueResult()
              .getOwner();
   }
}
