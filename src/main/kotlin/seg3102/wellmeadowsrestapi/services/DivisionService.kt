package seg3102.wellmeadowsrestapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import seg3102.wellmeadowsrestapi.entities.Division
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.repository.DivisionRepository
import seg3102.wellmeadowsrestapi.repository.PatientRepository
import java.util.*

interface DivisionService {
    fun addDivision(division: Division): Division
    fun deleteDivision(id: Long)
    fun deleteAllDivisions()
    fun getDivisionById(id: Long): Optional<Division>
    fun getDivisions(): MutableIterable<Division>?
    fun updateDivision(id: Long, division: Division): Division
}

@Service
class DivisionServiceImp: DivisionService {

    @Autowired
    lateinit var divisionRepository: DivisionRepository

    override fun addDivision(division: Division): Division = divisionRepository.save(division)

    override fun deleteDivision(id: Long) = divisionRepository.deleteById(id)

    override fun deleteAllDivisions() = divisionRepository.deleteAll()

    override fun getDivisionById(id: Long): Optional<Division> = divisionRepository.findById(id)

    override fun getDivisions(): MutableIterable<Division> = divisionRepository.findAll()

    override fun updateDivision(id: Long, division: Division): Division {
        val currentDivision = divisionRepository.findById(id).get()

        currentDivision.divisionName = division.divisionName
        currentDivision.numberOfBeds = division.numberOfBeds
        currentDivision.phoneExtension = division.phoneExtension
        currentDivision.location = division.location
        currentDivision.status = division.status

        currentDivision.divisionAdmissionFiles = division.divisionAdmissionFiles
        currentDivision.patients = division.patients

        return divisionRepository.save(currentDivision)
    }
}