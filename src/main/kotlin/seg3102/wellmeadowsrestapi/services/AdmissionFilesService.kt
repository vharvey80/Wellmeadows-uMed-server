package seg3102.wellmeadowsrestapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import seg3102.wellmeadowsrestapi.entities.DivisionAdmissionFile
import seg3102.wellmeadowsrestapi.entities.HospitalAdmissionFile
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.repository.DivisionFileRepository
import seg3102.wellmeadowsrestapi.repository.HospitalFileRepository
import seg3102.wellmeadowsrestapi.repository.PatientRepository
import java.util.*

interface DivisionAdmissionFileService {
    fun addDivisionFile(divFile: DivisionAdmissionFile): DivisionAdmissionFile
    fun deleteDivisionFile(id: Long)
    fun deleteAllDivisionFiles()
    fun getDivisionFileById(id: Long): Optional<DivisionAdmissionFile>
    fun getDivisionFiles(): MutableIterable<DivisionAdmissionFile>?
    fun updateDivisionFile(id: Long, divFile: DivisionAdmissionFile): DivisionAdmissionFile
}

interface HospitalAdmissionFileService {
    fun addHospitalFile(hosFile: HospitalAdmissionFile): HospitalAdmissionFile
    fun deleteHospitalFile(id: Long)
    fun deleteAllHospitalFiles()
    fun getHospitalFileById(id: Long): Optional<HospitalAdmissionFile>
    fun getHospitalFiles(): MutableIterable<HospitalAdmissionFile>?
    fun updateHospitalFile(id: Long, divFile: HospitalAdmissionFile): HospitalAdmissionFile
}

@Service
class DivisionAdmissionFileServiceImp: DivisionAdmissionFileService {

    @Autowired
    lateinit var divisionAdmissionFileRepository: DivisionFileRepository

    override fun addDivisionFile(divFile: DivisionAdmissionFile): DivisionAdmissionFile = divisionAdmissionFileRepository.save(divFile)

    override fun deleteDivisionFile(id: Long) = divisionAdmissionFileRepository.deleteById(id)

    override fun deleteAllDivisionFiles() = divisionAdmissionFileRepository.deleteAll()

    override fun getDivisionFileById(id: Long): Optional<DivisionAdmissionFile> = divisionAdmissionFileRepository.findById(id)

    override fun getDivisionFiles(): MutableIterable<DivisionAdmissionFile> = divisionAdmissionFileRepository.findAll()

    override fun updateDivisionFile(id: Long, divFile: DivisionAdmissionFile): DivisionAdmissionFile {
        val currentFile = divisionAdmissionFileRepository.findById(id).get()

        currentFile.requestRationale = divFile.requestRationale
        currentFile.priority = divFile.priority

        return divisionAdmissionFileRepository.save(currentFile)
    }
}

@Service
class HospitalAdmissionFileServiceImp: HospitalAdmissionFileService {

    @Autowired
    lateinit var hospitalAdmissionFileRepository: HospitalFileRepository

    override fun addHospitalFile(hosFile: HospitalAdmissionFile): HospitalAdmissionFile = hospitalAdmissionFileRepository.save(hosFile)

    override fun deleteHospitalFile(id: Long) = hospitalAdmissionFileRepository.deleteById(id)

    override fun deleteAllHospitalFiles() = hospitalAdmissionFileRepository.deleteAll()

    override fun getHospitalFileById(id: Long): Optional<HospitalAdmissionFile> = hospitalAdmissionFileRepository.findById(id)

    override fun getHospitalFiles(): MutableIterable<HospitalAdmissionFile> = hospitalAdmissionFileRepository.findAll()

    override fun updateHospitalFile(id: Long, hosFile: HospitalAdmissionFile): HospitalAdmissionFile {
        val currentFile = hospitalAdmissionFileRepository.findById(id).get()

        currentFile.bedNumber = hosFile.bedNumber
        currentFile.privateInsuranceNumber = hosFile.privateInsuranceNumber

        return hospitalAdmissionFileRepository.save(currentFile)
    }
}