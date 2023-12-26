package com.maria.module16.controllers;

import com.maria.module16.dto.NoteDto;
import com.maria.module16.entity.Note;
import com.maria.module16.exceptions.NoteNotFoundException;
import com.maria.module16.mapper.NoteMapper;
import com.maria.module16.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class NoteControllerTest {
    @Mock
    private NoteService authService;
    @Mock
    private NoteService noteService;
    @Mock
    private NoteMapper noteMapper;
    @InjectMocks
    private NoteController noteController;


    @BeforeEach
    void setUp() {
        openMocks(this);
        noteController = new NoteController(authService, noteMapper, noteService);
    }

    @Test
    void noteListTest() {
        List<Note> notes = Arrays.asList(new Note(), new Note());
        when(noteService.listAll()).thenReturn(notes);

        ModelAndView modelAndView = noteController.noteList();

        assertEquals("allNotes", modelAndView.getViewName());
        assertEquals(notes, modelAndView.getModel().get("notes"));
        verify(noteService, times(1)).listAll();
    }

    @Test
    void createNoteTest() {
        String title = "Test Title";
        String content = "Test Content";
        String username = "user";
        NoteDto noteDto = new NoteDto(1L, username, title, content);

        when(authService.getUsername()).thenReturn(username);
        when(noteMapper.toNoteDto(title, content)).thenReturn(noteDto);

        ModelAndView modelAndView = noteController.createNote(title, content);

        assertEquals("allNotes", modelAndView.getViewName());
        verify(noteService, times(1)).add(any(Note.class));
    }

    @Test
    void editNoteTest() throws NoteNotFoundException {
        String id = "1";
        Note note = new Note();
        when(noteService.getById(1L)).thenReturn(note);

        ModelAndView modelAndView = noteController.editNote(id);

        assertEquals("updateNote", modelAndView.getViewName());
        assertEquals(note, modelAndView.getModel().get("note"));
        verify(noteService, times(1)).getById(1L);
    }

    @Test
    void updateNoteTest() throws NoteNotFoundException {
        String id = "1";
        String title = "Updated Title";
        String content = "Updated Content";

        ModelAndView modelAndView = noteController.updateNote(id, title, content);

        assertEquals("allNotes", modelAndView.getViewName());
        verify(noteService, times(1)).update(1L, title, content);
    }

    @Test
    void deleteNoteByPostTest() throws NoteNotFoundException {
        String id = "1";

        ModelAndView modelAndView = noteController.deleteNoteByPost(id);

        assertEquals("allNotes", modelAndView.getViewName());
        verify(noteService, times(1)).deleteById(1L);
    }
}
