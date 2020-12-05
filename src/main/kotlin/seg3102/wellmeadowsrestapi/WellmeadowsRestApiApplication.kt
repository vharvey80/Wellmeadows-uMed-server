package seg3102.wellmeadowsrestapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.repository.*

@SpringBootApplication
class WellmeadowsRestApiApplication {
	@Bean
	fun init(patientRepo: PatientRepository,
			 divisionRepo: DivisionRepository,
			 doctorRepo: DoctorRepository,
			 nurseRepo: NurseRepository,
			 prescriptionRepo: PrescriptionRepository,
			 contactRepo: PatientContactRepository,
			 divFileRepo: DivisionFileRepository,
			 hosFileRepo: HospitalFileRepository
	) = CommandLineRunner {

		//TODO: Actual constructors for actual relevant data.

		val doc1 = Doctor("Roberto", "Caron", "rcaron123", "rcaron@domain.ca", "1234")
		val doc2 = Doctor("Brianna", "Goulet", "bgoulet123", "bgoulet@domain.ca", "2345")

		val nurse1 = Nurse("Alysha", "Tucker", "atucker123", "atucker@domain.ca", "3456")
		val nurse2 = Nurse("Tatiana", "Goodwin", "tgoodwin123", "tgoodwin@domain.ca", "4567")

		val div1 = Division("Intensif Care", "Wing A", 25, "Empty", nurse1)
		val div3 = Division("On-Call Room", "Wing C", 15, "Empty", nurse2)

		val contact1 = PatientContact("Leonard", "Downes", "Brother")
		val contact2 = PatientContact("Marshall", "Jackson", "Father")
		val contact3 = PatientContact("Kasey", "Elliot", "Sister")
		val contact4 = PatientContact("Joni", "Hail", "Legal Guardian")
		val contact5 = PatientContact("Susie", "Avery", "Wife")
		val contact6 = PatientContact("Elowen", "Wall", "Husband")

		val divFile1 = DivisionAdmissionFile("Intensive Care caused by a car acciend", 1)
		val divFile2 = DivisionAdmissionFile("Appointement for yearly checkup", 5)

		val hosFile1 = HospitalAdmissionFile(205, 123456789)
		val hosFile2 = HospitalAdmissionFile(206, 789456123)
		val hosFile3 = HospitalAdmissionFile(201, 567123894)
		val hosFile4 = HospitalAdmissionFile(202, 489712356)
		val hosFile5 = HospitalAdmissionFile(203, 102394586)
		val hosFile6 = HospitalAdmissionFile(208, 283948583)

		val presc1 = Prescription("Anti-Depressants", 1, "Pills")
		val presc2 = Prescription("Sleeping Aid", 1, "Drops")
		val presc3 = Prescription("Insulin", 2, "Needles")

		val patient1 = Patient("Vincent", "Harvey", "M", "1996-04-12", "Admitted", contact1)
		val patient2 = Patient("Ginette", "Poulin", "F", "1992-05-09", "Admitted", contact2)
		val patient3 = Patient("Robens", "Rodriguez", "M", "1989-10-21", "In Division", contact3)
		val patient4 = Patient("Juanita", "Basil", "F", "2000-09-19", "Admitted", contact4)
		val patient5 = Patient("Simon", "Vachon", "M", "1994-02-25", "In Division", contact5)
		val patient6 = Patient("Cindy", "Tremblay", "F", "1973-01-30", "Admitted", contact6)
	}
}

fun main(args: Array<String>) {
	runApplication<WellmeadowsRestApiApplication>(*args)
}
