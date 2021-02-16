package fr.agaspardcilia.msboilerplate.notes.notes

import org.springframework.stereotype.Service
import fr.agaspardcilia.msboilerplate.common.exception.ApiNotFoundException
import fr.agaspardcilia.msboilerplate.notes.notes.dto.NoteCreationDto
import fr.agaspardcilia.msboilerplate.notes.notes.dto.NoteUpdateDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Service
class NotesService(private val noteRepository: NoteRepository) {

    fun get(id: String): Note =
        noteRepository.findById(id)
            .orElseThrow { ApiNotFoundException("Unable to find Note with id '$id'") }


    fun getAll(pageable: Pageable): Page<Note> =
        noteRepository.findAll(pageable)

    fun create(note: NoteCreationDto): Note =
        noteRepository.save(
            note.let {
                Note(
                    title = it.title,
                    content = it.content
                )
            }
        )

    fun update(note: NoteUpdateDto): Note =
        noteRepository.save(
            get(note.id).apply {
                this.title = note.title
                this.content = note.content
            }
        )

    fun delete(id: String) {
        // Make sure the entity exists
        get(id)

        noteRepository.deleteById(id)
    }
}
