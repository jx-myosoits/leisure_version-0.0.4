package cc.myosotis.leisure.repository;

import cc.myosotis.leisure.model.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SortRepository extends JpaRepository<Sort,Integer> {

    Sort findSortBySortName(String sortName);
}
