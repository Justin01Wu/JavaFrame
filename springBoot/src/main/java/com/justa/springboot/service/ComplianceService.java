package com.justa.springboot.service;

import org.springframework.beans.factory.annotation.Value;

public class ComplianceService {

    private String[] userList;
    private static final String SetName = "${compliance.user.list}";

    // to do unit test, we inject value on constructor
    public ComplianceService(@Value(SetName) String users ) {
        if(users == null){
            String msg = String.format("Can't find %s in config.properties", SetName);
            throw new IllegalStateException(msg);
        }
        userList = users.split(",");
    }

    protected boolean isCompliantUw(final String uwFullName) {
        if ((null==uwFullName) || uwFullName.isEmpty()) {
            return false;
        }
        for (String user : this.userList) {
            if (uwFullName.contains(user)) {
                return true;
            }
        }
        return false;
    }


}

