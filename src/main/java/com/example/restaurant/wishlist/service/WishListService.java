package com.example.restaurant.wishlist.service;

import com.example.restaurant.naver.NaverClient;
import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.wishlist.dto.WishListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {
    private final NaverClient naverClient;

    public  WishListDto search(String query){  // return이  WishListDto()이기 때문에 void ->  WishListDto

        // 지역검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);
        var searchLocalRes = naverClient.searchLocal(searchLocalReq);

        if(searchLocalRes.getTotal() > 0){
            var localItem = searchLocalRes.getItems().stream().findFirst().get(); //첫번째 item을 꺼낼거임. getTotal 자체가 0초과 있기 때문에 null 에러는 안남
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>",""); //이 item을 가지고 imageQuery를 만들거임.
                                                                                             // ImageQuery가 정확하게 동작하기 위해서, 정규식을 활용
                                                                                             // 이상한 문자열들을 없애주는 효과
            var searchImageReq = new SearchImageReq(); //ImageQuery를 가지고 이미지를 검색.
            searchImageReq.setQuery(imageQuery); // setQuery에 ImageQuery를 넣어줌

            // 이미지 검색, 이미지 쿼리를 가지고 또 네이버 통해서 결과 가져옴
            var searchImageRes = naverClient.searchImage(searchImageReq);

            if(searchImageRes.getTotal() > 0){ //null인지 체크
                var imageItem = searchImageRes.getItems().stream().findFirst().get();

                // 결과를 리턴 , 검색했을때 페이지에 나오는
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setReadAddress(localItem.getRoadAddress());
                result.setHomePageLink(localItem.getLink());
                result.setImageLink(imageItem.getLink());
                return result;
            }
        }
        return new WishListDto(); // 아무 결과도 없을 때는 return null;을 해도 되지만 빈 객체 만들어서 리턴해줌
    }
}

