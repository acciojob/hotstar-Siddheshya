package com.driver.repository;

import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WebSeriesRepository extends JpaRepository<WebSeries,Integer> {

    WebSeries findBySeriesName(String seriesName);
    @Query("SELECT COUNT(ws) FROM WebSeries ws WHERE ws.productionHouse = :productionHouseId")
    Integer countWebSeriesByProductionHouseId(@Param("productionHouseId") ProductionHouse productionHouse);
}
