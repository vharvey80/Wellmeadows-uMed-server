package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.controller.*
import seg3102.wellmeadowsrestapi.representation.*

@Component
class DivisionAdmissionFileModelAssembler: RepresentationModelAssemblerSupport<DivisionAdmissionFile, DivisionAdmissionFileRepresentation>(ApiController::class.java, DivisionAdmissionFileRepresentation::class.java) {
    override fun toModel(entity: DivisionAdmissionFile): DivisionAdmissionFileRepresentation {
        val divisionFileRepresentation = instantiateModel(entity)
        divisionFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFileById(entity.divisionFileId))
            .withSelfRel())

        /*divisionFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionPatientsById(entity.divisionFileId))
            .withRel("patient"))*/

        /*divisionFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFileDivisionById(entity.divisionFileId))
            .withRel("division"))*/

        divisionFileRepresentation.divisionFileId = entity.divisionFileId
        divisionFileRepresentation.requestRationale = entity.requestRationale
        divisionFileRepresentation.priority = entity.priority

        return divisionFileRepresentation
    }
}

@Component
class HospitalAdmissionFileModelAssembler: RepresentationModelAssemblerSupport<HospitalAdmissionFile, HospitalAdmissionFileRepresentation>(ApiController::class.java, HospitalAdmissionFileRepresentation::class.java) {
    override fun toModel(entity: HospitalAdmissionFile): HospitalAdmissionFileRepresentation {
        val hospitalFileRepresentation = instantiateModel(entity)
        hospitalFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getHospitalFileById(entity.hospitalFileId))
            .withSelfRel())

        /*hospitalFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getHospitalFilePatientById(entity.hospitalFileId))
            .withRel("patient"))*/

        hospitalFileRepresentation.hospitalFileId = entity.hospitalFileId
        hospitalFileRepresentation.bedNumber = entity.bedNumber
        hospitalFileRepresentation.privateInsuranceNumber = entity.privateInsuranceNumber

        return hospitalFileRepresentation
    }
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

private fun divisionRepresentation(division: Division): DivisionNameRepresentation {
    val representation = DivisionNameRepresentation()

    representation.divisionName = division.divisionName
    representation.location = division.location

    return representation.add(WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(ApiController::class.java)
            .getDivisionById(division.divisionId))
        .withSelfRel())
}