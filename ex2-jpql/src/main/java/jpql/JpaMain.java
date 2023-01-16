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

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setName("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

//            fetch join을 사용하면 즉시로딩 된다. FetchType.LAZY로 설정되어 있어도 우선적으로 적용된다.
//            String query = "select m from Member m join fetch m.team";

//            1:N 조인 관계의 조인에서 결과값이 뻥튀기 될 수 있다.
//            N:1 조인 관계는 뻥튀기 되지않고 오히려 줄어들기도 한다.
//            String query = "select t from Team t join fetch t.members";

//            중복 제거 DISTINCT
//            String query = "select distinct t from Team t join fetch t.members";

//            일반 join 문도 데이터가 뻥튀기 되는것은 동일하다.
            String query = "select t from Team t join t.members m";

            List<Team> result = em.createQuery(query, Team.class).getResultList();

            for (Team team : result) {
                System.out.println("team = " + team.getName() + ", members=" + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("-> member = " + member);
                }
                // 회원1, 팀A(SQL)
                // 회원2, 팀A(1차캐시)
                // 회원3, 팀B(SQL)

                // 회원 100명 -> N + 1
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
