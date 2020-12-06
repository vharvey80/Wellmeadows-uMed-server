package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3102.wellmeadowsrestapi.assemblers.PatientContactModelAssembler
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.entities.PatientContact
import seg3102.wellmeadowsrestapi.representation.PatientContactRepresentation
import seg3102.wellmeadowsrestapi.representation.PatientRepresentation
import seg3102.wellmeadowsrestapi.services.ContactServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class ContactController {

    @Autowired
    val contactServices = ContactServiceImp()

    @Autowired
    val contactAssembler = PatientContactModelAssembler()

    @Operation(summary = "Get all Contacts")
    @GetMapping("/contacts")
    fun getContacts(): ResponseEntity<CollectionModel<PatientContactRepresentation>> {
        val contacts = contactServices.getContacts()
        return ResponseEntity(
            contactAssembler.toCollectionModel(contacts),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Contact by his id")
    @GetMapping("/contacts/{id}")
    fun getContactById(@PathVariable("id") id: Long) : ResponseEntity<PatientContactRepresentation> {
        val contact = contactServices.getContactById(id)
        return contact
            .map { entity: PatientContact -> contactAssembler.toModel(entity) }
            .map { body: PatientContactRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Contact by his id")
    @DeleteMapping("/contacts/delete/{id}")
    fun deleteContactById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            contactServices.deleteContact(id)
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Contacts in DB")
    @DeleteMapping("/patients/delete")
    fun deleteContacts(): ResponseEntity<Any> {
        return try {
            contactServices.deleteAllContacts()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Contact")
    @PostMapping("/contacts")
    fun addContact(@RequestBody contact: PatientContact): ResponseEntity<Any> {
        return try {
            val newContact = contactServices.addContact(contact)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newContact.contactId)
                .toUri()
            ResponseEntity.created(location).body(contactAssembler.toModel(newContact))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Contact")
    @PutMapping("/contacts/{id}")
    fun updatePatient(@PathVariable("id") id: Long, @RequestBody contact: PatientContact): ResponseEntity<Any> {
        return try {
            contactServices.updateContact(id, contact)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}