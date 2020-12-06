package seg3102.wellmeadowsrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "patients")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientRepresentation: RepresentationModel<PatientRepresentation>() {
    var patientId: Long = 0
    var firstName: String = ""
    var lastName: String = ""
    var address: String = ""
    var phoneNumber: String = ""
    var dateOfBirth: String = ""
    var gender: String = ""
    var maritalStatus: String = ""

    var prescriptions: List<PrescriptionNameRepresentation> = ArrayList()
}

@Relation(collectionRelation = "patientContacts")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientContactRepresentation: RepresentationModel<PatientContactRepresentation>() {
    var contactId: Long = 0
    var firstName: String = ""
    var lastName: String = ""
    var relationship: String = ""
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientNameRepresentation: RepresentationModel<PatientNameRepresentation>() {
    var firstName: String = ""
    var lastName: String = ""
}

