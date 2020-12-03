package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.controller.*
import seg3102.wellmeadowsrestapi.representation.*
import java.util.*

@Component
class DivisionAdmissionFileModelAssembler: RepresentationModelAssemblerSupport<DivisionAdmissionFile, DivisionAdmissionFileRepresentation>(ApiController::class.java, DivisionAdmissionFileRepresentation::class.java) {
    override fun toModel(entity: DivisionAdmissionFile): DivisionAdmissionFileRepresentation {
        val divisionFileRepresentation = instantiateModel(entity)
        divisionFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFileById(entity.divisionFileId))
            .withSelfRel())


        divisionFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFileDoctorById(entity.divisionFileId))
            .withRel("doctor"))

        divisionFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFileDivisionById(entity.divisionFileId))
            .withRel("division"))

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

        hospitalFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getHospitalFileDoctorById(entity.hospitalFileId))
            .withRel("doctor"))

        hospitalFileRepresentation.hospitalFileId = entity.hospitalFileId
        hospitalFileRepresentation.bedNumber = entity.bedNumber
        hospitalFileRepresentation.privateInsuranceNumber = entity.privateInsuranceNumber

        return hospitalFileRepresentation
    }
}