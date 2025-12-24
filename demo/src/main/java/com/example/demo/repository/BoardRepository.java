package com.example.demo.repository;


import com.example.demo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/* JPA 기능을 처리하는 인터페이스 */
/* JPA Repository 상속받아서 사용*/
/* JpaRepository <테이블명, id, type> */
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 일반 db 사용시 repository에서 할일은 없음
}
