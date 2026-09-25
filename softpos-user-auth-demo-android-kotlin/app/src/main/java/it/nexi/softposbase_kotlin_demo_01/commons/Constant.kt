package it.nexi.softposbase_kotlin_demo_01.commons

/**
 *
 * Nexi Payment
 *
 * Contains the constant values
 * required for the application to function.
 *
 */
object Constant {

    const val TIPO_OPERAZIONE_PAYMENT = "payment"
    const val TIPO_OPERAZIONE_REVERSAL = "reversal"
    const val TIPO_OPERAZIONE_LAST_TRANSACTION = "last_transaction"
    const val TIPO_OPERAZIONE_ACCOUNTING_CLOSURE = "accounting_closure"

    const val PARAMETER_SOFTPOS_CALLER_NAME = "SOFT POS BASE"

    const val LOG_PREFISSO_MSG_ERR = "TEST_SOFTPOS_BASE ERROR : "

    const val APP_SCHEMA = "demonexi_test_kotlin_demo_01"
    const val APP_AUTHORITY_PAYMENT = "payment"
    const val APP_AUTHORITY_REVERSAL = "reversal"
    const val APP_AUTHORITY_LAST_TRANSACTION = "last_transaction"
    const val APP_AUTHORITY_ACCOUNTING_CLOSURE = "accounting_closure"

    const val NEXIPOS_SCHEMA = "neximpos"
    const val NEXIPOS_AUTHORITY_PAYMENT = "payment"
    const val NEXIPOS_AUTHORITY_REVERSAL = "reversal"
    const val NEXIPOS_AUTHORITY_LAST_TRANSACTION = "last_transaction"
    const val NEXIPOS_AUTHORITY_ACCOUNTING_CLOSURE = "accounting_closure"

}