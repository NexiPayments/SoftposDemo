package it.nexi.softposbase_kotlin_demo_01.domain

/**
 *
 * Nexi Payment
 *
 * Contains the essential data returned by the
 * Nexi POS after the execution of the method:
 *
 * Payment
 *
 */
data class SoftPosResponsePayment(var callerTrxId: String? = null,
                                  var operationType: String? = null,
                                  var result: String? = null,
                                  var amount: String? = null,
                                  var tipAmount: String? = null,
                                  var totalAmount: String? = null,
                                  var actionCode: String? = null,
                                  var pan: String? = null,
                                  var transactionType: String? = null,
                                  var authorizationNumber: String? = null,
                                  var timeStamp: String? = null,
                                  var resultDescription: String? = null,
                                  var cardTypeCVM: String? = null,
                                  var acquireId: String? = null,
                                  var stan: String? = null,
                                  var operationNumber: String? = null,
                                  var acquirerName: String? = null,
                                  var terminalID: String? = null,
                                  var merchantId: String? = null,
                                  var urlTicket: String? = null,
                                  var autoClose: String? = null,
                                  var campiAggiuntivi: String? = null
)
