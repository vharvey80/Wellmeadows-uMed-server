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
class DoctorModelAssembler: RepresentationModelAssemblerSupport<Doctor, DoctorRepresentation>(ApiController::class.java, DoctorRepresentation::class.java) {
    override fun toModel(entity: Doctor): DoctorRepresentation {
        val doctorRepresentation = instantiateModel(entity)
        doctorRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDoctorById(entity.userId))
            .withSelfRel())

        doctorRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientsById(entity.userId))
            .withRel("patients"))

        doctorRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFilesById(entity.userId))
            .withRel("divisionFiles"))

        doctorRepresentation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getHospitalFilesById(entity.userId))
            .withRel("hospitalFiles"))

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

    fun toDivisionFilesRepresentation(divisionFiles: List<DivisionAdmissionFile>): List<DivisionAdmissionFileRepresentation> {
        return if (divisionFiles.isEmpty()) Collections.emptyList() else divisionFiles
            .map{
                divisionFileRepresentation(it)
            }
    }

    fun toHospitalFilesRepresentation(hospitalFiles: List<HospitalAdmissionFile>): List<HospitalAdmissionFileRepresentation> {
        return if (hospitalFiles.isEmpty()) Collections.emptyList() else hospitalFiles
            .map{
                hospitalFileRepresentation(it)
            }
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
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getPatientById(patient.patientId))
            .withSelfRel())
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

    private fun divisionFileRepresentation(divisionFile: DivisionAdmissionFile): DivisionAdmissionFileRepresentation {
        val representation = DivisionAdmissionFileRepresentation()

        representation.divisionFileId = divisionFile.divisionFileId
        representation.requestRationale = divisionFile.requestRationale
        representation.priority = divisionFile.priority

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getDivisionFilesById(divisionFile.divisionFileId))
            .withSelfRel())
    }

    private fun hospitalFileRepresentation(hospitalFile: HospitalAdmissionFile): HospitalAdmissionFileRepresentation {
        val representation = HospitalAdmissionFileRepresentation()

        representation.hospitalFileId = hospitalFile.hospitalFileId
        representation.privateInsuranceNumber = hospitalFile.privateInsuranceNumber
        representation.bedNumber = hospitalFile.bedNumber

        return representation.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(ApiController::class.java)
                .getHospitalFileById(hospitalFile.hospitalFileId))
            .withSelfRel())
    }
}