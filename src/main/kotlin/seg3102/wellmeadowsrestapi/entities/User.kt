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

    constructor()
    constructor(fName: String, lName: String, pWord: String, email: String) {
        this.firstName = fName
        this.lastName = lName
        this.password = pWord
        this.email = email
    }
}

@Entity
class Doctor : User {
    var phoneExtension: String = ""

    constructor() : super()
    constructor(fName: String, lName: String, pWord: String, email: String, pExtension: String) :
            super(fName, lName, pWord, email) {
        this.phoneExtension = pExtension
    }

    @OneToMany(fetch = FetchType.LAZY)
    var patients: MutableList<Patient> = ArrayList()

    @OneToMany(fetch = FetchType.LAZY)
    var prescriptions: MutableList<Prescription> = ArrayList()
}

@Entity
class Nurse : User {
    var phoneExtension: String = ""

    constructor() : super()
    constructor(fName: String, lName: String, pWord: String, email: String, pExtension: String) : super(fName, lName, pWord, email) {
        this.phoneExtension = pExtension
    }

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", referencedColumnName = "divisionId")
    var division: Division = Division()
}