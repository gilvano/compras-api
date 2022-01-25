package com.gilvano.comprasapi.model

import com.gilvano.comprasapi.enums.Profile
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document(collection = "resellers")
data class ResellerModel(

    @Id
    var id: String? = null,

    @NotBlank
    val name: String,

    @NotBlank
    val cpf: String,

    @NotBlank
    val email: String,

    @NotBlank
    val password: String,

    var profile: Profile = Profile.RESELLER
) {
    override fun toString(): String {
        return "ResellerModel(id=$id, name='$name', cpf='***********', email='$email', password='$password', profile=$profile)"
    }
}
