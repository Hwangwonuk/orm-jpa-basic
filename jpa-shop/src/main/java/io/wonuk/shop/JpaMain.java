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
            Address address = new Address("city", "street", "10000");

            Member member = new Member();
            member.setName("member1");
            member.setAddress(address);
            em.persist(member);

            Address newAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            member.setAddress(newAddress);

//            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
//
//            Member member2 = new Member();
//            member2.setName("member2");
//            member2.setAddress(copyAddress);
//            em.persist(member2);

//            member.getAddress().setCity("new city");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
