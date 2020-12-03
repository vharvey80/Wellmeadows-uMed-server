package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.*

@Entity
class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var divisionId: Long        = 0
    var divisionName: String    = ""
    var location: String        = ""
    var numberOfBeds: Int       = 0
    var phoneExtension: String  = ""
    var status: String          = ""

    constructor() {}
    constructor(name: String) { this.divisionName = name }

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "division")
    var patients: MutableList<Patient> = ArrayList()

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "division")
    var divisionAdmissionFiles: MutableList<DivisionAdmissionFile> = ArrayList()
}