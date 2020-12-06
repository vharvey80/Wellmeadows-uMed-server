package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.controller.*
import seg3102.wellmeadowsrestapi.representation.*
import java.util.*

@Component
class DivisionModelAssembler: RepresentationModelAssemblerSupport<Division, DivisionRepresentation>(ApiController::class.java, DivisionRepresentation::class.java) {
    override fun toModel(entity: Division): DivisionRepresentation {
        val divisionRepresentation = instantiateModel(entity)
        divisionRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionById(entity.divisionId))
            .withSelfRel())

        divisionRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionPatientsById(entity.divisionId))
            .withRel("patients"))

        divisionRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionAdmissionFilesById(entity.divisionId))
            .withRel("divisionAdmissionFiles"))

        /*divisionRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionNurseById(entity.divisionId))
            .withRel("nurse"))*/

        divisionRepresentation.patients = toPatientsRepresentation(entity.patients)
        divisionRepresentation.divisionFiles = toFilesRepresentation(entity.divisionAdmissionFiles)

        divisionRepresentation.divisionId = entity.divisionId
        divisionRepresentation.divisionName = entity.divisionName
        divisionRepresentation.location = entity.location
        divisionRepresentation.numberOfBeds = entity.numberOfBeds
        divisionRepresentation.phoneExtension = entity.phoneExtension
        divisionRepresentation.status = entity.status

        return divisionRepresentation
    }

    fun toPatientsRepresentation(patients: List<Patient>): List<PatientNameRepresentation> {
        return if (patients.isEmpty()) Collections.emptyList() else patients
            .map{
                patientRepresentation(it)
            }
    }

    fun toFilesRepresentation(files: List<DivisionAdmissionFile>): List<DivisionAdmissionFileRepresentation> {
        return if (files.isEmpty()) Collections.emptyList() else files
            .map{
                filesRepresentation(it)
            }
    }

    private fun nurseRepresentation(nurse: Nurse): UserNameRepresentation {
        val representation = UserNameRepresentation()

        representation.firstName = nurse.firstName
        representation.lastName = nurse.lastName

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getNurseById(nurse.userId))
            .withSelfRel())
    }

    private fun patientRepresentation(patient: Patient): PatientNameRepresentation {
        val representation = PatientNameRepresentation()

        representation.firstName = patient.firstName
        representation.lastName = patient.lastName

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PatientController::class.java)
                .getPatientById(patient.patientId))
            .withSelfRel())
    }

    private fun filesRepresentation(file: DivisionAdmissionFile): DivisionAdmissionFileRepresentation {
        val representation = DivisionAdmissionFileRepresentation()

        representation.divisionFileId = file.divisionFileId
        representation.priority = file.priority
        representation.requestRationale = file.requestRationale

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFileById(file.divisionFileId))
            .withSelfRel())
    }
}