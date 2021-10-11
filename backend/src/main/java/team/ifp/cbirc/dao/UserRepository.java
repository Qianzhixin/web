package team.ifp.cbirc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.ifp.cbirc.entity.User;

import java.util.List;

/**
 * @author GuoXinyuan
 * @date 2021/10/11
 */

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findByUsername(String username);

}
