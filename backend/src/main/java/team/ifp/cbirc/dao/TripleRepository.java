package team.ifp.cbirc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.ifp.cbirc._enum.Predicate;
import team.ifp.cbirc.po.Triple;

import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/12/9
 */
@Repository
public interface TripleRepository extends JpaRepository<Triple,Integer> {

    List<Triple> findBySourceId(int SId);

    List<Triple> findByTargetId(int TId);

    List<Triple> findByTargetIdAndPredicate(int targetId,Predicate predicate);

    List<Triple> findBySourceIdAndPredicate(int sourceId,Predicate predicate);

}
