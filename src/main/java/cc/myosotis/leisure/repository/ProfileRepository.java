package cc.myosotis.leisure.repository;

import cc.myosotis.leisure.model.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile,Integer> {

    List<Profile> findProfileByEmail(String email);
}
