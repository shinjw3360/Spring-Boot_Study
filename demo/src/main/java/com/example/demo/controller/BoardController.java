package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.BoardFileDTO;
import com.example.demo.dto.FileDTO;
import com.example.demo.handler.FileHandler;
import com.example.demo.handler.PageHandler;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Controller
public class BoardController {
    private final BoardService boardService;
    private final FileHandler fileHandler;

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String register(BoardDTO boardDTO,
                           @RequestParam(name="files", required = false) MultipartFile[] files){
        /**/
        List<FileDTO> fileList = null;
        if(files != null && files[0].getSize() > 0) {
            //핸들러 호출
        fileList = fileHandler.uploadFile(files);
            log.info(">>>> fileList id >> {}", fileList);
        }
        BoardFileDTO boardFileDTO = new BoardFileDTO(boardDTO, fileList);
        Long bno = boardService.insert(boardFileDTO);
        log.info(">>>> boardFileDTO id >> {}", boardFileDTO);
        return "redirect:/";
    }

//    @GetMapping("/list")
//    public void list(Model model){
    ////        페이징 없는 리스트
//        List<BoardDTO> list = boardService.getList();
//        model.addAttribute("list", list);
//    }

    @GetMapping("/list")
    public void list(Model model,
                     @RequestParam(name="pageNo", defaultValue = "1", required = false) int pageNo){
        // select * from board order by bno desc limit 0,10;
        //pageNo = 1; // 3page
        Page<BoardDTO> list = boardService.getList(pageNo);
        PageHandler<BoardDTO> pageHandler = new PageHandler<>(list, pageNo);
        model.addAttribute("ph", pageHandler);
    }

    @GetMapping("detail")
    public void detail(@RequestParam("bno") long bno, Model model)  {
        BoardFileDTO boardFileDTO = boardService.getDetail(bno);
        model.addAttribute("boardFileDTO", boardFileDTO);
    }

    @PostMapping("modify")
    public String modify(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        Long bno = boardService.modify(boardDTO);
        redirectAttributes.addAttribute("bno", boardDTO.getBno());
        return "redirect:/board/detail";
    }
    @GetMapping("/remove")
    public String remove(@RequestParam("bno") long bno) {
        boardService.remove(bno);
        return "redirect:/board/list";
    }
}