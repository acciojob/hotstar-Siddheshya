package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import jdk.internal.loader.AbstractClassLoaderValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WebSeriesRepository webSeriesRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;


    public Integer addUser(User user){
        User NewUser = new User();
        User u = userRepository.save(user);
        return u.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
        List<WebSeries> wb = new ArrayList<>();
        wb = webSeriesRepository.findAll();
        Optional<User> op = userRepository.findById(userId);
        Subscription sub = subscriptionRepository.findSubscriptionByUserId(op.get());
        int count = 0;
        for(int i=0;i<wb.size();i++){
            if(sub.getSubscriptionType() == SubscriptionType.BASIC){
                if(wb.get(i).getAgeLimit()>18 && wb.get(i).getSubscriptionType()==SubscriptionType.BASIC){
                    count++;
                }
            }
            else if(sub.getSubscriptionType() == SubscriptionType.PRO){
                if(wb.get(i).getAgeLimit()>18 && (wb.get(i).getSubscriptionType()==SubscriptionType.BASIC || wb.get(i).getSubscriptionType()==SubscriptionType.PRO)){
                    count++;
                }
            }
            else{
                count++;
            }
        }
        return count;
    }


}
