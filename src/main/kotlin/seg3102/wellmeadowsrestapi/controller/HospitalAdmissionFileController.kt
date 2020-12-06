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
import seg3102.wellmeadowsrestapi.entities.DivisionAdmissionFile
import seg3102.wellmeadowsrestapi.entities.HospitalAdmissionFile
import seg3102.wellmeadowsrestapi.representation.DivisionAdmissionFileRepresentation
import seg3102.wellmeadowsrestapi.representation.HospitalAdmissionFileRepresentation
import seg3102.wellmeadowsrestapi.services.DivisionAdmissionFileServiceImp
import seg3102.wellmeadowsrestapi.services.HospitalAdmissionFileServiceImp
import java.lang.Exception
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("uMed-api", produces = ["application/hal+json"])
class HospitalAdmissionFileController {

    @Autowired
    val hospitalFileServices = HospitalAdmissionFileServiceImp()

    @Autowired
    val hospitalFileAssembler = HospitalAdmissionFileModelAssembler()

    @Operation(summary = "Get all Hospital Admission Files")
    @GetMapping("/hospitalFiles")
    fun getHospitalAdmissionFiles(): ResponseEntity<CollectionModel<HospitalAdmissionFileRepresentation>> {
        val patients = hospitalFileServices.getHospitalFiles()
        return ResponseEntity(
            hospitalFileAssembler.toCollectionModel(patients),
            HttpStatus.OK)
    }

    @Operation(summary = "Get a Hospital Admission File by its id")
    @GetMapping("/hospitalFiles/{id}")
    fun getHospitalAdmissionFileById(@PathVariable("id") id: Long): ResponseEntity<HospitalAdmissionFileRepresentation> {
        val patient = hospitalFileServices.getHospitalFileById(id)
        return patient
            .map { entity: HospitalAdmissionFile -> hospitalFileAssembler.toModel(entity) }
            .map { body: HospitalAdmissionFileRepresentation -> ResponseEntity.ok(body) }
            .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Delete a Hospital Admission File by his id")
    @DeleteMapping("/hospitalFiles/delete/{id}")
    fun deleteHospitalAdmissionFileById(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            hospitalFileServices.deleteHospitalFile(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Delete all Hospital Admission Files in DB")
    @DeleteMapping("/hospitalFiles/delete")
    fun deleteHospitalAdmissionFiles(): ResponseEntity<Any> {
        return try {
            hospitalFileServices.deleteAllHospitalFiles()
            ResponseEntity.noContent().build<Any>()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Add a new Hospital Admission File")
    @PostMapping("/hospitalFiles")
    fun addHospitalAdmissionFile(@RequestBody hosFile: HospitalAdmissionFile): ResponseEntity<Any> {
        return try {
            val newHosFile = hospitalFileServices.addHospitalFile(hosFile)
            val location: URI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newHosFile.hospitalFileId)
                .toUri()
            ResponseEntity.created(location).body(hospitalFileAssembler.toModel(newHosFile))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update an existing Hospital Admission File")
    @PutMapping("/hospitalFiles/{id}")
    fun updateHospitalAdmissionFile(@PathVariable("id") id: Long, @RequestBody hosFile: HospitalAdmissionFile): ResponseEntity<Any> {
        return try {
            hospitalFileServices.updateHospitalFile(id, hosFile)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.badRequest().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}