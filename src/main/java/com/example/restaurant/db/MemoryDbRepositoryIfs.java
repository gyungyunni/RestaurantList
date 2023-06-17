package com.example.restaurant.db;

import java.util.List;
import java.util.Optional;

public interface MemoryDbRepositoryIfs<T> {
    //해당 타입에 대해서 Optional하게 값을 리턴해주는 findById , index 받아서 해당 데이터 리턴하는
    Optional<T> findById(int index);
    T save(T entity); // 저장하는 save 메서드
    void deleteById(int index); //삭제하는 메서드
    List<T> listAll(); //전체를 리턴시키는 메서드
}
