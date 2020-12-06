package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.PatientContact
import seg3102.wellmeadowsrestapi.controller.ContactController
import seg3102.wellmeadowsrestapi.representation.PatientContactRepresentation

@Component
class PatientContactModelAssembler: RepresentationModelAssemblerSupport<PatientContact, PatientContactRepresentation>(ContactController::class.java, PatientContactRepresentation::class.java) {
    override fun toModel(entity: PatientContact): PatientContactRepresentation {
        val contactRepresentation = instantiateModel(entity)
        contactRepresentation.add(linkTo(
            WebMvcLinkBuilder.methodOn(ContactController::class.java)
                .getContactById(entity.contactId))
            .withSelfRel())

        contactRepresentation.contactId = entity.contactId
        contactRepresentation.firstName = entity.firstName
        contactRepresentation.lastName = entity.lastName
        contactRepresentation.relationship = entity.relationship

        return contactRepresentation
    }
}