package it.nexi.softpos_evo_kotlin_01.domain

/**
 *
 * Nexi Payment
 *
 * Helper class used to return the result
 * of operations associated with the SDK's OperationListener
 *
 */
data class OperationResult(val isSuccess: Boolean,
                           val description: String
                          )
