package ua.kpi.its.lab.rest.repo

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import ua.kpi.its.lab.rest.entity.MobilePhone
import java.util.*

interface MobilePhoneRepository : JpaRepository<MobilePhone, Long> {
    @EntityGraph(attributePaths = ["files"])
    override fun findAll(): List<MobilePhone>

    @EntityGraph(attributePaths = ["files"])
    override fun findById(id: Long): Optional<MobilePhone>
}