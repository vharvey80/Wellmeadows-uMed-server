package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.Nurse
import seg3102.wellmeadowsrestapi.controller.NurseController
import seg3102.wellmeadowsrestapi.representation.NurseRepresentation

@Component
class NurseModelAssembler: RepresentationModelAssemblerSupport<Nurse, NurseRepresentation>(NurseController::class.java, NurseRepresentation::class.java) {
    override fun toModel(entity: Nurse): NurseRepresentation {
        val nurseRepresentation = instantiateModel(entity)
        nurseRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(NurseController::class.java)
                .getNurseById(entity.userId))
            .withSelfRel())

        nurseRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(NurseController::class.java)
                .getNurseDivisionById(entity.userId))
            .withRel("division"))

        nurseRepresentation.phoneExtension = entity.phoneExtension

        nurseRepresentation.userId = entity.userId
        nurseRepresentation.firstName = entity.firstName
        nurseRepresentation.lastName = entity.lastName
        nurseRepresentation.email = entity.email
        nurseRepresentation.password = entity.password
        nurseRepresentation.phoneNumber = entity.phoneNumber

        return nurseRepresentation
    }
}