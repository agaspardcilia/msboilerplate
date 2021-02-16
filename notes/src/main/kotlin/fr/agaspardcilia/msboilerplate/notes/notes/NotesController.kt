package fr.agaspardcilia.msboilerplate.notes.notes

import fr.agaspardcilia.msboilerplate.common.security.Permission
import fr.agaspardcilia.msboilerplate.common.security.annotations.PermissionRequired
import fr.agaspardcilia.msboilerplate.notes.notes.dto.NoteCreationDto
import fr.agaspardcilia.msboilerplate.notes.notes.dto.NoteUpdateDto
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notes")
class NotesController(
    private val notesService: NotesService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{id}")
    @PermissionRequired(Permission.NOTES_GET)
    fun get(@PathVariable id: String): Note {
        log.info("Getting a Note with id '$id'")

        return notesService.get(id)
    }

    @GetMapping
    @PermissionRequired(Permission.NOTES_GET)
    fun getAll(pageable: Pageable): Page<Note> {
        log.info("Getting Note page '${pageable.pageNumber}' of size '${pageable.pageSize}'")

        return notesService.getAll(pageable)
    }

    @PostMapping
    @PermissionRequired(Permission.NOTES_CREATE)
    fun create(@RequestBody note: NoteCreationDto): Note {
        log.info("Creating a new note")

        return notesService.create(note)
    }

    @PutMapping
    @PermissionRequired(Permission.NOTES_UPDATE)
    fun update(@RequestBody note: NoteUpdateDto): Note {
        log.info("Updating a Note with id '${note.id}'")

        return notesService.update(note)
    }

    @DeleteMapping("/{id}")
    @PermissionRequired(Permission.NOTES_DELETE)
    fun delete(@PathVariable id: String) {
        log.info("Delete Note with id '$id'")

        return notesService.delete(id)
    }
}
