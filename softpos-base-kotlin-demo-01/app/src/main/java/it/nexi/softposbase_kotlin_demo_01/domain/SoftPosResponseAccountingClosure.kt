package it.nexi.softposbase_kotlin_demo_01.domain

/**
 *
 * Nexi Payment
 *
 * Contains the essential data returned by the
 * Nexi POS after the execution of the method:
 *
 * AccountingClosure
 *
 */
data class SoftPosResponseAccountingClosure(var result: String? = null,
                                            var callerTrxId: String? = null,
                                            var hostTotal: String? = null,
                                            var terminalTotal: String? = null
)
