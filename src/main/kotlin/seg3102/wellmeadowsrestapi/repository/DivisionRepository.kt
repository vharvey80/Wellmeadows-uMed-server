package seg3102.wellmeadowsrestapi.repository

import seg3102.wellmeadowsrestapi.entities.Division
import org.springframework.data.repository.CrudRepository

interface DivisionRepository: CrudRepository<Division, Long>