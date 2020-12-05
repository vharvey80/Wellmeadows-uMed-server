package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.*
import javax.persistence.FetchType




@Entity
class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var medicationId: Long      = 0
    var medicationName: String  = ""
    var unitsByDay: Int         = 0
    var methodOfAdmin: String   = ""
    var startDate: String       = ""

    constructor() {}
    constructor(name: String, unitsByDay: Int, method: String) {
        this.medicationName = name
        this.unitsByDay = unitsByDay
        this.methodOfAdmin = method
        this.startDate = LocalDateTime.now().toString()
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    var doctor: Doctor = Doctor()

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("patientId")
    var patient: Patient = Patient()

}