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
            member1.setAge(0);
            em.persist(member1);

            Member member2 = new Member();
            member2.setName("회원2");
            member2.setTeam(teamA);
            member2.setAge(0);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("회원3");
            member3.setTeam(teamB);
            member3.setAge(0);
            em.persist(member3);

            // 벌크 연산
            // 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리, 벌크 연산을 먼저 실행하고 영속성 컨텍스트를 초기화 해야한다.
            // FLUSH
            int resultCount = em.createQuery("update Member m set m.age = 20")
                .executeUpdate();

            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            // DB 에만 반영이 되어있기 때문에 em.clear 로 영속성 컨텍스트 초기화 필수
            // @Modifying 어노테이션을 사용하면 쿼리가 사용될 때 자동으로 영속성 컨텍스트를 초기화 시켜준다.
            System.out.println("findMember = " + findMember.getAge());

            System.out.println("resultCount = " + resultCount);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
