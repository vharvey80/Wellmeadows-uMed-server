package seg3102.wellmeadowsrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class UserRepresentation<T>: RepresentationModel<UserRepresentation<T>>() {
    var userId: Long = 0
    var password: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var phoneNumber: String = ""
}

@Relation(collectionRelation = "doctors")
@JsonInclude(JsonInclude.Include.NON_NULL)
class DoctorRepresentation: UserRepresentation<DoctorRepresentation>() {
    var phoneExtension: String = ""

    var patients: List<PatientNameRepresentation> = ArrayList<PatientNameRepresentation>()
    var prescriptions: List<PrescriptionNameRepresentation> = ArrayList<PrescriptionNameRepresentation>()
}

@Relation(collectionRelation = "nurses")
@JsonInclude(JsonInclude.Include.NON_NULL)
class NurseRepresentation: UserRepresentation<NurseRepresentation>() {
    var phoneExtension: String = ""

    var division: DivisionRepresentation = DivisionRepresentation()
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserNameRepresentation: RepresentationModel<UserNameRepresentation>() {
    var firstName: String = ""
    var lastName: String = ""
}