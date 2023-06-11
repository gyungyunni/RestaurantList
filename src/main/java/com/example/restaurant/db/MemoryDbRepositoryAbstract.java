package com.example.restaurant.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T> {

    private  final List<T> db = new ArrayList<>(); // data를 저장할 ArrayList
    private  int index = 0; // 만들 db에서 pk에 해당하는 index


    // 상속 받은거 오버라이딩
    @Override
    public Optional<T> findById(int index) {
        return db.stream().filter(it -> it.getIndex() == index).findFirst(); //filter란 것은 db.stream에 들어있는 타입<T> 에 대한 부분
                                                                             //getIndex는 MemoryDbEntity에 정의된 index
                                                                             // <T extends MemoryDbEntity> 이렇게 와일드카드를 걸었기 때문에 제네릭 타입에 해당하는 변수에 대해서 getIndex로 접근이 가능
                                                                             // MemoryDbEntity를 상속받으면 모두 index라는 변수를 갖고 있기 때문에 getIndex를 통해서 db에서 해당 인덱스에 해당하는 데이터를 찾아서 첫뻔쨰(findfirst)값을 찾을 수 있음
                                                                             // 있을 수도 있고 없을 수도 있는 데이터 리턴
    }

    // 데이터를 받아서 밀어 넣어주면 됨
    // 두가지 케이스 - db에 이미 데이터가 있는 경우 , 없는 경우
    @Override
    public T save(T entity) {
        // db에 인덱스가 찾고자 하는 entity의 getIndex와 동일하면 이미 있는 케이스, 없으면 없는 케이스
        var optionalEntity = db.stream().filter(it -> it.getIndex() == entity.getIndex()).findFirst();

        if(optionalEntity.isEmpty()){
            //DB에 데이터가 없는 경우 - 해당값이 비어있으면
            index++;
            entity.setIndex(index); //인덱스 1 올리고 데이터를 setting 하고
            db.add(entity); //db에 저장
            return entity; // 타입을 리턴
        } else{ //db에 데이터가 있는 경우, 업데이트 해줘야함
            var preIndex = optionalEntity.get().getIndex(); // 사전에 이미 있던 데이터의 인덱스를 가져와서
            entity.setIndex(preIndex); // 새로운 데이터의 setIndex를 통해 해당값을 세팅
            deleteById(preIndex); // 기존에 있는 데이터를 지우고
            db.add(entity); //새로 받은 엔티티를 넣어줌
            return entity;
        }
    }

    @Override
    public void deleteById(int index) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        optionalEntity.ifPresent(db::remove);
        /*
         if(optionalEntity.isPresent()){ //데이터가 이미 있는 경우
            db.remove(optionalEntity.get()); //remove라는 메서드를 통해 지우도록함
         }
        }
         */
    }

    //db를 바로 return 시키면 됨
    @Override
    public List<T> listAll() {
        return db;
    }
}
