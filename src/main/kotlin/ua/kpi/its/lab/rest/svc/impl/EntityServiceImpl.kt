package ua.kpi.its.lab.rest.svc.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.dto.MobilePhoneRequest
import ua.kpi.its.lab.rest.dto.MobilePhoneResponse
import ua.kpi.its.lab.rest.dto.FileResponse
import ua.kpi.its.lab.rest.dto.FileRequest
import ua.kpi.its.lab.rest.entity.File
import ua.kpi.its.lab.rest.entity.MobilePhone
import ua.kpi.its.lab.rest.repo.MobilePhoneRepository
import ua.kpi.its.lab.rest.svc.MobilePhoneService
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class MobilePhoneServiceImpl @Autowired constructor(
    private val repository: MobilePhoneRepository
) : MobilePhoneService {
    override fun create(phone: MobilePhoneRequest): MobilePhoneResponse {
        val files = phone.files.map { file ->
            File(
                name = file.name,
                extension = file.extension,
                size = file.size,
                creationDate = this.stringToDate(file.creationDate),
                isPhoto = file.isPhoto
            )
        }.toMutableList()

        var newPhone = MobilePhone(
            brand = phone.brand,
            manufacturer = phone.manufacturer,
            model = phone.model,
            memory = phone.memory,
            price = phone.price,
            productionDate = this.stringToDate(phone.productionDate),
            dualSim = phone.dualSim,
            files = files
        )

        newPhone = this.repository.save(newPhone)
        return phoneEntityToDto(newPhone)
    }

    override fun read(): List<MobilePhoneResponse> {
        return repository.findAll().map { phoneEntityToDto(it) }
    }

    override fun readById(id: Long): MobilePhoneResponse {
        val phone = getPhoneById(id)
        return phoneEntityToDto(phone)
    }

    override fun updateById(id: Long, phone: MobilePhoneRequest): MobilePhoneResponse {
        val oldPhone = getPhoneById(id)
        val updatedFiles = phone.files.map { file ->
            File(
                name = file.name,
                extension = file.extension,
                size = file.size,
                creationDate = stringToDate(file.creationDate),
                isPhoto = file.isPhoto,
                phone = oldPhone
            )
        }

        oldPhone.apply {
            brand = phone.brand
            manufacturer = phone.manufacturer
            model = phone.model
            memory = phone.memory
            price = phone.price
            productionDate = stringToDate(phone.productionDate)
            dualSim = phone.dualSim
            files.clear()
            files.addAll(updatedFiles)
        }

        val newPhone = repository.save(oldPhone)
        return phoneEntityToDto(newPhone)
    }

    override fun deleteById(id: Long): MobilePhoneResponse {
        val phone = getPhoneById(id)
        repository.delete(phone)
        return phoneEntityToDto(phone)
    }

    private fun getPhoneById(id: Long): MobilePhone {
        return repository.findById(id).orElseThrow { IllegalArgumentException("Phone not found by id = $id") }
    }

    private fun phoneEntityToDto(phone: MobilePhone): MobilePhoneResponse {
        return MobilePhoneResponse(
            id = phone.id,
            brand = phone.brand,
            manufacturer = phone.manufacturer,
            model = phone.model,
            memory = phone.memory,
            price = phone.price,
            productionDate = dateToString(phone.productionDate),
            dualSim = phone.dualSim,
            files = phone.files.map { fileEntityToDto(it) }
        )
    }

    private fun fileEntityToDto(file: File): FileResponse {
        return FileResponse(
            id = file.id,
            name = file.name,
            extension = file.extension,
            size = file.size,
            creationDate = dateToString(file.creationDate),
            isPhoto = file.isPhoto
        )
    }

    private fun dateToString(date: Date): String {
        val instant = date.toInstant()
        val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    private fun stringToDate(date: String): Date {
        val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
        val instant = dateTime.toInstant(ZoneOffset.UTC)
        return Date.from(instant)
    }
}