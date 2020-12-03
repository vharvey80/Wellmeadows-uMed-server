package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*
import javax.validation.constraints.*

@Entity
class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var medicationId: Long      = 0
    var medicationName: String  = ""
    var unitsByDay: Int         = 0
    var methodOfAdmin: String   = ""
    var startDate: String       = ""

    @ManyToMany
    @JsonIgnoreProperties("prescriptions")
    var patients: MutableList<Patient> = ArrayList()

    @ManyToMany
    @JsonIgnoreProperties("prescriptions")
    var doctors: MutableList<Doctor> = ArrayList()
}