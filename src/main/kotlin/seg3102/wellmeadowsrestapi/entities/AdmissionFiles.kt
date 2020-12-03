package seg3102.wellmeadowsrestapi.entities

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class HospitalAdmissionFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var hospitalFileId: Long        = 0
    var bedNumber: Int              = 0
    var privateInsuranceNumber: Int = 0

    @ManyToOne
    var doctor: Doctor = Doctor()
}

@Entity
class DivisionAdmissionFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var divisionFileId: Long        = 0
    var requestRationale: String    = ""
    var priority: Int               = 0

    @ManyToOne
    var doctor: Doctor = Doctor()

    @ManyToOne
    var division: Division = Division()
}