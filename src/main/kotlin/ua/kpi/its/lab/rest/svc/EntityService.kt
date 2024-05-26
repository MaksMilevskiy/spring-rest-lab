package ua.kpi.its.lab.rest.svc

import ua.kpi.its.lab.rest.dto.MobilePhoneRequest
import ua.kpi.its.lab.rest.dto.MobilePhoneResponse

interface MobilePhoneService {
    fun create(phone: MobilePhoneRequest): MobilePhoneResponse
    fun read(): List<MobilePhoneResponse>
    fun readById(id: Long): MobilePhoneResponse
    fun updateById(id: Long, phone: MobilePhoneRequest): MobilePhoneResponse
    fun deleteById(id: Long): MobilePhoneResponse
}