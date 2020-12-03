package seg3102.wellmeadowsrestapi.repository

import seg3102.wellmeadowsrestapi.entities.Prescription
import org.springframework.data.repository.CrudRepository

interface PrescriptionRepository: CrudRepository<Prescription, Long>