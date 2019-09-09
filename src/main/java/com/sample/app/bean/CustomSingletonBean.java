package com.sample.app.bean;

import org.springframework.stereotype.Component;

@Component
public class CustomSingletonBean {

    public boolean amIAlive() {
        return true;
    }

}
