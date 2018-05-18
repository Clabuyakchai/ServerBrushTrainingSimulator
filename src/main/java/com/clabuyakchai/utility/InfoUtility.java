package com.clabuyakchai.utility;

import org.springframework.stereotype.Component;

@Component
public class InfoUtility {
    public InfoUtility() {

    }

    public String getInfo(){
        return "Server by clabuyakchai";
    }
}
