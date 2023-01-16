package jpql;

import java.awt.print.Book;
import java.util.Collection;
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

            Team team = new Team();
            em.persist(team);

            Member member1 = new Member();
            member1.setName("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("member2");
            member2.setTeam(team);
            em.persist(member2);

            em.flush();
            em.clear();

//            단일 값 연관 경로 = 묵시적 내부 조인 inner join 발생, 탐색 O
//            String query = "select m.team from Member m";
//            String query = "select m.team from Member m";

//            컬렉션 값 연관 경로 - 묵시적 내부 조인 발생, 탐색 X
//            String query = "select t.members from Team t";
//            String query = "select t.members.size from Team t";

//            묵시적 조인이 아닌 명시적 조인을 사용하자.
            String query = "select m.name From Team t join t.members m";

            Integer result = em.createQuery(query, Integer.class).getSingleResult();

//            for (Integer o : resultList) {
//                System.out.println("o = " + o);
//            }

            System.out.println("result = " + result);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
