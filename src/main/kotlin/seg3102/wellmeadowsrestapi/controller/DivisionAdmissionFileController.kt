package seg3102.wellmeadowsrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3102.wellmeadowsrestapi.assemblers.DivisionAdmissionFileModelAssembler
import seg3102.wellmeadowsrestapi.entities.DivisionAdmissionFile
import seg3102.wellmeadowsrestapi.representation.DivisionAdmissionFileRepresentation
import seg3102.wellmeadowsrestapi.services.DivisionAdmissionFileServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class DivisionAdmissionFileController {

    @Autowired
    val divisionFileServices = DivisionAdmissionFileServiceImp()

    @Autowired
    val divisionFileAssembler = DivisionAdmissionFileModelAssembler()

    @Operation(summary = "Get all Division Admission Files")
    @GetMapping("/divisionFiles")
    fun getDivisionAdmissionFiles(): ResponseEntity<CollectionModel<DivisionAdmissionFileRepresentation>> {
        val patients = divisionFileServices.getDivisionFiles()
        return ResponseEntity(
            divisionFileAssembler.toCollectionModel(patients),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Division Admission File by its id")
    @GetMapping("/divisionFiles/{id}")
    fun getDivisionAdmissionFileById(@PathVariable("id") id: Long): ResponseEntity<DivisionAdmissionFileRepresentation> {
        val patient = divisionFileServices.getDivisionFileById(id)
        return patient
            .map { entity: DivisionAdmissionFile -> divisionFileAssembler.toModel(entity) }
            .map { body: DivisionAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Division Admission File by his id")
    @DeleteMapping("/divisionFiles/delete/{id}")
    fun deleteDivisionAdmissionFileById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            divisionFileServices.deleteDivisionFile(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Patients in DB")
    @DeleteMapping("/divisionFiles/delete")
    fun deleteDivisionAdmissionFiles(): ResponseEntity<Any> {
        return try {
            divisionFileServices.deleteAllDivisionFiles()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Division Admission File")
    @PostMapping("/divisionFiles")
    fun addDivisionAdmissionFile(@RequestBody divFile: DivisionAdmissionFile): ResponseEntity<Any> {
        return try {
            val newDivFile = divisionFileServices.addDivisionFile(divFile)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDivFile.divisionFileId)
                .toUri()
            ResponseEntity.created(location).body(divisionFileAssembler.toModel(newDivFile))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Division Admission File")
    @PutMapping("/divisionFiles/{id}")
    fun updateDivisionAdmissionFile(@PathVariable("id") id: Long, @RequestBody divFile: DivisionAdmissionFile): ResponseEntity<Any> {
        return try {
            divisionFileServices.updateDivisionFile(id, divFile)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}