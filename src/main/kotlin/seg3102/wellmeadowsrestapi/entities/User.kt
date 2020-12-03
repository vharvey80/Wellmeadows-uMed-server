package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.*

@Entity
@Inheritance
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var userId: Long            = 0
    open var password: String        = ""
    open var firstName: String       = ""
    open var lastName: String        = ""
    open var email: String           = ""
    open var phoneNumber: String     = ""

    constructor() {}
    constructor(fName: String) { this.firstName = fName }
}

@Entity
class Doctor : User {
    var phoneExtension: String = ""

    constructor() : super() {}
    constructor(fName: String) : super(fName) {}

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "doctor")
    var hospitalAdmissionFiles: MutableList<HospitalAdmissionFile> = ArrayList()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "doctor")
    var divisionAdmissionFiles: MutableList<DivisionAdmissionFile> = ArrayList()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "doctor")
    var patients: MutableList<Patient> = ArrayList()

    @ManyToMany(cascade = [CascadeType.ALL],
                fetch = FetchType.LAZY,
                mappedBy = "doctors")
    @JsonIgnoreProperties("doctors")
    var prescriptions: MutableList<Prescription> = ArrayList()
}

@Entity
class Nurse : User {
    var phoneExtension: String = ""

    constructor() : super() {}
    constructor(fName: String) : super(fName) {}

    @OneToOne(optional = true)
    @JoinColumn(name = "division_id")
    @JsonManagedReference
    var division: Division = Division()
}