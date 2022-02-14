package org.zerock.club.contorller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PreAuthorize("permitAll()")
    @GetMapping(value="/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteDTO> read(@PathVariable("num") Long num) {
        log.info("read........num:"+num);
        return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NoteDTO>> getList(String email){
        log.info("all........email : "+email);
        return new ResponseEntity<>(noteService.getAllWithWriter(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE) 
    public ResponseEntity<String> remove(@PathVariable ("num") Long num) {
        log.info("remove.........num"+num);
        
        noteService.remove(num);
        return new ResponseEntity<>("remove", HttpStatus.OK);
    }

    @PutMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE) 
    public ResponseEntity<String> modify(@RequestBody NoteDTO noteDTO){
        log. info ("modify.........."); 
        log.info(noteDTO);
        noteService.modify(noteDTO);
        return new ResponseEntity<>("modified", HttpStatus.OK); 
    }
}
