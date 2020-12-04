package com.hsbc.gradhack.service.impl;

import com.hsbc.gradhack.dao.CloudSQLDAO;
import com.hsbc.gradhack.domain.User;
import com.hsbc.gradhack.service.CloudSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloudSQLServiceImpl implements CloudSQLService {

    @Autowired
    CloudSQLDAO cloudSQLDAO;

    @Override
    public void testSQL() {
        cloudSQLDAO.testSQL().forEach(System.out::println);
    }

    @Override
    public User getUser(String name) {
        return cloudSQLDAO.getUser(name);
    }

    @Override
    public void updateCredit(Double credit, String name) {
        cloudSQLDAO.updateCredit(credit, name);
    }
}
