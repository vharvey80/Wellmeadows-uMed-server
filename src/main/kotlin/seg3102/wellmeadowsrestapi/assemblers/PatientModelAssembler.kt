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
class PatientModelAssembler: RepresentationModelAssemblerSupport<Patient, PatientRepresentation>(ApiController::class.java, PatientRepresentation::class.java) {
    override fun toModel(entity: Patient): PatientRepresentation {
        val patientRepresentation = instantiateModel(entity)
        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientById(entity.patientId))
            .withSelfRel())

        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientContactById(entity.patientId))
            .withRel("patientContact"))

        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientDivisionFileById(entity.patientId))
            .withRel("divisionAdmissionFile"))

        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientHospitalFileById(entity.patientId))
            .withRel("hospitalAdmissionFile"))

        /*patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientDoctorById(entity.patientId))
            .withRel("doctor"))

        patientRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientDivisionById(entity.patientId))
            .withRel("division"))*/

        patientRepresentation.patientContact = contactRepresentation(entity.patientContact)
        patientRepresentation.divisionFile = divisionFileRepresentation(entity.divisionAdmissionFile!!)
        patientRepresentation.hospitalFile = hospitalFileRepresentation(entity.hospitalAdmissionFile!!)

        //patientRepresentation.doctor = doctorRepresentation(entity.doctor)
        //patientRepresentation.division = divisionRepresentation(entity.division)

        patientRepresentation.prescriptions = toPrescriptionsRepresentation(entity.prescriptions)

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

    private fun toPrescriptionsRepresentation(prescriptions: List<Prescription>): List<PrescriptionNameRepresentation> {
        return if (prescriptions.isEmpty()) Collections.emptyList() else prescriptions
            .map{
                prescriptionRepresentation(it)
            }
    }

    private fun prescriptionRepresentation(prescription: Prescription): PrescriptionNameRepresentation {
        val representation = PrescriptionNameRepresentation()

        representation.medicationName = prescription.medicationName
        representation.startDate = prescription.startDate

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPrescriptionById(prescription.medicationId))
            .withSelfRel())
    }

    private fun contactRepresentation(contact: PatientContact): PatientContactRepresentation {
        val representation = PatientContactRepresentation()

        representation.firstName = contact.firstName
        representation.lastName = contact.lastName
        representation.relationship = contact.relationship

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientContactById(contact.contactId))
            .withSelfRel())
    }

    private fun divisionFileRepresentation(divisionFile: DivisionAdmissionFile): DivisionAdmissionFileRepresentation {
        val representation = DivisionAdmissionFileRepresentation()

        representation.divisionFileId = divisionFile.divisionFileId
        representation.requestRationale = divisionFile.requestRationale
        representation.priority = divisionFile.priority

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFileById(divisionFile.divisionFileId))
            .withSelfRel())
    }

    private fun hospitalFileRepresentation(hospitalFile: HospitalAdmissionFile): HospitalAdmissionFileRepresentation {
        val representation = HospitalAdmissionFileRepresentation()

        representation.hospitalFileId = hospitalFile.hospitalFileId
        representation.bedNumber = hospitalFile.bedNumber
        representation.privateInsuranceNumber = hospitalFile.privateInsuranceNumber

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getHospitalFileById(hospitalFile.hospitalFileId))
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

    private fun divisionRepresentation(division: Division): DivisionNameRepresentation {
        val representation = DivisionNameRepresentation()

        representation.divisionName = division.divisionName
        representation.location = division.location

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionById(division.divisionId))
            .withSelfRel())
    }
}