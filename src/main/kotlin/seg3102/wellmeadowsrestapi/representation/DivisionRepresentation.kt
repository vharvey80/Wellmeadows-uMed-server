package seg3102.wellmeadowsrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude
import net.minidev.json.JSONArray
import org.springframework.hateoas.RepresentationModel

@JsonInclude(JsonInclude.Include.NON_NULL)
class DivisionRepresentation: RepresentationModel<DivisionRepresentation>() {
    var divisionId: Long = 0
    var divisionName: String = ""
    var location: String = ""
    var numberOfBeds: Int = 0
    var phoneExtension: String = ""
    var status: String = ""

    var patients : List<PatientNameRepresentation> = ArrayList<PatientNameRepresentation>()
    var divisionFiles: List<DivisionAdmissionFileRepresentation> = ArrayList<DivisionAdmissionFileRepresentation>()
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class DivisionNameRepresentation: RepresentationModel<DivisionNameRepresentation>() {
    var divisionName: String = ""
    var location: String = ""
}