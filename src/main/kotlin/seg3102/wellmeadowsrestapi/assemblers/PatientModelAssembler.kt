package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.controller.PatientController
import seg3102.wellmeadowsrestapi.representation.PatientRepresentation
import java.util.*

@Component
class PatientModelAssembler: RepresentationModelAssemblerSupport<Patient, PatientRepresentation>(PatientController::class.java, PatientRepresentation::class.java) {
    override fun toModel(entity: Patient): PatientRepresentation {
        val patientRepresentation = instantiateModel(entity)
        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PatientController::class.java)
                .getPatientById(entity.patientId))
            .withSelfRel())

        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PatientController::class.java)
                .getPatientContactById(entity.patientId))
            .withRel("patientContact"))

        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PatientController::class.java)
                .getPatientDivisionFileById(entity.patientId))
            .withRel("divisionAdmissionFile"))

        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PatientController::class.java)
                .getPatientHospitalFileById(entity.patientId))
            .withRel("hospitalAdmissionFile"))

        patientRepresentation.patientId = entity.patientId
        patientRepresentation.firstName = entity.firstName
        patientRepresentation.lastName = entity.lastName
        patientRepresentation.address = entity.address
        patientRepresentation.phoneNumber = entity.phoneNumber
        patientRepresentation.dateOfBirth = entity.dateOfBirth
        patientRepresentation.gender = entity.gender
        patientRepresentation.maritalStatus = entity.maritalStatus

        return patientRepresentation
    }
}