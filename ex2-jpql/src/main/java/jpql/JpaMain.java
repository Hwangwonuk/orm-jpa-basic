package jpql;

import java.awt.print.Book;
import java.util.List;
import jpql.domain.Member;
import jpql.domain.MemberType;
import jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member1 = new Member();
            member1.setName("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("member2");
            em.persist(member2);

            em.flush();
            em.clear();

//            JPQL 기본 함수
//            String query = "select 'a' || 'b' from Member m";
//            String query = "select locate('de', 'abcdegf') from Member m";
//            String query = "select index(t.members) from Team t";

//            사용자 정의 함수
            String query = "select function('group_concat', m.name) from Member m";
//            hibernate를 사용하면 select group_concat(m.name) from Member m 형태로 사용이 가능하다.
            List<Integer> resultList = em.createQuery(query, Integer.class).getResultList();

            for (Integer s : resultList) {
                System.out.println("s = " + s);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
