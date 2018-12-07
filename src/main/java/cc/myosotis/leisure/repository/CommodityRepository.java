package cc.myosotis.leisure.repository;

import cc.myosotis.leisure.model.Commodity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommodityRepository extends CrudRepository<Commodity, Integer> {

    Commodity findCommodityByCommodityNumber(String commodityNumber);

    List<Commodity> findCommoditiesByCreator(String creator);

    List<Commodity> findCommoditiesByCreatorAndCommodityName(String username, String commodityName);

    @Query(value = "select * from leisure4.commodity where commodity_name like :name limit :page,:size", nativeQuery = true)
    List<Commodity> getByDefault(@Param("name") String name,
                                 @Param("page") Integer page,
                                 @Param("size") Integer size);

    @Query(value = "select * from leisure4.commodity where commodity_name like :name order by create_time desc limit :page,:size", nativeQuery = true)
    List<Commodity> getByLatest(@Param("name") String name,
                                @Param("page") Integer page,
                                @Param("size") Integer size);

    @Query(value = "select * from leisure4.commodity where commodity_name like :name order by score desc limit :page,:size", nativeQuery = true)
    List<Commodity> getByGoods(@Param("name") String name,
                               @Param("page") Integer page,
                               @Param("size") Integer size);

    @Query(value = "select * from leisure4.commodity where commodity_name like :name order by price asc limit :page,:size", nativeQuery = true)
    List<Commodity> getByPriceAsc(@Param("name") String name,
                                  @Param("page") Integer page,
                                  @Param("size") Integer size);

    @Query(value = "select * from leisure4.commodity where commodity_name like :name order by price desc limit :page,:size", nativeQuery = true)
    List<Commodity> getByPriceDesc(@Param("name") String name,
                                   @Param("page") Integer page,
                                   @Param("size") Integer size);

    @Query(value = "select * from leisure4.commodity where commodity_name like :name and price >= :min and price <= :max order by price asc limit :page,:size", nativeQuery = true)
    List<Commodity> getByPriceAreaDesc(@Param("min") Double min,
                                       @Param("max") Double max,
                                       @Param("name") String name,
                                       @Param("page") Integer page,
                                       @Param("size") Integer size);

    @Query(value = "select * from leisure4.commodity where commodity_name like :name and price >= :min and price <= :max order by price desc limit :page,:size", nativeQuery = true)
    List<Commodity> getByPriceAreaAsc(@Param("min") Double min,
                                      @Param("max") Double max,
                                      @Param("name") String name,
                                      @Param("page") Integer page,
                                      @Param("size") Integer size);


}
