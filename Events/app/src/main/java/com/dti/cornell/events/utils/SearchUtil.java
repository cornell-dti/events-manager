package com.dti.cornell.events.utils;

import com.dti.cornell.events.models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jboss925 on 9/3/18.
 */

public class SearchUtil {

    public static List<Event> getEventsFromSearch(String search){
        List<Event> returnList = new ArrayList<>();
        for(Event e : Data.events()){
            if(eventIsRelatedToSearch(e, search)){
                returnList.add(e);
            }
        }
        return returnList;
    }


    public static boolean eventIsRelatedToSearch(Event e, String search){
        String searchLC = search.toLowerCase();
        if(e.title.toLowerCase().contains(searchLC)
                || e.description.toLowerCase().contains(searchLC)
                || e.location.toLowerCase().contains(searchLC)
                || Data.organizationForID.get(e.organizerID).name.toLowerCase().contains(searchLC)){
            return true;
        }
        for(Integer tagID : e.tagIDs){
            if(Data.tagForID.get(tagID).toLowerCase().contains(searchLC)){
                return true;
            }
        }
        return false;
    }


}
