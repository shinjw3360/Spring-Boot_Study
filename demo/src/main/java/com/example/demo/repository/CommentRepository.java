package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    /* findBy** => ** 테이블 안에 있는 모든 칼럼
    *   select * from comment where ** =?
    *
    * */
//    List<Comment> findByBno(Long bno);

    Page<Comment> findByBno(long bno, Pageable pageble);

}
