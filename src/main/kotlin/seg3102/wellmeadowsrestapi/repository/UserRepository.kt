package seg3102.wellmeadowsrestapi.repository

import seg3102.wellmeadowsrestapi.entities.User
import seg3102.wellmeadowsrestapi.entities.Doctor
import seg3102.wellmeadowsrestapi.entities.Nurse

import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface UserRepository<User, Long> : CrudRepository<User, Long> {
    @Query(value = 
        "SELECT user FROM User AS u WHERE u.firstName = :firstName AND u.lastName = :lastName"
    )
    fun findUsersByName(firstName: String, lastName: String): List<User>
}

interface DoctorRepository : UserRepository<Doctor, Long>

interface NurseRepository : UserRepository<Nurse, Long>