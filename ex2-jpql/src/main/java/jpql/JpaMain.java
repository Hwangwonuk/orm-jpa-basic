package jpql;

import java.util.List;
import jpql.domain.Member;
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

            member.setTeam(team);

            em.persist(member);


            em.flush();
            em.clear();

            String query = "select m from Member m left join Team t on m.name = t.name";
            List<Member> result = em.createQuery(query, Member.class)
                .getResultList();

            System.out.println("result.size() = " + result.size());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
