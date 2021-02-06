package ru.s1mple.myapp.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ActorsData (
    val id: Long,
    @SerialName("cast")
    val cast: List<Actor>,
    val crew: List<Actor>
)

@Serializable
data class Actor (
    val adult: Boolean,
    val gender: Long,
    val id: Long,

//    @SerialName("known_for_department")
//    val knownForDepartment: Department,

    @SerialName("name")
    val name: String,

    @SerialName("original_name")
    val originalName: String,

    val popularity: Double,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("cast_id")
    val castID: Long? = null,

    val character: String? = null,

    @SerialName("credit_id")
    val creditID: String,

    val order: Long? = null,
//    val department: Department? = null,
    val job: String? = null
)

//@Serializable(with = Department.Companion::class)
//enum class Department(val value: String) {
//    Acting("Acting"),
//    Art("Art"),
//    Camera("Camera"),
//    CostumeMakeUp("Costume & Make-Up"),
//    Creator("Creator"),
//    Crew("Crew"),
//    Directing("Directing"),
//    Editing("Editing"),
//    Lighting("Lighting"),
//    Production("Production"),
//    Sound("Sound"),
//    VisualEffects("Visual Effects"),
//    Writing("Writing");
//
//    companion object : KSerializer<Department> {
//        override val descriptor: SerialDescriptor get() {
//            return PrimitiveDescriptor("quicktype.Department", PrimitiveKind.STRING)
//        }
//        override fun deserialize(decoder: Decoder): Department = when (val value = decoder.decodeString()) {
//            "Acting"            -> Acting
//            "Art"               -> Art
//            "Camera"            -> Camera
//            "Costume & Make-Up" -> CostumeMakeUp
//            "Creator"           -> Creator
//            "Crew"              -> Crew
//            "Directing"         -> Directing
//            "Editing"           -> Editing
//            "Lighting"          -> Lighting
//            "Production"        -> Production
//            "Sound"             -> Sound
//            "Visual Effects"    -> VisualEffects
//            "Writing"           -> Writing
//            else                -> throw IllegalArgumentException("Department could not parse: $value")
//        }
//        override fun serialize(encoder: Encoder, value: Department) {
//            return encoder.encodeString(value.value)
//        }
//    }
//}
