package seg3102.wellmeadowsrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "hospital_admission_files")
@JsonInclude(JsonInclude.Include.NON_NULL)
class HospitalAdmissionFileRepresentation: RepresentationModel<HospitalAdmissionFileRepresentation>() {
    var hospitalFileId: Long = 0
    var bedNumber: Int = 0
    var privateInsuranceNumber: Int = 0

    var patient: PatientNameRepresentation = PatientNameRepresentation()
}

@Relation(collectionRelation = "division_admission_files")
@JsonInclude(JsonInclude.Include.NON_NULL)
class DivisionAdmissionFileRepresentation: RepresentationModel<DivisionAdmissionFileRepresentation>() {
    var divisionFileId: Long = 0
    var priority: Int = 0
    var requestRationale: String = ""

    var patient: PatientNameRepresentation = PatientNameRepresentation()
    var division: DivisionNameRepresentation = DivisionNameRepresentation()
}