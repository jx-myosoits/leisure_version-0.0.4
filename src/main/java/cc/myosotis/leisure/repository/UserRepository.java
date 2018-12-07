package cc.myosotis.leisure.repository;

import cc.myosotis.leisure.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {

    User findUserByUsername(String username);

}
