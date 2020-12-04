package com.hsbc.gradhack.service;

import com.hsbc.gradhack.domain.User;

public interface CloudSQLService {

    void testSQL();
    User getUser(String name);
    void updateCredit(Double credit, String name);

}
