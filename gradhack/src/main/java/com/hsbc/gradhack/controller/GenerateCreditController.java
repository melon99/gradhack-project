package com.hsbc.gradhack.controller;

import com.hsbc.gradhack.service.BigQueryService;
import com.hsbc.gradhack.service.CloudSQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateCreditController {

    @Autowired
    BigQueryService bigQueryService;

    @Autowired
    CloudSQLService cloudSQLService;

    @GetMapping("/test/bq")
    void testBQ(){
        try {
            bigQueryService.generateCredit("test");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/test/sql")
    void testSQL(){
        cloudSQLService.testSQL();
    }

    @GetMapping("/generate/credit")
    Double generateCredit(@RequestParam String name){
        try {
            return bigQueryService.generateCredit(name);
        } catch (InterruptedException e) {
            return 0D;
        }
    }

    @GetMapping("/check/credit")
    Double checkCredit(@RequestParam String name){

        return cloudSQLService.getUser(name).getCredit();
    }

    @GetMapping("/test/app")
    String testApp(){
        return "success";
    }


}
