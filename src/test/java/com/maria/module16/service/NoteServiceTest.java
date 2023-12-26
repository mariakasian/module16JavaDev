package com.maria.module16.service;

import com.maria.module16.entity.Note;
import com.maria.module16.exceptions.NoteNotFoundException;
import com.maria.module16.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNoteTest() {
        Note note = new Note();
        when(noteRepository.save(any())).thenReturn(note);

        Note addedNote = noteService.add(note);

        assertNotNull(addedNote);
        verify(noteRepository, times(1)).save(any());
    }

    @Test
    void listAllNotesTest() {
        Note note1 = new Note();
        Note note2 = new Note();
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        List<Note> notes = noteService.listAll();

        assertNotNull(notes);
        assertEquals(2, notes.size());
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void getByIdNoteFoundTest() throws NoteNotFoundException {
        Long noteId = 1L;
        Note note = new Note();
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        Note foundNote = noteService.getById(noteId);

        assertNotNull(foundNote);
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    void getByIdNoteNotFoundTest() {
        Long noteId = 1L;
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.getById(noteId));
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    void updateNoteTest() throws NoteNotFoundException {
        Long noteId = 1L;
        Note note = new Note();
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));
        when(noteRepository.save(any())).thenReturn(note);

        noteService.update(noteId, "New Title", "New Content");

        assertEquals("New Title", note.getTitle());
        assertEquals("New Content", note.getContent());
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).save(any());
    }

    @Test
    void deleteByIdNoteFoundTest() throws NoteNotFoundException {
        Long noteId = 1L;
        Note note = new Note();
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(note));

        noteService.deleteById(noteId);

        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).deleteById(noteId);
    }

    @Test
    void deleteByIdNoteNotFoundTest() {
        Long noteId = 1L;
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.deleteById(noteId));
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, never()).deleteById(noteId);
    }
}
