package com.example.demo.service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    default Comment converDtoToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .cno(commentDTO.getCno())
                .bno(commentDTO.getBno())
                .writer(commentDTO.getWriter())
                .content(commentDTO.getContent())
                .build();
    }

    default CommentDTO convertEntityToDto(Comment comment) {
        return CommentDTO.builder()
                .cno(comment.getCno())
                .bno(comment.getBno())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .regDate(comment.getRegDate())
                .modDate(comment.getModDate())
                .build();
    }

    long post(CommentDTO commentDTO);

//    List<CommentDTO> getList(Long bno);

    @Transactional
    long modify(CommentDTO commentDTO);

    void remove(Long cno);

    Page<CommentDTO> getList(Long bno, int page);
}