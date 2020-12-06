package seg3102.wellmeadowsrestapi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import seg3102.wellmeadowsrestapi.entities.PatientContact
import seg3102.wellmeadowsrestapi.repository.PatientContactRepository
import java.util.*

interface ContactService {
    fun addContact(contact: PatientContact): PatientContact
    fun deleteContact(id: Long)
    fun deleteAllContacts()
    fun getContactById(id: Long): Optional<PatientContact>
    fun getContacts(): MutableIterable<PatientContact>?
    fun updateContact(id: Long, contact: PatientContact): PatientContact
}

@Service
class ContactServiceImp: ContactService {

    @Autowired
    lateinit var contactRepository: PatientContactRepository

    override fun addContact(contact: PatientContact): PatientContact = contactRepository.save(contact)

    override fun deleteContact(id: Long) = contactRepository.deleteById(id)

    override fun deleteAllContacts() = contactRepository.deleteAll()

    override fun getContactById(id: Long): Optional<PatientContact> = contactRepository.findById(id)

    override fun getContacts(): MutableIterable<PatientContact> = contactRepository.findAll()

    override fun updateContact(id: Long, contact: PatientContact): PatientContact {
        val currentPatient = contactRepository.findById(id).get()

        currentPatient.firstName = contact.firstName
        currentPatient.lastName = contact.lastName
        currentPatient.address = contact.address
        currentPatient.phoneNumber = contact.phoneNumber
        currentPatient.relationship = contact.relationship

        return contactRepository.save(currentPatient)
    }
}