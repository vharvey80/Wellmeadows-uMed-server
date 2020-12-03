package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import seg3102.wellmeadowsrestapi.assemblers.*
import seg3102.wellmeadowsrestapi.entities.*
import seg3102.wellmeadowsrestapi.repository.*
import seg3102.wellmeadowsrestapi.representation.*

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class ApiController(val hospitalFileRepository: HospitalFileRepository,
                    val divisionFileRepository: DivisionFileRepository,
                    val divisionRepository: DivisionRepository,
                    val patientRepository: PatientRepository,
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

    @Operation(summary = "Get a Division by id")
    @GetMapping("/divisions/{id}")
    fun getDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        return divisionRepository.findById(id)
            .map { entity: Division -> divisionAssembler.toModel(entity) }
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
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

    @Operation(summary = "Get a Patients by id")
    @GetMapping("/patients/{id}")
    fun getPatientById(@PathVariable("id") id: Long): ResponseEntity<PatientRepresentation> {
        return patientRepository.findById(id)
            .map { entity: Patient -> patientAssembler.toModel(entity) }
            .map { body: PatientRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Patients for a Doctor")
    @GetMapping("/doctors/{id}/patients")
    fun getPatientsById(@PathVariable("id") id: Long): ResponseEntity<List<PatientNameRepresentation>> {
        return doctorRepository.findById(id)
            .map { doctor: Doctor -> doctorAssembler.toPatientsRepresentation(doctor.patients) }
            .map { body: List<PatientNameRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an patient's assigned doctor")
    @GetMapping("/patients/{id}/doctor")
    fun getPatientDoctorById(@PathVariable("id") id: Long): ResponseEntity<DoctorRepresentation> {
        return patientRepository.findById(id)
            .map { patient: Patient ->  doctorAssembler.toModel(patient.doctor)}
            .map { body: DoctorRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an patient's assigned division")
    @GetMapping("/patients/{id}/division")
    fun getPatientDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        return patientRepository.findById(id)
            .map { patient: Patient ->  divisionAssembler.toModel(patient.division)}
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Division Files for a Doctor")
    @GetMapping("/doctors/{id}/divisionFiles")
    fun getDivisionFilesById(@PathVariable("id") id: Long): ResponseEntity<List<DivisionAdmissionFileRepresentation>> {
        return doctorRepository.findById(id)
            .map { doctor: Doctor -> doctorAssembler.toDivisionFilesRepresentation(doctor.divisionAdmissionFiles) }
            .map { body: List<DivisionAdmissionFileRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an division file's assigned doctor")
    @GetMapping("/divisionFiles/{id}/doctor")
    fun getDivisionFileDoctorById(@PathVariable("id") id: Long): ResponseEntity<DoctorRepresentation> {
        return divisionFileRepository.findById(id)
            .map { file: DivisionAdmissionFile ->  doctorAssembler.toModel(file.doctor)}
            .map { body: DoctorRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an division file's assigned nurse")
    @GetMapping("/divisionFiles/{id}/division")
    fun getDivisionFileDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        return divisionFileRepository.findById(id)
            .map { file: DivisionAdmissionFile ->  divisionAssembler.toModel(file.division)}
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an hospital file's assigned doctor")
    @GetMapping("/hospitalFiles/{id}/doctor")
    fun getHospitalFileDoctorById(@PathVariable("id") id: Long): ResponseEntity<DoctorRepresentation> {
        return hospitalFileRepository.findById(id)
            .map { file: HospitalAdmissionFile ->  doctorAssembler.toModel(file.doctor)}
            .map { body: DoctorRepresentation -> ResponseEntity.ok(body) }
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

    @Operation(summary = "Get all Hospital Files for a Doctor")
    @GetMapping("/doctors/{id}/hospitalFiles")
    fun getHospitalFilesById(@PathVariable("id") id: Long): ResponseEntity<List<HospitalAdmissionFileRepresentation>> {
        return doctorRepository.findById(id)
            .map { doctor: Doctor -> doctorAssembler.toHospitalFilesRepresentation(doctor.hospitalAdmissionFiles) }
            .map { body: List<HospitalAdmissionFileRepresentation> -> ResponseEntity.ok(body) }
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

    @Operation(summary = "Get a Hospital File by id")
    @GetMapping("/hospital/files/{id}")
    fun getHospitalFileById(@PathVariable("id") id: Long): ResponseEntity<HospitalAdmissionFileRepresentation> {
        return hospitalFileRepository.findById(id)
            .map { entity: HospitalAdmissionFile -> hospitalFileAssembler.toModel(entity) }
            .map { body: HospitalAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get a Prescription by id")
    @GetMapping("/prescriptions/{id}")
    fun getPrescriptionById(@PathVariable("id") id: Long): ResponseEntity<PrescriptionRepresentation> {
        return prescriptionRepository.findById(id)
            .map { entity: Prescription -> prescriptionAssembler.toModel(entity) }
            .map { body: PrescriptionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }
}