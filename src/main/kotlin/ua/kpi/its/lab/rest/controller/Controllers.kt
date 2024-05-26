package ua.kpi.its.lab.rest.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.kpi.its.lab.rest.dto.MobilePhoneRequest
import ua.kpi.its.lab.rest.dto.MobilePhoneResponse
import ua.kpi.its.lab.rest.svc.MobilePhoneService

@RestController
@RequestMapping("/phones")
class MobilePhoneController @Autowired constructor(
    private val mobilePhoneService: MobilePhoneService
) {

    /**
     * Gets the list of all mobile phones
     *
     * @return: List of MobilePhoneResponse
     */
    @GetMapping(path = ["", "/"])
    fun phones(): List<MobilePhoneResponse> = mobilePhoneService.read()

    /**
     * Reads the mobile phone by its id
     *
     * @param id: id of the mobile phone
     * @return: MobilePhoneResponse for the given id
     */
    @GetMapping("{id}")
    fun readPhone(@PathVariable("id") id: Long): ResponseEntity<MobilePhoneResponse> {
        return try {
            val phone = mobilePhoneService.readById(id)
            ResponseEntity.ok(phone)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Creates a new mobile phone instance
     *
     * @param phone: MobilePhoneRequest with set properties
     * @return: MobilePhoneResponse for the created phone
     */
    @PostMapping(path = ["", "/"])
    fun createPhone(@RequestBody phone: MobilePhoneRequest): MobilePhoneResponse {
        return mobilePhoneService.create(phone)
    }

    /**
     * Updates an existing mobile phone instance
     *
     * @param id: Id of the mobile phone to update
     * @param phone: MobilePhoneRequest with properties set
     * @return: MobilePhoneResponse of the updated phone
     */
    @PutMapping("{id}")
    fun updatePhone(
        @PathVariable("id") id: Long,
        @RequestBody phone: MobilePhoneRequest
    ): ResponseEntity<MobilePhoneResponse> {
        return try {
            val updatedPhone = mobilePhoneService.updateById(id, phone)
            ResponseEntity.ok(updatedPhone)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Deletes an existing mobile phone instance
     *
     * @param id: id of the mobile phone
     * @return: MobilePhoneResponse of the deleted phone
     */
    @DeleteMapping("{id}")
    fun deletePhone(
        @PathVariable("id") id: Long
    ): ResponseEntity<MobilePhoneResponse> {
        return try {
            val deletedPhone = mobilePhoneService.deleteById(id)
            ResponseEntity.ok(deletedPhone)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }
}