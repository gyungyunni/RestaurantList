package com.example.restaurant.wishlist.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishListServiceTest {

    @Autowired
    private WishListService wishListService;

    @Test
    public void searchTest(){
        var result = wishListService.search("갈비집"); //이미 NaverAPI는 잘 동작하는거 테스트 했으므로 wishListService만 잘되면 됨
        System.out.println(result);
        Assertions.assertNotNull(result);

    }
}