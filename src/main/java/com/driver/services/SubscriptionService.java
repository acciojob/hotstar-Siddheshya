package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    WebSeriesRepository webSeries;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        Subscription subs = new Subscription();
        subs.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        Date currentDate = new Date();
        subs.setStartSubscriptionDate(currentDate);
        Optional<User> op = userRepository.findById(subscriptionEntryDto.getUserId());
        subs.setUser(op.get());
        subs.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.PRO){
            subs.setTotalAmountPaid(800 + 250*subscriptionEntryDto.getNoOfScreensRequired());
        }
        else if(subscriptionEntryDto.getSubscriptionType()==SubscriptionType.BASIC){
            subs.setTotalAmountPaid(500 + 200*subscriptionEntryDto.getNoOfScreensRequired());
        }
        else{
            subs.setTotalAmountPaid(500 + 200*subscriptionEntryDto.getNoOfScreensRequired());
        }
        subscriptionRepository.save(subs);
        return subs.getTotalAmountPaid();
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        try{
            Optional<User> op = userRepository.findById(userId);
            Subscription sub = subscriptionRepository.findSubscriptionByUserId(op.get());
            if(sub.getSubscriptionType()==SubscriptionType.ELITE){
                throw new Exception();
            }
            else if(sub.getSubscriptionType()==SubscriptionType.BASIC){
                int now = sub.getNoOfScreensSubscribed();
                int extra = (800 + 250*now)-(500+200*now);
                sub.setSubscriptionType(SubscriptionType.PRO);
                sub.setTotalAmountPaid(800+now*250);
                subscriptionRepository.save(sub);
                return extra;
            }
            else{
                int now = sub.getNoOfScreensSubscribed();
                int extra = (1000 + 350*now)-(800+250*now);
                sub.setSubscriptionType(SubscriptionType.ELITE);
                sub.setTotalAmountPaid(1000+now*350);
                subscriptionRepository.save(sub);
                return extra;
            }
        }
        catch(Exception e){
            return -1;
        }
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        List<Subscription> sub = subscriptionRepository.findAll();
        int count = 0;
        for(int i=0;i<sub.size();i++){
            count+= sub.get(i).getTotalAmountPaid();
        }

        return count;
    }

}
