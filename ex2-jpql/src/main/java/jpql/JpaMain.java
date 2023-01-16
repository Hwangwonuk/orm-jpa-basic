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

//            fetch join 대상에는 별칭을 사용하면 안된다.
//            String query = "select t from Team t join fetch t.members as m";

//            둘 이상의 컬렉션은 페치 조인 할 수 없다.
//            컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
//            일대일, 다대일 같은 단일 값 연관 필드들은 페치 조인해도 페이징 가능
//            하이버네이트는 경고 로그를 남기고 메모리에서 페이징 -> 매우 위험
//            String query = "select t from Team t join fetch t.members m";

//            @BatchSize(size = ?)를 조절, default_batch_fetch_size를 xml에 추가하여도 된다. 최적화 기능
            String query = "select t from Team t";



            List<Team> result = em.createQuery(query, Team.class)
                .setFirstResult(0)
                .setMaxResults(2)
                .getResultList();

            System.out.println("result = " + result.size());

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
