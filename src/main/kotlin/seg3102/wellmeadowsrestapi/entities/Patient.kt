package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.sun.istack.Nullable
import javax.persistence.*

@Entity
class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var patientId: Long         = 0
    var firstName: String       = ""
    var lastName: String        = ""
    var address: String         = ""
    var phoneNumber: String     = ""
    var dateOfBirth: String     = ""
    var gender: String          = ""
    var maritalStatus: String   = ""

    constructor()
    constructor(fName: String, lName: String, gender: String, dateOfBirth: String, status: String, contact: PatientContact) {
        this.firstName = fName
        this.lastName = lName
        this.dateOfBirth = dateOfBirth
        this.maritalStatus = status
        this.gender = gender

        this.patientContact = contact
    }

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "contact_id", referencedColumnName = "contactId")
    @JsonManagedReference
    var patientContact: PatientContact = PatientContact()

    @OneToOne
    @JoinColumn(name = "division_file_id", referencedColumnName = "divisionFileId")
    @JsonManagedReference
    var divisionAdmissionFile: DivisionAdmissionFile? = null

    @OneToOne
    @JoinColumn(name = "hospital_file_id", referencedColumnName = "hospitalFileId")
    @JsonManagedReference
    var hospitalAdmissionFile: HospitalAdmissionFile? = null

    @OneToMany(mappedBy = "patient")
    var prescriptions: MutableList<Prescription> = ArrayList()

    @Nullable
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("divisionId")
    var division: Division? = null


    @Nullable
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @MapsId("userId")
    var doctor: Doctor = Doctor()
}

@Entity
class PatientContact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var contactId: Long         = 0
    var firstName: String       = ""
    var lastName: String        = ""
    var relationship: String    = ""
    var address: String         = ""
    var phoneNumber: String     = ""

    constructor()
    constructor(fName: String, lName: String, rel: String) {
        this.firstName = fName
        this.lastName = lName
        this.relationship = rel
    }

    @OneToOne
    var patient: Patient? = null
}