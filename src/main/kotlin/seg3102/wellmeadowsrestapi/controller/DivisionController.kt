package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3102.wellmeadowsrestapi.assemblers.DivisionModelAssembler
import seg3102.wellmeadowsrestapi.entities.Division
import seg3102.wellmeadowsrestapi.representation.DivisionAdmissionFileRepresentation
import seg3102.wellmeadowsrestapi.representation.DivisionRepresentation
import seg3102.wellmeadowsrestapi.representation.PatientNameRepresentation
import seg3102.wellmeadowsrestapi.services.DivisionServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class DivisionController {

    @Autowired
    val divisionServices = DivisionServiceImp()

    @Autowired
    val divisionAssembler = DivisionModelAssembler()

    @Operation(summary = "Get all Divisions")
    @GetMapping("/divisions")
    fun getDivisions(): ResponseEntity<CollectionModel<DivisionRepresentation>> {
        val divisions = divisionServices.getDivisions()
        return ResponseEntity(
            divisionAssembler.toCollectionModel(divisions),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Division by its id")
    @GetMapping("/divisions/{id}")
    fun getDivisionById(@PathVariable("id") id: Long): ResponseEntity<DivisionRepresentation> {
        val division = divisionServices.getDivisionById(id)
        return division
            .map { entity: Division -> divisionAssembler.toModel(entity) }
            .map { body: DivisionRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Admission Files for a Division")
    @GetMapping("/divisions/{id}/files")
    fun getDivisionAdmissionFilesById(@PathVariable("id") id: Long): ResponseEntity<List<DivisionAdmissionFileRepresentation>> {
        return divisionServices.getDivisionById(id)
            .map { division: Division -> divisionAssembler.toFilesRepresentation(division.divisionAdmissionFiles) }
            .map { body: List<DivisionAdmissionFileRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Get all Patients for a Division")
    @GetMapping("/divisions/{id}/patients")
    fun getDivisionPatientsById(@PathVariable("id") id: Long): ResponseEntity<List<PatientNameRepresentation>> {
        return divisionServices.getDivisionById(id)
            .map { division: Division -> divisionAssembler.toPatientsRepresentation(division.patients) }
            .map { body: List<PatientNameRepresentation> -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Division by its id")
    @DeleteMapping("/divisions/delete/{id}")
    fun deleteDivisionById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            divisionServices.deleteDivision(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Divisions in DB")
    @DeleteMapping("/divisions/delete")
    fun deleteDivisions(): ResponseEntity<Any> {
        return try {
            divisionServices.deleteAllDivisions()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Division")
    @PostMapping("/divisions")
    fun addDivision(@RequestBody division: Division): ResponseEntity<Any> {
        return try {
            val newDivision = divisionServices.addDivision(division)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDivision.divisionId)
                .toUri()
            ResponseEntity.created(location).body(divisionAssembler.toModel(newDivision))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Division")
    @PutMapping("/divisions/{id}")
    fun updateDivision(@PathVariable("id") id: Long, @RequestBody division: Division): ResponseEntity<Any> {
        return try {
            divisionServices.updateDivision(id, division)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

}