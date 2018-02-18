package org.raman.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.raman.hibernate.org.raman.hibernate.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * Created by ramanh3 on 04/01/2018.
 */
public class HClient {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    Logger logger = Logger.getLogger(HClient.class);
    Random random = new SecureRandom();
    public HClient() {
        entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void execSimpleQuery() {
        entityManager.getTransaction().begin();
        List<User> result = entityManager.createQuery("from User", User.class).getResultList();
        for (User user : result) {
            logger.info("User " + user);
        }
        entityManager.getTransaction().commit();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    public void execSimpleCriteria() {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        entityManager.getTransaction().begin();
        Criteria executableCriteria = getExecutableCriteria(criteria);
        List<User> result = executableCriteria.list();
        for (User user : result) {
            logger.info("User " + user);
        }
        entityManager.getTransaction().commit();
      }

    public void execSqlRestrictionCriteria() {
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        String fieldName = "name";
        final String sql = String.format("%s = cast( ? as varchar)",fieldName);
        criteria.add(Restrictions.sqlRestriction(sql,"haim",StringType.INSTANCE));
        entityManager.getTransaction().begin();
        Criteria executableCriteria = getExecutableCriteria(criteria);
        List<User> result = executableCriteria.list();
        for (User user : result) {
            logger.info("User " + user);
        }
        entityManager.getTransaction().commit();
      }

    private Criteria getExecutableCriteria(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria((Session) entityManager.getDelegate());
    }

    private void createUser(){
        User user = new User();
        final long id = Math.abs(random.nextLong());
        user.setName("Test-"+id);
        String description = new String((id+" בדיקה").getBytes(), StandardCharsets.UTF_8);
        user.setDescription(description);
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.detach(user);
    }

    public static void main(String[] args) {
        HClient client = new HClient();
        client.createUser();
       // client.execSimpleQuery();
        client.execSimpleCriteria();
        //client.execSqlRestrictionCriteria();
        client.close();
    }
}
