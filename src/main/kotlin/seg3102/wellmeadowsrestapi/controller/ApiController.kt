package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import seg3102.wellmeadowsrestapi.assemblers.*
import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.repository.*
import seg3102.wellmeadowsrestapi.representation.*
import seg3102.wellmeadowsrestapi.services.PatientServiceImp

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class ApiController(val hospitalFileRepository: HospitalFileRepository,
                    val divisionFileRepository: DivisionFileRepository,
                    val divisionRepository: DivisionRepository,
                    val patientRepository: PatientRepository,
                    val contactRepository: PatientContactRepository,
                    val prescriptionRepository: PrescriptionRepository,
                    val doctorRepository: DoctorRepository,
                    val nurseRepository: NurseRepository,
                    val doctorAssembler: DoctorModelAssembler,
                    val divisionFileAssembler: DivisionAdmissionFileModelAssembler,
                    val hospitalFileAssembler: HospitalAdmissionFileModelAssembler,
                    val patientAssembler: PatientModelAssembler,
                    val prescriptionAssembler: PrescriptionModelAssembler,
                    val nurseAssembler: NurseModelAssembler,
                    val divisionAssembler: DivisionModelAssembler,
                    val patientContactAssembler: PatientContactModelAssembler) {

    @Autowired
    val patientServices = PatientServiceImp()

    @Operation(summary = "Get a Doctor by id")
    @GetMapping("/doctors/{id}")
    fun getDoctorById(@PathVariable("id") id: Long): ResponseEntity<DoctorRepresentation> {
        return doctorRepository.findById(id)
            .map { entity: Doctor -> doctorAssembler.toModel(entity) }
            .map { body: DoctorRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get a Nurse by id")
    @GetMapping("/nurses/{id}")
    fun getNurseById(@PathVariable("id") id: Long): ResponseEntity<NurseRepresentation> {
        return nurseRepository.findById(id)
            .map { entity: Nurse -> nurseAssembler.toModel(entity) }
            .map { body: NurseRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Nurses")
    @GetMapping("/nurses")
    fun getNurses(): ResponseEntity<CollectionModel<NurseRepresentation>> {
        val nurses = nurseRepository.findAll()
        return ResponseEntity(
            nurseAssembler.toCollectionModel(nurses),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Division by id")
    @GetMapping("/divisions/{id}")
    fun getDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        return divisionRepository.findById(id)
            .map { entity: Division -> divisionAssembler.toModel(entity) }
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Divisions")
    @GetMapping("/divisions/bad")
    fun getDivisions(): ResponseEntity<CollectionModel<DivisionRepresentation>> {
        val divisions = divisionRepository.findAll()
        return ResponseEntity(
            divisionAssembler.toCollectionModel(divisions),
            HttpStatus.OK)
    }

    @Operation(summary = "Get an nurse's assigned division")
    @GetMapping("/nurses/{id}/division")
    fun getNurseDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        return nurseRepository.findById(id)
            .map { nurse: Nurse ->  divisionAssembler.toModel(nurse.division)}
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an patient's contact")
    @GetMapping("/patients/{id}/contact")
    fun getPatientContactById(@PathVariable("id") id: Long): ResponseEntity<PatientContactRepresentation> {
        return patientRepository.findById(id)
            .map { patient: Patient ->  patientContactAssembler.toModel(patient.patientContact)}
            .map { body: PatientContactRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an patient's division admission file")
    @GetMapping("/patients/{id}/divisionFile")
    fun getPatientDivisionFileById(@PathVariable("id") id: Long): ResponseEntity<DivisionAdmissionFileRepresentation> {
        return patientRepository.findById(id)
            .map { patient: Patient ->  divisionFileAssembler.toModel(patient.divisionAdmissionFile)}
            .map { body: DivisionAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an patient's contact")
    @GetMapping("/patients/{id}/hospitalFile")
    fun getPatientHospitalFileById(@PathVariable("id") id: Long): ResponseEntity<HospitalAdmissionFileRepresentation> {
        return patientRepository.findById(id)
            .map { patient: Patient ->  hospitalFileAssembler.toModel(patient.hospitalAdmissionFile)}
            .map { body: HospitalAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Prescriptions")
    @GetMapping("/prescriptions")
    fun getPrescriptions(): ResponseEntity<CollectionModel<PrescriptionRepresentation>> {
        val prescription = prescriptionRepository.findAll()
        return ResponseEntity(
            prescriptionAssembler.toCollectionModel(prescription),
            HttpStatus.OK)
    }

    @Operation(summary = "Get all Patients for a Doctor")
    @GetMapping("/doctors/{id}/patients")
    fun getPatientsById(@PathVariable("id") id: Long): ResponseEntity<List<PatientNameRepresentation>> {
        return doctorRepository.findById(id)
            .map { doctor: Doctor -> doctorAssembler.toPatientsRepresentation(doctor.patients) }
            .map { body: List<PatientNameRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Prescriptions for a Doctor")
    @GetMapping("/doctors/{id}/prescriptions")
    fun getPrescriptionsById(@PathVariable("id") id: Long): ResponseEntity<List<PrescriptionNameRepresentation>> {
        return doctorRepository.findById(id)
            .map { doctor: Doctor -> doctorAssembler.toPrescriptionsRepresentation(doctor.prescriptions) }
            .map { body: List<PrescriptionNameRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Patients for a Division")
    @GetMapping("/divisions/{id}/patients")
    fun getDivisionPatientsById(@PathVariable("id") id: Long): ResponseEntity<List<PatientNameRepresentation>> {
        return divisionRepository.findById(id)
            .map { division: Division -> divisionAssembler.toPatientsRepresentation(division.patients) }
            .map { body: List<PatientNameRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Admission Files for a Division")
    @GetMapping("/divisions/{id}/files")
    fun getDivisionAdmissionFilesById(@PathVariable("id") id: Long): ResponseEntity<List<DivisionAdmissionFileRepresentation>> {
        return divisionRepository.findById(id)
            .map { division: Division -> divisionAssembler.toFilesRepresentation(division.divisionAdmissionFiles) }
            .map { body: List<DivisionAdmissionFileRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get a Division File by id")
    @GetMapping("/division/files/{id}")
    fun getDivisionFileById(@PathVariable("id") id: Long): ResponseEntity<DivisionAdmissionFileRepresentation> {
        return divisionFileRepository.findById(id)
            .map { entity: DivisionAdmissionFile -> divisionFileAssembler.toModel(entity) }
            .map { body: DivisionAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    /*@Operation(summary = "Get a Division File's Division by id")
    @GetMapping("/divisionFiles/{id}/division")
    fun getDivisionFileDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        return divisionFileRepository.findById(id)
            .map { entity: DivisionAdmissionFile -> divisionAssembler.toModel(entity.division) }
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get a Division Nurse by id")
    @GetMapping("/division/{id}/nurse")
    fun getDivisionNurseById(@PathVariable("id") id: Long): ResponseEntity<NurseRepresentation> {
        return divisionRepository.findById(id)
            .map { entity: Division -> nurseAssembler.toModel(entity.nurse!!) }
            .map { body: NurseRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }*/

    @Operation(summary = "Get a Hospital File by id")
    @GetMapping("/hospital/files/{id}")
    fun getHospitalFileById(@PathVariable("id") id: Long): ResponseEntity<HospitalAdmissionFileRepresentation> {
        return hospitalFileRepository.findById(id)
            .map { entity: HospitalAdmissionFile -> hospitalFileAssembler.toModel(entity) }
            .map { body: HospitalAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    /*@Operation(summary = "Get a Hospital File by id")
    @GetMapping("/contact/{id}/patient")
    fun getContactPatientById(@PathVariable("id") id: Long): ResponseEntity<PatientRepresentation> {
        return contactRepository.findById(id)
            .map { entity: PatientContact -> patientAssembler.toModel(entity.patient!!) }
            .map { body: PatientRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get a Hospital File's patient by id")
    @GetMapping("/hospital/files/{id}/patient")
    fun getHospitalFilePatientById(@PathVariable("id") id: Long): ResponseEntity<PatientRepresentation> {
        return hospitalFileRepository.findById(id)
            .map { entity: HospitalAdmissionFile -> patientAssembler.toModel(entity.patient!!) }
            .map { body: PatientRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }*/

    @Operation(summary = "Get a Prescription by id")
    @GetMapping("/prescriptions/{id}")
    fun getPrescriptionById(@PathVariable("id") id: Long): ResponseEntity<PrescriptionRepresentation> {
        return prescriptionRepository.findById(id)
            .map { entity: Prescription -> prescriptionAssembler.toModel(entity) }
            .map { body: PrescriptionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }
}