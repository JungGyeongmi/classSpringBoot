package org.zerock.club.contorller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.club.dto.NoteDTO;
import org.zerock.club.service.NoteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    
    @PreAuthorize("permitAll()")
    @PostMapping(value= "")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO){
        log.info("register......NoteDTO : "+noteDTO);
        Long num = noteService.register(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }
}
