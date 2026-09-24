package it.nexi.softposbase_kotlin_demo_01.domain


/**
 *
 * Nexi Payment
 *
 * Contains the essential data returned by the
 * Nexi POS after the execution of the method:
 *
 * GetLastTransaction
 *
 */
data class SoftPosResponseLastTransaction(var result: String? = null,
                                          var callerTrxId: String? = null,
                                          var amount: String? = null,
                                          var tipAmount: String? = null,
                                          var totalAmount: String? = null,
                                          var operationType: String? = null,
                                          var terminalType: String? = null,
                                          var terminalID: String? = null,
                                          var campiAggiuntiviCT122: String? = null,
                                          var campiAggiuntiviATAGSRECEIPT: String? = null
)
