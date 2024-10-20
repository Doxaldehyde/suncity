package com.dox.suncity.Login

data class Dependent(
    val dependentId: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val relationship: String = ""
    // Add other properties for dependents
)

data class User(
    var userId: String = "", // This should be generated when a new user is created
    val firstName: String = "",
    val lastName: String = "",
    val emailAddress: String = "",
    val homeAddress : String = "",
    val phoneNumber : String = "",
   val gender: String = "",
   val maritalStatus: String = "",
    val occupancyType: String = "",
    // Add other properties here with default values
    val dependents: List<Dependent> = emptyList()
) {
    constructor() : this("", "", "", "", "",
        "", "", "", "") {
        // Default constructor required for Firebase
    }
    fun addDependent(dependent: Dependent): User {
        val updatedDependents = dependents.toMutableList()
        updatedDependents.add(dependent)
        return copy(dependents = updatedDependents)
    }
}