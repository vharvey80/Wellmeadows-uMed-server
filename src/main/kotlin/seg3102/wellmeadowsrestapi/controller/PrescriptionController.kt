package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3102.wellmeadowsrestapi.assemblers.PrescriptionModelAssembler
import seg3102.wellmeadowsrestapi.entities.Patient
import seg3102.wellmeadowsrestapi.entities.Prescription
import seg3102.wellmeadowsrestapi.representation.PatientRepresentation
import seg3102.wellmeadowsrestapi.representation.PrescriptionRepresentation
import seg3102.wellmeadowsrestapi.services.PrescriptionServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class PrescriptionController {

    @Autowired
    val prescriptionServices = PrescriptionServiceImp()

    @Autowired
    val prescriptionAssembler = PrescriptionModelAssembler()

    @Operation(summary = "Get all Prescriptions")
    @GetMapping("/prescriptions")
    fun getPrescriptions(): ResponseEntity<CollectionModel<PrescriptionRepresentation>> {
        val prescriptions = prescriptionServices.getPrescriptions()
        return ResponseEntity(
            prescriptionAssembler.toCollectionModel(prescriptions),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Prescription by its id")
    @GetMapping("/prescriptions/{id}")
    fun getPrescriptionById(@PathVariable("id") id: Long): ResponseEntity<PrescriptionRepresentation> {
        val patient = prescriptionServices.getPrescriptionById(id)
        return patient
            .map { entity: Prescription -> prescriptionAssembler.toModel(entity) }
            .map { body: PrescriptionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Prescription by its id")
    @DeleteMapping("/prescriptions/delete/{id}")
    fun deletePrescriptionById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            prescriptionServices.deletePrescription(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Prescriptions in DB")
    @DeleteMapping("/patients/delete")
    fun deletePrescriptions(): ResponseEntity<Any> {
        return try {
            prescriptionServices.deleteAllPrescriptions()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Prescription")
    @PostMapping("/prescriptions")
    fun addPrescription(@RequestBody prescription: Prescription): ResponseEntity<Any> {
        return try {
            val newPrescription = prescriptionServices.addPrescription(prescription)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPrescription.medicationId)
                .toUri()
            ResponseEntity.created(location).body(prescriptionAssembler.toModel(newPrescription))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Prescription")
    @PutMapping("/prescriptions/{id}")
    fun updatePrescription(@PathVariable("id") id: Long, @RequestBody prescription: Prescription): ResponseEntity<Any> {
        return try {
            prescriptionServices.updatePrescription(id, prescription)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}