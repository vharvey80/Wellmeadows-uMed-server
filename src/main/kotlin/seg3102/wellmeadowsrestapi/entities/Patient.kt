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
    constructor(fName: String, lName: String, gender: String, dateOfBirth: String, status: String) {
        this.firstName = fName
        this.lastName = lName
        this.dateOfBirth = dateOfBirth
        this.maritalStatus = status
        this.gender = gender
    }

    /** ONE TO ONE RELATIONSHIP **/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", referencedColumnName = "contactId")
    var patientContact: PatientContact = PatientContact()

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "division_file_id", referencedColumnName = "divisionFileId")
    var divisionAdmissionFile: DivisionAdmissionFile = DivisionAdmissionFile()

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_file_id", referencedColumnName = "hospitalFileId")
    var hospitalAdmissionFile: HospitalAdmissionFile = HospitalAdmissionFile()

    /** ONE TO MANY RELATIONSHIP **/
    @OneToMany(fetch = FetchType.LAZY)
    var prescriptions: MutableList<Prescription> = ArrayList()

    /** MANY TO ONE RELATIONSHIP **/
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id")
    var division: Division = Division()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    var doctor: Doctor = Doctor()*/
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
}