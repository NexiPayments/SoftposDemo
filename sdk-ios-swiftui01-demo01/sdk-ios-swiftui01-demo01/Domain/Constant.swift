//
//  Constant.swift
//

import UIKit

class Constant {

    public static let APP_MANAGER                                   = "AppManager"
    
    public static let USER_DEFAULTS_AMOUNT_ULTIMO_PAGAMENTO         = "AMOUNT_ULTIMO_PAGAMENTO"
    public static let USER_DEFAULTS_TERMINAL_ID_ULTIMO_PAGAMENTO    = "TERMINAL_ID_ULTIMO_PAGAMENTO"
    public static let USER_DEFAULTS_TRANSACTION_ID_ULTIMO_PAGAMENTO = "TRANSACTION_ID_ULTIMO_PAGAMENTO"
    
    // Inizio - Dati da utilizzare nella fase di init dell'SDK

    // Da recuperare dal portale sviluppatore Nexi
    // identificativo dell'applicazione registrata
    // nel portale sviluppatori Nexi
    public static let CLIENT_ID                                     = ""

    // Identificativo punto vendita associato all'app
    // registrata nel portale sviluppatori Nexi
    public static let ID_PUNTO_VENDITA                              = ""

    // Indirizzo web indicato nell'app
    // registrata nel portale sviluppatori Nexi
    // Può anche non esistere ma devo essere formalmente corretto
    // es. https://miosito/miapagina
    public static let REDIRECT_URI                                  = ""

        // Il merchant_user_name normalmente è un indirizzo email
        // associato all'utente.
    public static let MERCHANT_USER_NAME                            = ""

    // Fine - Dati da utilizzare nella fase di init dell'SDK

    public static let DEEP_LINK_SCHEMA = "demonexi_test"

    
}
