package io.wonuk.shop;

import io.wonuk.shop.domain.Address;
import io.wonuk.shop.domain.Book;

import io.wonuk.shop.domain.Child;
import io.wonuk.shop.domain.Member;
import io.wonuk.shop.domain.Parent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.hibernate.Hibernate;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            int a = 10;
            int b = 10;

            System.out.println("a == b : " + (a == b));

            Address address1 = new Address("city", "street", "10000");
            Address address2 = new Address("city", "street", "10000");

            System.out.println("address1 == address2 : " + (a == b));
            System.out.println("address1.equals(address2) : " + address1.equals(address2));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
