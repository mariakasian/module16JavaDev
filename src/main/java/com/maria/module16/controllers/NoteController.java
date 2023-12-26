package com.maria.module16.controllers;

import com.maria.module16.auth.AuthService;
import com.maria.module16.dto.NoteDto;
import com.maria.module16.entity.Note;
import com.maria.module16.exceptions.NoteNotFoundException;
import com.maria.module16.mapper.NoteMapper;
import com.maria.module16.service.NoteService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Validated
@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private AuthService authService;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private NoteService noteService;

    public NoteController(AuthService authService, NoteMapper noteMapper, NoteService noteService) {
        this.authService = authService;
        this.noteMapper = noteMapper;
        this.noteService = noteService;
    }

    @GetMapping(value = "/list")
    public ModelAndView noteList() {
        ModelAndView result = new ModelAndView("allNotes");
        result.addObject("notes",  noteService.listAll());
        return result;
    }

    @PostMapping(value = "/create")
    public ModelAndView createNote(
            @RequestParam(value="title") @Size(min = 3, max = 50) String title,
            @RequestParam(value="content") @Size(min = 3, max = 300) String content) {

        NoteDto noteDto = noteMapper.toNoteDto(title, content);
        noteDto.setUsername(authService.getUsername());
        Note entity = NoteMapper.toNoteEntity(noteDto);
        noteService.add(entity);
        return noteList();
    }

    @GetMapping(value = "/edit")
    public ModelAndView editNote(
            @NotEmpty @RequestParam(value="id") String id) throws NoteNotFoundException {
        ModelAndView result = new ModelAndView("updateNote");
        result.addObject("note", noteService.getById(Long.valueOf(id)));
        return result;
    }

    @PostMapping(value = "/edit")
    public ModelAndView updateNote(
            @NotEmpty @RequestParam(value="id") String id,
            @Size(min = 3, max = 50) @RequestParam(value="title") String title,
            @Size(min = 3, max = 300) @RequestParam(value="content") String content) throws NoteNotFoundException {

        noteService.update(Long.valueOf(id), title, content);
        return noteList();
    }

    @GetMapping(value = "/delete")
    @PostMapping(value = "/delete")
    public ModelAndView deleteNoteByPost(@NotEmpty @RequestParam(value="id") String id) throws NoteNotFoundException {
        noteService.deleteById(Long.valueOf(id));
        return noteList();
    }
}

