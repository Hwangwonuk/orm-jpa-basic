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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Hibernate;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setName("member1");
            em.persist(member);

            // flush -> commit, query

            // 결과 0
            // dbconn.executeQuery("select * from member");
            // 이러한 경우 em.flush() 수동으로 flush

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
