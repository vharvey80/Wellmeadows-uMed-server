package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.controller.*
import seg3102.wellmeadowsrestapi.representation.*

import java.util.*

@Component
class PatientContactModelAssembler: RepresentationModelAssemblerSupport<PatientContact, PatientContactRepresentation>(ApiController::class.java, PatientContactRepresentation::class.java) {
    override fun toModel(entity: PatientContact): PatientContactRepresentation {
        val contactRepresentation = instantiateModel(entity)
        contactRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientContactById(entity.contactId))
            .withSelfRel())

        /*contactRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getContactPatientById(entity.contactId))
            .withRel("patient"))*/

        contactRepresentation.firstName = entity.firstName
        contactRepresentation.lastName = entity.lastName
        contactRepresentation.relationship = entity.relationship

        return contactRepresentation
    }

    private fun patientRepresentation(patient: Patient): PatientNameRepresentation {
        val representation = PatientNameRepresentation()

        representation.firstName = patient.firstName
        representation.lastName = patient.firstName

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PatientController::class.java)
                .getPatientById(patient.patientId))
            .withSelfRel())
    }
}