package seg3102.wellmeadowsrestapi.entities

import javax.persistence.*

@Entity
class HospitalAdmissionFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var hospitalFileId: Long        = 0
    var bedNumber: Int              = 0
    var privateInsuranceNumber: Int = 0

    constructor()
    constructor(bedNumber: Int, privateInsNum: Int) {
        this.bedNumber = bedNumber
        this.privateInsuranceNumber = privateInsNum
    }
}

@Entity
class DivisionAdmissionFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var divisionFileId: Long        = 0
    var requestRationale: String    = ""
    var priority: Int               = 0

    constructor()
    constructor(rationale: String, priority: Int) {
        this.requestRationale = rationale
        this.priority = priority
    }
}