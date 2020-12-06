package seg3102.wellmeadowsrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import java.util.*
import kotlin.collections.ArrayList

@Relation(collectionRelation = "prescriptions")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PrescriptionRepresentation: RepresentationModel<PrescriptionRepresentation>() {
    var medicationId: Long = 0
    var medicationName: String = ""
    var unitsByDay: Int = 0
    var methodOfAdmin: String = ""
    var startDate: String = ""
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class PrescriptionNameRepresentation: RepresentationModel<PrescriptionNameRepresentation>() {
    var medicationName: String = ""
    var startDate: String = ""
}