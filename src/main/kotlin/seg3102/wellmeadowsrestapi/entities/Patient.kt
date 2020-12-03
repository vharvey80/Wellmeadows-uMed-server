package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var patientId: Long         = 0
    @NotBlank
    var firstName: String       = ""
    var lastName: String        = ""
    var address: String         = ""
    var phoneNumber: String     = ""
    var dateOfBirth: String     = ""
    var gender: String          = ""
    var maritalStatus: String   = ""

    @OneToOne
    @MapsId
    @JoinColumn(name = "contact_id")
    @JsonManagedReference
    var patientContact: PatientContact = PatientContact()

    @OneToOne
    @MapsId
    @JoinColumn(name = "division_file_id")
    @JsonManagedReference
    var divisionAdmissionFile: DivisionAdmissionFile =  DivisionAdmissionFile()

    @OneToOne
    @MapsId
    @JoinColumn(name = "hospital_file_id")
    @JsonManagedReference
    var hospitalAdmissionFile: HospitalAdmissionFile = HospitalAdmissionFile()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id")
    var doctor: Doctor = Doctor()

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "division_id")
    var division: Division = Division()

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