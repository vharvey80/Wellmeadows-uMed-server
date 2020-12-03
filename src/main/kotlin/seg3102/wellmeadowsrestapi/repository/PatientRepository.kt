package seg3102.wellmeadowsrestapi.repository

import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.entities.PatientContact
import org.springframework.data.repository.CrudRepository

interface PatientRepository: CrudRepository<Patient, Long>

interface PatientContactRepository: CrudRepository<PatientContact, Long>