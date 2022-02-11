package org.zerock.club.service;

import java.util.List;

import org.zerock.club.dto.NoteDTO;
import org.zerock.club.entity.ClubMember;
import org.zerock.club.entity.Note;

public interface NoteService {
    Long register(NoteDTO NoteDTO);
    NoteDTO get(Long num);
    void modify(NoteDTO noteDTO);
    void remove(Long num);
    
    List<NoteDTO>getAllWithWriter(String wirterEmail);

    /* default 메소드 */
    default Note dtoToEntity(NoteDTO noteDTO){

        Note note = Note.builder()
        .num(noteDTO.getNum())
        .title(noteDTO.getTitle())
        .content(noteDTO.getContent())
        .writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build())
        .build();

        return note;
    }

    default NoteDTO entityToDTO(Note note) {
        NoteDTO noteDTO = NoteDTO.builder()
        .num(note.getNum())
        .title(note.getTitle())
        .content(note.getContent())
        .writerEmail(note.getWriter().getEmail())
        .regDate(note.getRegDate())
        .modDate(note.getModDate())
        .build();

        return noteDTO;
    }
}
