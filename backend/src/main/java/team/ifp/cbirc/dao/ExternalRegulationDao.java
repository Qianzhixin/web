package team.ifp.cbirc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;
import team.ifp.cbirc.po.ExternalRegulation;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@Repository
public interface ExternalRegulationDao extends JpaRepository<ExternalRegulation,Integer> {


}
