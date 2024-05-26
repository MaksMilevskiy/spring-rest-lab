package ua.kpi.its.lab.rest.dto

data class MobilePhoneRequest(
    var brand: String,
    var manufacturer: String,
    var model: String,
    var memory: Int,
    var price: Double,
    var productionDate: String,
    var dualSim: Boolean,
    var files: List<FileRequest>
)

data class MobilePhoneResponse(
    var id: Long,
    var brand: String,
    var manufacturer: String,
    var model: String,
    var memory: Int,
    var price: Double,
    var productionDate: String,
    var dualSim: Boolean,
    var files: List<FileResponse>
)

data class FileRequest(
    var name: String,
    var extension: String,
    var size: Double,
    var creationDate: String,
    var isPhoto: Boolean
)

data class FileResponse(
    var id: Long,
    var name: String,
    var extension: String,
    var size: Double,
    var creationDate: String,
    var isPhoto: Boolean
)