package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seg3102.wellmeadowsrestapi.assemblers.PatientContactModelAssembler
import seg3102.wellmeadowsrestapi.representation.PatientContactRepresentation
import seg3102.wellmeadowsrestapi.services.ContactServiceImp

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api/contacts", produces = ["application/hal+json"])
class ContactController {

    @Autowired
    val contactServices = ContactServiceImp()

    @Autowired
    val contactAssembler = PatientContactModelAssembler()

    @Operation(summary = "Get all Contacts")
    @GetMapping("")
    fun getContacts(): ResponseEntity<CollectionModel<PatientContactRepresentation>> {
        val contacts = contactServices.getContacts()
        return ResponseEntity(
            contactAssembler.toCollectionModel(contacts),
            HttpStatus.OK)
    }
}