package fr.agaspardcilia.msboilerplate.notes.notes

import org.springframework.data.mongodb.repository.MongoRepository

interface NoteRepository:MongoRepository<Note, String>
