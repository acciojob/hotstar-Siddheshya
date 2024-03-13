package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo
        WebSeries  wb = new WebSeries();
        wb.setSeriesName(webSeriesEntryDto.getSeriesName());
        wb.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        wb.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());
        wb.setRating(webSeriesEntryDto.getRating());
        Optional<ProductionHouse> op = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId());
        ProductionHouse pr = op.get();
        wb.setProductionHouse(pr);
//        WebSeries r = webSeriesRepository.save(wb);
        int x = webSeriesRepository.countWebSeriesByProductionHouseId(pr);
//        System.out.println(webSeriesEntryDto.getSeriesName());
        double y = pr.getRatings();
        System.out.println(y+" Current Ratings");
        System.out.println(x+ " Count of WebSeries");
        double z = ((y*x)+webSeriesEntryDto.getRating())/(x+1);

        try{
            productionHouseRepository.save(pr);
            webSeriesRepository.save(wb);
            pr.setRatings(z);
            System.out.println(z + " VAlue to set");
        }
        catch(Exception e){
            return -1;
        }


        return 1;

    }

}
