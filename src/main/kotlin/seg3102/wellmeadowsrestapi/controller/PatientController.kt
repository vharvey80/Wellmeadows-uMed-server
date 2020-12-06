package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3102.wellmeadowsrestapi.assemblers.PatientModelAssembler
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.representation.PatientRepresentation
import seg3102.wellmeadowsrestapi.services.PatientServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api/patients", produces = ["application/hal+json"])
class PatientController {

    @Autowired
    val patientServices = PatientServiceImp()

    @Autowired
    val patientAssembler = PatientModelAssembler()

    @Operation(summary = "Get all Patients")
    @GetMapping("")
    fun getPatients(): ResponseEntity<CollectionModel<PatientRepresentation>> {
        val patients = patientServices.getPatients()
        return ResponseEntity(
            patientAssembler.toCollectionModel(patients),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Patient by his id")
    @GetMapping("/{id}")
    fun getPatientById(@PathVariable("id") id: Long): ResponseEntity<PatientRepresentation> {
        val patient = patientServices.getPatientById(id)
        return patient
            .map { entity: Patient -> patientAssembler.toModel(entity) }
            .map { body: PatientRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Patient by his id")
    @DeleteMapping("/delete/{id}")
    fun deletePatientById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            patientServices.deletePatient(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Patients in DB")
    @DeleteMapping("/delete")
    fun deletePatients(): ResponseEntity<Any> {
        return try {
            patientServices.deleteAllPatients()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Patient")
    @PostMapping("")
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

    @Operation(summary = "Update an existing Patient")
    @PutMapping("/{id}")
    fun updateBook(@PathVariable("id") id: Long, @RequestBody patient: Patient): ResponseEntity<Any> {
        return try {
            val currPatient = patientServices.updatePatient(id, patient)
            ResponseEntity.noContent().build<Any>()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}