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

    var patientContact: PatientContactRepresentation = PatientContactRepresentation()
    var divisionFile: DivisionAdmissionFileRepresentation = DivisionAdmissionFileRepresentation()
    var hospitalFile: HospitalAdmissionFileRepresentation = HospitalAdmissionFileRepresentation()
    var doctor: UserNameRepresentation = UserNameRepresentation()
    var division: DivisionNameRepresentation = DivisionNameRepresentation()
    var prescriptions: List<PrescriptionNameRepresentation> = ArrayList<PrescriptionNameRepresentation>()
}

@Relation(collectionRelation = "patientContacts")
@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientContactRepresentation: RepresentationModel<PatientContactRepresentation>() {
    var firstName: String = ""
    var lastName: String = ""
    var relationship: String = ""
}

@JsonInclude(JsonInclude.Include.NON_NULL)
class PatientNameRepresentation: RepresentationModel<PatientNameRepresentation>() {
    var firstName: String = ""
    var lastName: String = ""
}

