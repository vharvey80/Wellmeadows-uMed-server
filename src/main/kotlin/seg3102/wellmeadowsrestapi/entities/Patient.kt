package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.print.Doc

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

    constructor(fName: String,
                lName: String,
                g: String) {
        this.firstName = fName
        this.lastName = lName
        this.gender = g
    }

    @OneToOne(optional = true)
    @JoinColumn(name = "contact_id")
    @JsonManagedReference
    var patientContact: PatientContact? = null

    @OneToOne(optional = true)
    @JoinColumn(name = "division_file_id")
    @JsonManagedReference
    var divisionAdmissionFile: DivisionAdmissionFile? = null

    @OneToOne(optional = true)
    @JoinColumn(name = "hospital_file_id")
    @JsonManagedReference
    var hospitalAdmissionFile: HospitalAdmissionFile? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "doctor_id")
    var doctor: Doctor? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "division_id")
    var division: Division? = null

    @ManyToMany(cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                mappedBy = "patients")
    @JsonIgnoreProperties("patients")
    var prescriptions: MutableList<Prescription> = ArrayList()
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
}