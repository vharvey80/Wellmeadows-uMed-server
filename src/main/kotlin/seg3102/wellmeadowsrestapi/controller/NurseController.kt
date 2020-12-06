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
import seg3102.wellmeadowsrestapi.assemblers.NurseModelAssembler
import seg3102.wellmeadowsrestapi.entities.Division
import seg3102.wellmeadowsrestapi.entities.Doctor
import seg3102.wellmeadowsrestapi.entities.Nurse
import seg3102.wellmeadowsrestapi.representation.DivisionRepresentation
import seg3102.wellmeadowsrestapi.representation.DoctorRepresentation
import seg3102.wellmeadowsrestapi.representation.NurseRepresentation
import seg3102.wellmeadowsrestapi.services.DivisionServiceImp
import seg3102.wellmeadowsrestapi.services.DoctorServiceImp
import seg3102.wellmeadowsrestapi.services.NurseService
import seg3102.wellmeadowsrestapi.services.NurseServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class NurseController {

    @Autowired
    val nurseServices = NurseServiceImp()

    @Autowired
    val nurseAssembler = NurseModelAssembler()

    @Autowired
    val divisionAssembler = DivisionModelAssembler()

    @Operation(summary = "Get all Nurses")
    @GetMapping("/nurses")
    fun getNurses(): ResponseEntity<CollectionModel<NurseRepresentation>> {
        val doctors = nurseServices.getNurses()
        return ResponseEntity(
            nurseAssembler.toCollectionModel(doctors),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Nurse by his id")
    @GetMapping("/nurses/{id}")
    fun getNurseById(@PathVariable("id") id: Long): ResponseEntity<NurseRepresentation> {
        val doctor = nurseServices.getNurseById(id)
        return doctor
            .map { entity: Nurse -> nurseAssembler.toModel(entity) }
            .map { body: NurseRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get an nurse's assigned division")
    @GetMapping("/nurses/{id}/division")
    fun getNurseDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        return nurseServices.getNurseById(id)
            .map { nurse: Nurse ->  divisionAssembler.toModel(nurse.division)}
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Nurse by his id")
    @DeleteMapping("/nurses/delete/{id}")
    fun deleteNurseById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            nurseServices.deleteNurse(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Nurses in DB")
    @DeleteMapping("/nurses/delete")
    fun deleteNurses(): ResponseEntity<Any> {
        return try {
            nurseServices.deleteAllNurses()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Nurse")
    @PostMapping("/nurses")
    fun addNurse(@RequestBody nurse: Nurse): ResponseEntity<Any> {
        return try {
            val newNurse = nurseServices.addNurse(nurse)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newNurse.userId)
                .toUri()
            ResponseEntity.created(location).body(nurseAssembler.toModel(newNurse))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Nurse")
    @PutMapping("/nurses/{id}")
    fun updateNurse(@PathVariable("id") id: Long, @RequestBody nurse: Nurse): ResponseEntity<Any> {
        return try {
            nurseServices.updateNurse(id, nurse)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}