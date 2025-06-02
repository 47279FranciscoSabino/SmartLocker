package project.smartlocker.domain.module

data class ModuleStatus(
    val module: Int,
    val locName: String,
    val status: ModuleEnum
)

enum class ModuleEnum{
    AVAILABLE, UNAVAILABLE, MAINTENANCE
}