package com.example.restaurant.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//db에서 사용할 Entity
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자 todtjd
@Data
public class MemoryDbEntity {
    protected Integer index;
}
