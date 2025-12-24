package com.example.demo.service;


import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.BoardFileDTO;
import com.example.demo.dto.FileDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.File;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    @Override
    public Long insert(BoardFileDTO boardFileDTO) {
        BoardDTO boardDTO = boardFileDTO.getBoardDTO();

        Long bno = boardRepository.save(convertDtoToEntity(boardDTO)).getBno();
        List<FileDTO> fileDTOList = boardFileDTO.getFileDTOList();
        if(bno > 0 && fileDTOList != null) {
            for(FileDTO fileDTO : fileDTOList) {
                fileDTO.setBno(bno);
                bno = fileRepository.save(converDtoToEntity(fileDTO)).getBno();
            }
        }
        return bno;
    }



    @Override
    public Long insert(BoardDTO boardDTO) {
        // save() : 저장
        // 저장 객체는 Entity (Board)
        // DTO => Entity 로 변환
        Board board = convertDtoToEntity(boardDTO);
        Long bno = boardRepository.save(board).getBno();
        return bno;
    }

    @Override
    public Page<BoardDTO> getList(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.by("bno").descending());
        Page<Board> pageList = boardRepository.findAll(pageable);
        Page<BoardDTO> boardDTOPage = pageList.map(this::convertEntityToDto);
                return boardDTOPage;

    }

    @Transactional
    @Override
    public BoardFileDTO getDetail(long bno) {
        Optional<Board> optional = boardRepository.findById(bno);
        if(optional.isPresent()) {
            Board board = optional.get();
            board.setReadCount(board.getReadCount()+1);

            BoardDTO boardDTO = convertEntityToDto(board);
            
            // file 가져오기 - bno와 일치하는 파일 가져오기
            List<File> fileList = fileRepository.findByBno(bno);
            List<FileDTO> fileDTOList = fileList.stream()
                    .map(this::converEntityToDto)
                    .toList();
            BoardFileDTO boardFileDTO = new BoardFileDTO(boardDTO, fileDTOList);
        log.info(">>>>boardFileDTO{}",boardFileDTO);
            return boardFileDTO;

        }
        return null;
    }

//    @Override
//    public BoardDTO getDetail(long bno) {
//        /* findOne => 기본키를 이요ㅕㅇ하여 원하는 객체 검색 where ~
//        *  findBy 칼럼명 => 원하는 칼럼명을 이용하여 검색
//        *  findbyId = findOne
//        * Optional<T> : NullPointException이 발생하지 않도록 도와줌
//        * Optional.isEmptyt() : null 일 경우 true / false
//        *  optional.isPresent() : 값이 있는지를 확인 true, false
//        *  Optional.get() : 객체 가져오기
//        *
//        *
//        * */
//        Optional<Board> optional = boardRepository.findById(bno);
//        if(optional.isPresent()) {
//            Board board = optional.get();
//            boardReadCountUpdate(board, 1);
//            BoardDTO boardDTO = convertEntityToDto(board);
//            return boardDTO;
//        }
//        return null;
//    }

    @Override
    public Long modify(BoardDTO boardDTO) {
        /* optional.orElseThrow(()-> new EntityNotFoundExcetion("존재하지 않는 게시글")
        *
        *
        * */

        Board board = boardRepository.findById(boardDTO.getBno())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글"));
                board.setTitle(boardDTO.getTitle());
                board.setContent(boardDTO.getContent());
                boardReadCountUpdate(board, -1);
        return boardDTO.getBno();
    }

    @Override
    public void remove(long bno) {
        boardRepository.deleteById(bno);
    }
    /*
    * save() => id가 없으면 insert / id가 없으면 update
    * EntityNotFoundException : where 값이 존재하지 않을경우 발생
    * 정보 유실 가능성이 커짐
    * dirty checking 미작동 가능성이 커짐
    * 변동감지(dirty checking)
    * findById(bno) 를 통해 먼저 조회 => 영속 상태를 만든 후 수정 => save()
    * @Transactional 이 변동감지함 (dirty checking) / save() 없이도 update가 가능함
    * dirty checking ( 더티 체킹 ) = 변동 감지
    * 엔티티가 영속성 컨텍스트에 올라가 있는 상태 (영속 상태)
    * 해당 객체의 필드가 변경되면, 트랜잭션이 종료되기 전에 JPA가 변경한 부분만 자동감지하여, 업데이트 쿼리를 실행
    * SAVE()가 없어도 (명시적으로 호출하지 않아도) 수정된 필드를 DB에 자동 반영
    * */

//    @Override
//    public Long modify(BoardDTO boardDTO) {
//        //업데이트 기능은 없음.
//        //get으로 데이터 가져와서 findbyId를 사용해서 가져온다. => 내 객체에서 변경값만 수정 => save()
//        Optional<Board> optional = boardRepository.findById(boardDTO.getBno());
//        if(optional.isPresent()) {
//            Board board = optional.get();
//            board.setTitle(boardDTO.getTitle());
//            board.setContent(boardDTO.getContent());
//
//            boardRepository.save(board);
//            boardReadCountUpdate(board, 1);
//            return board.getBno();
//        }
//
//        return 0L;
//    }





    private void boardReadCountUpdate(Board board, int i) {
        int count = board.getReadCount() +1;
        board.setReadCount(board.getReadCount()+i);
        boardRepository.save(board);
    }


//    @Override
//    public List<BoardDTO> getList(int pageNo) {
//        /* DB에서 가져오는 RETURN LIST BOARD => List<BoardDTO> 로 변환
//        * findAll() => 전체 값 리턴
//        * select * from order by bno desc
//        * 정렬 : Sort.by(Sort.direction.DESC, "정렬기준 칼럼명")
//        * */
//        List<Board> boardList = boardRepository.findAll(
//                Sort.by(Sort.Direction.DESC, "bno"));
//        List<BoardDTO> boardDTOList = boardList
//                .stream()
//                .map(board -> convertEntityToDto(board))
//                .toList();
//        return boardDTOList;
//    }
}
