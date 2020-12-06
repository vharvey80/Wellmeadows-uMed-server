package seg3102.wellmeadowsrestapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.entities.Prescription
import seg3102.wellmeadowsrestapi.repository.PatientRepository
import seg3102.wellmeadowsrestapi.repository.PrescriptionRepository
import java.util.*

interface PrescriptionService {
    fun addPrescription(prescription: Prescription): Prescription
    fun deletePrescription(id: Long)
    fun deleteAllPrescriptions()
    fun getPrescriptionById(id: Long): Optional<Prescription>
    fun getPrescriptions(): MutableIterable<Prescription>?
    fun updatePrescription(id: Long, prescription: Prescription): Prescription
}

@Service
class PrescriptionServiceImp: PrescriptionService {

    @Autowired
    lateinit var prescriptionRepository: PrescriptionRepository

    override fun addPrescription(prescription: Prescription): Prescription = prescriptionRepository.save(prescription)

    override fun deletePrescription(id: Long) = prescriptionRepository.deleteById(id)

    override fun deleteAllPrescriptions() = prescriptionRepository.deleteAll()

    override fun getPrescriptionById(id: Long): Optional<Prescription> = prescriptionRepository.findById(id)

    override fun getPrescriptions(): MutableIterable<Prescription> = prescriptionRepository.findAll()

    override fun updatePrescription(id: Long, prescription: Prescription): Prescription {
        val currentPrescription = prescriptionRepository.findById(id).get()

        currentPrescription.medicationName = prescription.medicationName
        currentPrescription.startDate = prescription.startDate
        currentPrescription.methodOfAdmin = prescription.methodOfAdmin
        currentPrescription.unitsByDay = prescription.unitsByDay

        return prescriptionRepository.save(currentPrescription)
    }
}