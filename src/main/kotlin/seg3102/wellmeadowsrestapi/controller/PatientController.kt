package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3102.wellmeadowsrestapi.assemblers.DivisionAdmissionFileModelAssembler
import seg3102.wellmeadowsrestapi.assemblers.HospitalAdmissionFileModelAssembler
import seg3102.wellmeadowsrestapi.assemblers.PatientContactModelAssembler
import seg3102.wellmeadowsrestapi.assemblers.PatientModelAssembler
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.entities.PatientContact
import seg3102.wellmeadowsrestapi.representation.DivisionAdmissionFileRepresentation
import seg3102.wellmeadowsrestapi.representation.HospitalAdmissionFileRepresentation
import seg3102.wellmeadowsrestapi.representation.PatientContactRepresentation
import seg3102.wellmeadowsrestapi.representation.PatientRepresentation
import seg3102.wellmeadowsrestapi.services.ContactServiceImp
import seg3102.wellmeadowsrestapi.services.PatientServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class PatientController {

    @Autowired
    val patientServices = PatientServiceImp()

    @Autowired
    val contactServices = ContactServiceImp()

    @Autowired
    val patientAssembler = PatientModelAssembler()

    @Autowired
    val contactAssembler = PatientContactModelAssembler()

    @Autowired
    val hospitalFileAssembler = HospitalAdmissionFileModelAssembler()

    @Autowired
    val divisionFileAssembler = DivisionAdmissionFileModelAssembler()

    @Operation(summary = "Get all Patients")
    @GetMapping("/patients")
    fun getPatients(): ResponseEntity<CollectionModel<PatientRepresentation>> {
        val patients = patientServices.getPatients()
        return ResponseEntity(
            patientAssembler.toCollectionModel(patients),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Patient by his id")
    @GetMapping("/patients/{id}")
    fun getPatientById(@PathVariable("id") id: Long): ResponseEntity<PatientRepresentation> {
        val patient = patientServices.getPatientById(id)
        return patient
            .map { entity: Patient -> patientAssembler.toModel(entity) }
            .map { body: PatientRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get a Patient's Contact")
    @GetMapping("/patients/{id}/contact")
    fun getPatientContactById(@PathVariable("id") id: Long): ResponseEntity<PatientContactRepresentation> {
        return patientServices.getPatientById(id)
            .map { patient: Patient ->  contactAssembler.toModel(patient.patientContact)}
            .map { body: PatientContactRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an patient's hospital file")
    @GetMapping("/patients/{id}/hospitalFile")
    fun getPatientHospitalFileById(@PathVariable("id") id: Long): ResponseEntity<HospitalAdmissionFileRepresentation> {
        return patientServices.getPatientById(id)
            .map { patient: Patient ->  hospitalFileAssembler.toModel(patient.hospitalAdmissionFile)}
            .map { body: HospitalAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an patient's division admission file")
    @GetMapping("/patients/{id}/divisionFile")
    fun getPatientDivisionFileById(@PathVariable("id") id: Long): ResponseEntity<DivisionAdmissionFileRepresentation> {
        return patientServices.getPatientById(id)
            .map { patient: Patient ->  divisionFileAssembler.toModel(patient.divisionAdmissionFile)}
            .map { body: DivisionAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Patient by his id")
    @DeleteMapping("/patients/delete/{id}")
    fun deletePatientById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            patientServices.deletePatient(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Patients in DB")
    @DeleteMapping("/patients/delete")
    fun deletePatients(): ResponseEntity<Any> {
        return try {
            patientServices.deleteAllPatients()
            contactServices.deleteAllContacts()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Patient")
    @PostMapping("/patients")
    fun addPatient(@RequestBody patient: Patient): ResponseEntity<Any> {
        return try {
            val newPatient = patientServices.addPatient(patient)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPatient.patientId)
                .toUri()
            ResponseEntity.created(location).body(patientAssembler.toModel(newPatient))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a contact to a Patient")
    @PostMapping("/patients/{id}/contact")
    fun addContactToPatient(@PathVariable("id") id: Long, @RequestBody contact: PatientContact): ResponseEntity<Any> {
        return try {
            val patient = patientServices.getPatientById(id).get()
            patient.patientContact = contact

            val newContact = contactServices.addContact(contact)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/uMed-api")
                .path("/contacts")
                .path("/{id}")
                .buildAndExpand(newContact.contactId)
                .toUri()
            ResponseEntity.created(location).body(contactAssembler.toModel(newContact))
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Patient")
    @PutMapping("/patients/{id}")
    fun updatePatient(@PathVariable("id") id: Long, @RequestBody patient: Patient): ResponseEntity<Any> {
        return try {
            patientServices.updatePatient(id, patient)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}