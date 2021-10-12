package team.ifp.cbirc.dao.externalRegulation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.ifp.cbirc.po.ExternalRegulation;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@Repository
public interface ExternalRegulationRepository extends JpaRepository<ExternalRegulation,Integer>,ExternalRegulationDao {

}
