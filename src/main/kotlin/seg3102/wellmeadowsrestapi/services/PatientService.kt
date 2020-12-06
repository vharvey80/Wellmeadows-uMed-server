package seg3102.wellmeadowsrestapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.repository.PatientRepository
import java.util.*

interface PatientService {
    fun addPatient(patient: Patient): Patient
    fun deletePatient(id: Long)
    fun deleteAllPatients()
    fun getPatientById(id: Long): Optional<Patient>
    fun getPatients(): MutableIterable<Patient>?
    fun updatePatient(id: Long, patient: Patient): Patient
}

@Service
class PatientServiceImp: PatientService {

    @Autowired
    lateinit var patientRepository: PatientRepository

    override fun addPatient(patient: Patient): Patient = patientRepository.save(patient)

    override fun deletePatient(id: Long) = patientRepository.deleteById(id)

    override fun deleteAllPatients() = patientRepository.deleteAll()

    override fun getPatientById(id: Long): Optional<Patient> = patientRepository.findById(id)

    override fun getPatients(): MutableIterable<Patient> = patientRepository.findAll()

    override fun updatePatient(id: Long, patient: Patient): Patient {
        val currentPatient = patientRepository.findById(id).get()

        currentPatient.firstName = patient.firstName
        currentPatient.lastName = patient.lastName
        currentPatient.address = patient.address
        currentPatient.phoneNumber = patient.phoneNumber
        currentPatient.dateOfBirth = patient.dateOfBirth
        currentPatient.gender = patient.gender
        currentPatient.maritalStatus = patient.maritalStatus

        return patientRepository.save(currentPatient)
    }
}