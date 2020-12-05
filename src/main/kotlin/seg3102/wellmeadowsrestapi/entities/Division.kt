package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import org.hibernate.engine.internal.Cascade
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
    constructor(name: String, location: String, numberOfBeds: Int, status: String, nurse: Nurse) {
        this.divisionName = name
        this.location = location
        this.numberOfBeds = numberOfBeds
        this.status = status

        this.nurse = nurse
    }

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "division")
    var patients: MutableList<Patient> = ArrayList()

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "division")
    var divisionAdmissionFiles: MutableList<DivisionAdmissionFile> = ArrayList()

    @OneToOne(mappedBy = "division")
    var nurse: Nurse? = Nurse()
}