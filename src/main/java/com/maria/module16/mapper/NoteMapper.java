package com.maria.module16.mapper;

import com.maria.module16.dto.NoteDto;
import com.maria.module16.entity.Note;
import com.maria.module16.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoteMapper {
    public NoteDto toNoteDto(Note entity) {
        NoteDto noteDto = new NoteDto();
        noteDto.setId(entity.getId());
        noteDto.setUsername(entity.getUser().getUsername());
        noteDto.setTitle(entity.getTitle());
        noteDto.setContent(entity.getContent());
        return noteDto;
    }

    public static Note toNoteEntity(NoteDto noteDto) {
        Note entity = new Note();
        entity.setId(noteDto.getId());
        entity.setUser(new User(noteDto.getUsername()));
        entity.setTitle(noteDto.getTitle());
        entity.setContent(noteDto.getContent());
        return entity;
    }
    public NoteDto toNoteDto(String title, String content) {
        NoteDto noteDto = new NoteDto();
        noteDto.setTitle(title);
        noteDto.setContent(content);
        return noteDto;
    }

    public static List<Note> toNoteEntities(List<NoteDto> noteDtos) {
        List<Note> list = new ArrayList<>();
        for (NoteDto noteDto : noteDtos) {
            Note note = toNoteEntity(noteDto);
            list.add(note);
        }
        return list;
    }
}
