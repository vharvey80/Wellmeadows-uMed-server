package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.Prescription
import seg3102.wellmeadowsrestapi.controller.PrescriptionController
import seg3102.wellmeadowsrestapi.representation.PrescriptionRepresentation
import java.util.*

@Component
class PrescriptionModelAssembler: RepresentationModelAssemblerSupport<Prescription, PrescriptionRepresentation>(PrescriptionController::class.java, PrescriptionRepresentation::class.java) {
    override fun toModel(entity: Prescription): PrescriptionRepresentation {
        val prescriptionRepresentation = instantiateModel(entity)
        prescriptionRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PrescriptionController::class.java)
                .getPrescriptionById(entity.medicationId))
            .withSelfRel())

        prescriptionRepresentation.medicationId = entity.medicationId
        prescriptionRepresentation.medicationName = entity.medicationName
        prescriptionRepresentation.methodOfAdmin = entity.methodOfAdmin
        prescriptionRepresentation.startDate = entity.startDate
        prescriptionRepresentation.unitsByDay = entity.unitsByDay

        return prescriptionRepresentation
    }
}