package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3102.wellmeadowsrestapi.assemblers.DivisionModelAssembler
import seg3102.wellmeadowsrestapi.assemblers.DoctorModelAssembler
import seg3102.wellmeadowsrestapi.entities.Division
import seg3102.wellmeadowsrestapi.entities.Doctor
import seg3102.wellmeadowsrestapi.representation.DivisionRepresentation
import seg3102.wellmeadowsrestapi.representation.DoctorRepresentation
import seg3102.wellmeadowsrestapi.representation.PatientNameRepresentation
import seg3102.wellmeadowsrestapi.representation.PrescriptionNameRepresentation
import seg3102.wellmeadowsrestapi.services.DivisionServiceImp
import seg3102.wellmeadowsrestapi.services.DoctorServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class DoctorController {

    @Autowired
    val doctorServices = DoctorServiceImp()

    @Autowired
    val doctorAssembler = DoctorModelAssembler()

    @Operation(summary = "Get all Doctors")
    @GetMapping("/doctors")
    fun getDoctors(): ResponseEntity<CollectionModel<DoctorRepresentation>> {
        val doctors = doctorServices.getDoctors()
        return ResponseEntity(
            doctorAssembler.toCollectionModel(doctors),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Doctor by his id")
    @GetMapping("/doctors/{id}")
    fun getDoctorById(@PathVariable("id") id: Long): ResponseEntity<DoctorRepresentation> {
        val doctor = doctorServices.getDoctorById(id)
        return doctor
            .map { entity: Doctor -> doctorAssembler.toModel(entity) }
            .map { body: DoctorRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Patients for a Doctor")
    @GetMapping("/doctors/{id}/patients")
    fun getDoctorPatientsById(@PathVariable("id") id: Long): ResponseEntity<List<PatientNameRepresentation>> {
        return doctorServices.getDoctorById(id)
            .map { doctor: Doctor -> doctorAssembler.toPatientsRepresentation(doctor.patients) }
            .map { body: List<PatientNameRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Prescriptions for a Doctor")
    @GetMapping("/doctors/{id}/prescriptions")
    fun getDoctorPrescriptionsById(@PathVariable("id") id: Long): ResponseEntity<List<PrescriptionNameRepresentation>> {
        return doctorServices.getDoctorById(id)
            .map { doctor: Doctor -> doctorAssembler.toPrescriptionsRepresentation(doctor.prescriptions) }
            .map { body: List<PrescriptionNameRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Division by its id")
    @DeleteMapping("/doctors/delete/{id}")
    fun deleteDoctorById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            doctorServices.deleteDoctor(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Divisions in DB")
    @DeleteMapping("/doctors/delete")
    fun deleteDoctors(): ResponseEntity<Any> {
        return try {
            doctorServices.deleteAllDoctors()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Division")
    @PostMapping("/doctors")
    fun addDoctor(@RequestBody doctor: Doctor): ResponseEntity<Any> {
        return try {
            val newDoctor = doctorServices.addDoctor(doctor)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDoctor.userId)
                .toUri()
            ResponseEntity.created(location).body(doctorAssembler.toModel(newDoctor))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Division")
    @PutMapping("/doctors/{id}")
    fun updateDoctor(@PathVariable("id") id: Long, @RequestBody doctor: Doctor): ResponseEntity<Any> {
        return try {
            doctorServices.updateDoctor(id, doctor)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}