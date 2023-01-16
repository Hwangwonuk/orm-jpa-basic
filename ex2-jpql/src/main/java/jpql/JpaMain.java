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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            member.setAge(10);
            member.setMemberType(MemberType.ADMIN);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

//            String query = "select case when m.age <= 10 then '학생요금' else '일반요금' end from Member m";
//            String query = "select coalesce(m.name, '이름 없는 회원') as userName from Member m";
            String query = "select nullif(m.name, '관리자') from Member m";

            List<String> resultList = em.createQuery(query, String.class).getResultList();

            for (String s : resultList) {
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
