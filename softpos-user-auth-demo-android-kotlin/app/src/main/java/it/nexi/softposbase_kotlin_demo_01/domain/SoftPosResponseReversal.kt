package it.nexi.softposbase_kotlin_demo_01.domain

/**
 *
 * Nexi Payment
 *
 * Contains the essential data returned by the
 * Nexi POS after the execution of the method:
 *
 * Reversal
 *
 */
data class SoftPosResponseReversal( var terminalID: String? = null,
                                    var callerTrxId: String? = null,
                                    var operationType: String? = null,
                                    var result: String? = null,
                                    var hostTotal: String? = null,
                                    var terminalTotal: String? = null
)
