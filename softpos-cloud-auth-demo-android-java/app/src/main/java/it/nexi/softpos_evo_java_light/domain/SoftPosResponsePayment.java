package it.nexi.softpos_evo_java_light.domain;


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

public class SoftPosResponsePayment {

    private String callerTrxId;
    private String operationType;
    private String result;
    private String amount;
    private String tipAmount;
    private String totalAmount;
    private String actionCode;
    private String pan;
    private String transactionType;
    private String authorizationNumber;
    private String timeStamp;
    private String resultDescription;
    private String cardTypeCVM;
    private String acquireId;
    private String stan;
    private String operationNumber;
    private String acquirerName;
    private String terminalID;
    private String merchantId;
    private String urlTicket;
    private String autoClose;
    private String campiAggiuntivi;

    public String getCallerTrxId() {
        return callerTrxId;
    }

    public void setCallerTrxId(String callerTrxId) {
        this.callerTrxId = callerTrxId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTipAmount() {
        return tipAmount;
    }

    public void setTipAmount(String tipAmount) {
        this.tipAmount = tipAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public String getCardTypeCVM() {
        return cardTypeCVM;
    }

    public void setCardTypeCVM(String cardTypeCVM) {
        this.cardTypeCVM = cardTypeCVM;
    }

    public String getAcquireId() {
        return acquireId;
    }

    public void setAcquireId(String acquireId) {
        this.acquireId = acquireId;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(String operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getAcquirerName() {
        return acquirerName;
    }

    public void setAcquirerName(String acquirerName) {
        this.acquirerName = acquirerName;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUrlTicket() {
        return urlTicket;
    }

    public void setUrlTicket(String urlTicket) {
        this.urlTicket = urlTicket;
    }

    public String getAutoClose() {
        return autoClose;
    }

    public void setAutoClose(String autoClose) {
        this.autoClose = autoClose;
    }

    public String getCampiAggiuntivi() {
        return campiAggiuntivi;
    }

    public void setCampiAggiuntivi(String campiAggiuntivi) {
        this.campiAggiuntivi = campiAggiuntivi;
    }

    @Override
    public String toString() {
        return "SoftPosResponsePayment{" +
                "callerTrxId='" + callerTrxId + '\'' +
                ", operationType='" + operationType + '\'' +
                ", result='" + result + '\'' +
                ", amount='" + amount + '\'' +
                ", tipAmount='" + tipAmount + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", actionCode='" + actionCode + '\'' +
                ", pan='" + pan + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", authorizationNumber='" + authorizationNumber + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", resultDescription='" + resultDescription + '\'' +
                ", cardTypeCVM='" + cardTypeCVM + '\'' +
                ", acquireId='" + acquireId + '\'' +
                ", stan='" + stan + '\'' +
                ", operationNumber='" + operationNumber + '\'' +
                ", acquirerName='" + acquirerName + '\'' +
                ", terminalID='" + terminalID + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", urlTicket='" + urlTicket + '\'' +
                ", autoClose='" + autoClose + '\'' +
                ", campiAggiuntivi='" + campiAggiuntivi + '\'' +
                '}';
    }
} // end class
