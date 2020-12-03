package seg3102.wellmeadowsrestapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import seg3102.wellmeadowsrestapi.entities.Division
import seg3102.wellmeadowsrestapi.entities.Doctor
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.repository.DivisionRepository
import seg3102.wellmeadowsrestapi.repository.DoctorRepository
import seg3102.wellmeadowsrestapi.repository.PatientRepository

@SpringBootApplication
class WellmeadowsRestApiApplication {
	@Bean
	fun init(patientRepo: PatientRepository,
	  			   divisionRepo: DivisionRepository,
	               doctorRepo: DoctorRepository) = CommandLineRunner {
		patientRepo.save(Patient(
			fName = "Vincent",
			lName = "Harvey",
			g 	  = "male"
		))

		patientRepo.save(Patient(
			fName = "Ginette",
			lName = "Poulin",
			g 	  = "female"
		))
	}
}

fun main(args: Array<String>) {
	runApplication<WellmeadowsRestApiApplication>(*args)
}
