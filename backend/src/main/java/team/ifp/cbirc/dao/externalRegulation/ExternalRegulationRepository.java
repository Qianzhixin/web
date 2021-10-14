package team.ifp.cbirc.dao.externalRegulation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.ifp.cbirc.po.ExternalRegulation;

import java.util.Date;

/**
 * @author GuoXinyuan
 * @date 2021/10/9
 */

@Repository
public interface ExternalRegulationRepository extends JpaRepository<ExternalRegulation,Integer>,ExternalRegulationDao {

    @Modifying
    @Query(nativeQuery = true,value = "" +
            "update internet_facing_plus.external_regulation " +
            "set title=?2,number=?3,type=?4,publishing_department=?5,effectiveness_level=?6,release_date=?7,implementation_date=?8,interpretation_department=?9 " +
            "where id=?1"
    )
    void update(int id, String title, String number, String type, String publishingDepartment, Integer effectivenessLevel, Date releaseDate,Date implementationDate,String interpretationDepartment);

}
