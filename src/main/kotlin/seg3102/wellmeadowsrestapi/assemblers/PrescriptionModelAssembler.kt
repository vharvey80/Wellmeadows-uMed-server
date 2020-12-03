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
class PrescriptionModelAssembler: RepresentationModelAssemblerSupport<Prescription, PrescriptionRepresentation>(ApiController::class.java, PrescriptionRepresentation::class.java) {
    override fun toModel(entity: Prescription): PrescriptionRepresentation {
        val prescriptionRepresentation = instantiateModel(entity)
        prescriptionRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPrescriptionById(entity.medicationId))
            .withSelfRel())

        prescriptionRepresentation.patients = toPatientsRepresentation(entity.patients)
        prescriptionRepresentation.doctors = toDoctorsRepresentation(entity.doctors)

        prescriptionRepresentation.medicationId = entity.medicationId
        prescriptionRepresentation.medicationName = entity.medicationName
        prescriptionRepresentation.methodOfAdmin = entity.methodOfAdmin
        prescriptionRepresentation.startDate = entity.startDate
        prescriptionRepresentation.unitsByDay = entity.unitsByDay

        return prescriptionRepresentation
    }

    private fun toPatientsRepresentation(patients: List<Patient>): List<PatientNameRepresentation> {
        return if (patients.isEmpty()) Collections.emptyList() else patients
            .map{
                patientRepresentation(it)
            }
    }

    private fun toDoctorsRepresentation(doctors: List<Doctor>): List<UserNameRepresentation> {
        return if (doctors.isEmpty()) Collections.emptyList() else doctors
            .map{
                doctorRepresentation(it)
            }
    }

    private fun patientRepresentation(patient: Patient): PatientNameRepresentation {
        val representation = PatientNameRepresentation()

        representation.firstName = patient.firstName
        representation.lastName = patient.lastName

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientById(patient.patientId))
            .withSelfRel())
    }

    private fun doctorRepresentation(doctor: Doctor): UserNameRepresentation {
        val representation = UserNameRepresentation()

        representation.firstName = doctor.firstName
        representation.lastName = doctor.lastName

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDoctorById(doctor.userId))
            .withSelfRel())
    }
}