package seg3102.wellmeadowsrestapi.assemblers

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component

import seg3102.wellmeadowsrestapi.entities.Doctor
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.entities.Prescription
import seg3102.wellmeadowsrestapi.controller.DoctorController
import seg3102.wellmeadowsrestapi.controller.PatientController
import seg3102.wellmeadowsrestapi.controller.PrescriptionController
import seg3102.wellmeadowsrestapi.representation.DoctorRepresentation
import seg3102.wellmeadowsrestapi.representation.PatientNameRepresentation
import seg3102.wellmeadowsrestapi.representation.PrescriptionNameRepresentation
import java.util.*

@Component
class DoctorModelAssembler: RepresentationModelAssemblerSupport<Doctor, DoctorRepresentation>(DoctorController::class.java, DoctorRepresentation::class.java) {
    override fun toModel(entity: Doctor): DoctorRepresentation {
        val doctorRepresentation = instantiateModel(entity)
        doctorRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(DoctorController::class.java)
                .getDoctorById(entity.userId))
            .withSelfRel())

        doctorRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(DoctorController::class.java)
                .getDoctorPatientsById(entity.userId))
            .withRel("patients"))

        doctorRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(DoctorController::class.java)
                .getDoctorPrescriptionsById(entity.userId))
            .withRel("prescriptions"))

        doctorRepresentation.patients = toPatientsRepresentation(entity.patients)
        doctorRepresentation.prescriptions = toPrescriptionsRepresentation(entity.prescriptions)

        doctorRepresentation.phoneExtension = entity.phoneExtension

        doctorRepresentation.userId = entity.userId
        doctorRepresentation.firstName = entity.firstName
        doctorRepresentation.lastName = entity.lastName
        doctorRepresentation.email = entity.email
        doctorRepresentation.password = entity.password
        doctorRepresentation.phoneNumber = entity.phoneNumber

        return doctorRepresentation
    }

    fun toPatientsRepresentation(patients: List<Patient>): List<PatientNameRepresentation> {
        return if (patients.isEmpty()) Collections.emptyList() else patients
            .map{
                patientRepresentation(it)
            }
    }

    fun toPrescriptionsRepresentation(prescriptions: List<Prescription>): List<PrescriptionNameRepresentation> {
        return if (prescriptions.isEmpty()) Collections.emptyList() else prescriptions
            .map{
                prescriptionRepresentation(it)
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

    private fun prescriptionRepresentation(prescription: Prescription): PrescriptionNameRepresentation {
        val representation = PrescriptionNameRepresentation()

        representation.medicationName = prescription.medicationName
        representation.startDate = prescription.startDate

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PrescriptionController::class.java)
                .getPrescriptionById(prescription.medicationId))
            .withSelfRel())
    }
}