package ua.kpi.its.lab.rest.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "mobile_phones")
class MobilePhone(
    @Column var brand: String,
    @Column var manufacturer: String,
    @Column var model: String,
    @Column var memory: Int,
    @Column var price: Double,
    @Column var productionDate: Date,
    @Column var dualSim: Boolean,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "phone", orphanRemoval = true, fetch = FetchType.LAZY)  // fetch = FetchType.LAZY
    var files: MutableList<File>
) : Comparable<MobilePhone> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: MobilePhone): Int {
        return this.brand.compareTo(other.brand)
    }

    override fun toString(): String {
        return "MobilePhone(brand=$brand, model=$model)"
    }
}

@Entity
@Table(name = "files")
class File(
    @Column var name: String,
    @Column var extension: String,
    @Column var size: Double,
    @Column var creationDate: Date,
    @Column var isPhoto: Boolean,
    @ManyToOne @JoinColumn(name = "phone_id") var phone: MobilePhone? = null
) : Comparable<File> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: File): Int {
        return this.name.compareTo(other.name)
    }

    override fun toString(): String {
        return "File(name=$name, extension=$extension)"
    }
}