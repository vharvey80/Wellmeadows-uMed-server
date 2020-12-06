package seg3102.wellmeadowsrestapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import seg3102.wellmeadowsrestapi.entities.Doctor
import seg3102.wellmeadowsrestapi.entities.Nurse
import seg3102.wellmeadowsrestapi.repository.DoctorRepository
import seg3102.wellmeadowsrestapi.repository.NurseRepository
import java.util.*

interface DoctorService {
    fun addDoctor(doctor: Doctor): Doctor
    fun deleteDoctor(id: Long)
    fun deleteAllDoctors()
    fun getDoctorById(id: Long): Optional<Doctor>
    fun getDoctors(): MutableIterable<Doctor>?
    fun updateDoctor(id: Long, doctor: Doctor): Doctor
}

interface NurseService {
    fun addNurse(nurse: Nurse): Nurse
    fun deleteNurse(id: Long)
    fun deleteAllNurses()
    fun getNurseById(id: Long): Optional<Nurse>
    fun getNurses(): MutableIterable<Nurse>?
    fun updateNurse(id: Long, nurse: Nurse): Nurse
}

@Service
class DoctorServiceImp: DoctorService {

    @Autowired
    lateinit var doctorRepository: DoctorRepository

    override fun addDoctor(doctor: Doctor): Doctor = doctorRepository.save(doctor)

    override fun deleteDoctor(id: Long) = doctorRepository.deleteById(id)

    override fun deleteAllDoctors() = doctorRepository.deleteAll()

    override fun getDoctorById(id: Long): Optional<Doctor> = doctorRepository.findById(id)

    override fun getDoctors(): MutableIterable<Doctor> = doctorRepository.findAll()

    override fun updateDoctor(id: Long, doctor: Doctor): Doctor {
        val currentDoctor = doctorRepository.findById(id).get()

        currentDoctor.firstName = doctor.firstName
        currentDoctor.lastName = doctor.lastName
        currentDoctor.phoneNumber = doctor.phoneNumber
        currentDoctor.email = doctor.email
        currentDoctor.password = doctor.password
        currentDoctor.phoneExtension = doctor.phoneExtension

        currentDoctor.prescriptions = doctor.prescriptions
        currentDoctor.patients = doctor.patients

        return doctorRepository.save(currentDoctor)
    }
}

@Service
class NurseServiceImp: NurseService {

    @Autowired
    lateinit var nurseRepository: NurseRepository

    override fun addNurse(nurse: Nurse): Nurse = nurseRepository.save(nurse)

    override fun deleteNurse(id: Long) = nurseRepository.deleteById(id)

    override fun deleteAllNurses() = nurseRepository.deleteAll()

    override fun getNurseById(id: Long): Optional<Nurse> = nurseRepository.findById(id)

    override fun getNurses(): MutableIterable<Nurse> = nurseRepository.findAll()

    override fun updateNurse(id: Long, nurse: Nurse): Nurse {
        val currentNurse = nurseRepository.findById(id).get()

        currentNurse.firstName = nurse.firstName
        currentNurse.lastName = nurse.lastName
        currentNurse.phoneNumber = nurse.phoneNumber
        currentNurse.email = nurse.email
        currentNurse.password = nurse.password
        currentNurse.phoneExtension = nurse.phoneExtension

        currentNurse.division = nurse.division

        return nurseRepository.save(currentNurse)
    }
}