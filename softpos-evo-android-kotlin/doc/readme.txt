Per utilizzare il progetto di test è necessario intervenire
nei seguenti file :

### File Constant

Impostare i valori delle costanti con quelli associati all'app
cliente registrata nel portale sviluppatori

const val CLIENT_ID = "<vedere configurazione dell'app sul portale sviluppatori Nexi>"
const val ID_PUNTO_VENDITA = "<vedere configurazione dell'app sul portale sviluppatori Nexi>"
const val REDIRECT_URI = "<vedere configurazione dell'app sul portale sviluppatori Nexi>"
const val MERCHANT_USER_NAME = "<vedere configurazione dell'app sul portale sviluppatori Nexi>"

I valori di cui sopra sono utilizzati nella fase di init dell'SDK

### File BackendService
Il cliente dovrebbe predisporre un servizio che gli consenta
di interfacciarsi con il portale sviluppatore Nexi:

Implementare i seguenti metodi :

# Recupera la request_uri utilizzata in fase di autenticazione con l?SDK
suspend fun getRequestUri(deviceId: String): String

# Recupera una transazione, richiedendola ai sistemi Nexi
suspend fun checkTransaction(callerTrxId: String): String

