package org.raman.hibernate;

import org.raman.hibernate.org.raman.hibernate.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by ramanh3 on 04/01/2018.
 */
public class HClient {
    EntityManagerFactory entityManagerFactory;
    public HClient() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "PU");
    }

    public  void exec(){
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<User> result = entityManager.createQuery("from User", User.class ).getResultList();
        for ( User user : result ) {
            System.out.println( "User (" +user .getName() + ") : " + user.getId() );
        }
        entityManager.getTransaction().commit();
        entityManager.close();


    }
    public static void main(String[] args) {
        HClient client = new HClient();
        client.exec();
    }
}
