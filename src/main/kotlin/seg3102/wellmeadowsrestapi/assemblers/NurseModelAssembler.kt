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
class NurseModelAssembler: RepresentationModelAssemblerSupport<Nurse, NurseRepresentation>(ApiController::class.java, NurseRepresentation::class.java) {
    override fun toModel(entity: Nurse): NurseRepresentation {
        val nurseRepresentation = instantiateModel(entity)
        nurseRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getNurseById(entity.userId))
            .withSelfRel())

        nurseRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getNurseDivisionById(entity.userId))
            .withRel("division"))

        nurseRepresentation.division = entity.division?.let { divisionRepresentation(it) }!!

        nurseRepresentation.phoneExtension = entity.phoneExtension

        nurseRepresentation.userId = entity.userId
        nurseRepresentation.firstName = entity.firstName
        nurseRepresentation.lastName = entity.lastName
        nurseRepresentation.email = entity.email
        nurseRepresentation.password = entity.password
        nurseRepresentation.phoneNumber = entity.phoneNumber

        return nurseRepresentation
    }

    private fun divisionRepresentation(division: Division): DivisionRepresentation {
        val representation = DivisionRepresentation()

        representation.divisionId = division.divisionId
        representation.divisionName = division.divisionName
        representation.location = division.location
        representation.numberOfBeds = division.numberOfBeds

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionById(division.divisionId))
            .withSelfRel())
    }
}