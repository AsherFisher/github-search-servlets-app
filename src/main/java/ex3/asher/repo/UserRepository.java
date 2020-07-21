package ex3.asher.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/* default scope of this Bean is "singleton" */
public interface UserRepository extends JpaRepository<ex3.asher.repo.UsersSearch, Long> {

    /* add here the queries you need - in addition to CRUD operations */

    // return List of find by name
    List    <ex3.asher.repo.UsersSearch> findByName(String name);
    // return List of first 10 order by userCount desc
    List    <ex3.asher.repo.UsersSearch> findFirst10ByOrderByUserCountDesc();

}
