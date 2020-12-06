package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.controller.*
import seg3102.wellmeadowsrestapi.representation.*

@Component
class DivisionAdmissionFileModelAssembler: RepresentationModelAssemblerSupport<DivisionAdmissionFile, DivisionAdmissionFileRepresentation>(DivisionAdmissionFileController::class.java, DivisionAdmissionFileRepresentation::class.java) {
    override fun toModel(entity: DivisionAdmissionFile): DivisionAdmissionFileRepresentation {
        val divisionFileRepresentation = instantiateModel(entity)
        divisionFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(DivisionAdmissionFileController::class.java)
                .getDivisionAdmissionFileById(entity.divisionFileId))
            .withSelfRel())

        divisionFileRepresentation.divisionFileId = entity.divisionFileId
        divisionFileRepresentation.requestRationale = entity.requestRationale
        divisionFileRepresentation.priority = entity.priority

        return divisionFileRepresentation
    }
}

@Component
class HospitalAdmissionFileModelAssembler: RepresentationModelAssemblerSupport<HospitalAdmissionFile, HospitalAdmissionFileRepresentation>(HospitalAdmissionFileController::class.java, HospitalAdmissionFileRepresentation::class.java) {
    override fun toModel(entity: HospitalAdmissionFile): HospitalAdmissionFileRepresentation {
        val hospitalFileRepresentation = instantiateModel(entity)
        hospitalFileRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(HospitalAdmissionFileController::class.java)
                .getHospitalAdmissionFileById(entity.hospitalFileId))
            .withSelfRel())

        hospitalFileRepresentation.hospitalFileId = entity.hospitalFileId
        hospitalFileRepresentation.bedNumber = entity.bedNumber
        hospitalFileRepresentation.privateInsuranceNumber = entity.privateInsuranceNumber

        return hospitalFileRepresentation
    }
}