package it.nexi.softposbase_java_light_01.domain;


/**
 *
 * Nexi Payment
 *
 * Contains the essential data returned by the
 * Nexi POS after the execution of the method:
 *
 * GetLastTrnsaction
 *
 */
public class SoftPosResponseLastTransaction {

    private String result;
    private String callerTrxId;
    private String amount;
    private String tipAmount;
    private String totalAmount;
    private String operationType;
    private String terminalType;

    private String terminalID;
    private String campiAggiuntiviCT122;
    private String campiAggiuntiviATAGSRECEIPT;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCallerTrxId() {
        return callerTrxId;
    }

    public void setCallerTrxId(String callerTrxId) {
        this.callerTrxId = callerTrxId;
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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

    public String getCampiAggiuntiviCT122() {
        return campiAggiuntiviCT122;
    }

    public void setCampiAggiuntiviCT122(String campiAggiuntiviCT122) {
        this.campiAggiuntiviCT122 = campiAggiuntiviCT122;
    }

    public String getCampiAggiuntiviATAGSRECEIPT() {
        return campiAggiuntiviATAGSRECEIPT;
    }

    public void setCampiAggiuntiviATAGSRECEIPT(String campiAggiuntiviATAGSRECEIPT) {
        this.campiAggiuntiviATAGSRECEIPT = campiAggiuntiviATAGSRECEIPT;
    }

    @Override
    public String toString() {
        return "SoftPosResponseLastTransaction{" +
                "result='" + result + '\'' +
                ", callerTrxId='" + callerTrxId + '\'' +
                ", amount='" + amount + '\'' +
                ", tipAmount='" + tipAmount + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", operationType='" + operationType + '\'' +
                ", terminalType='" + terminalType + '\'' +
                ", terminalID='" + terminalID + '\'' +
                ", campiAggiuntiviCT122='" + campiAggiuntiviCT122 + '\'' +
                ", campiAggiuntiviATAGSRECEIPT='" + campiAggiuntiviATAGSRECEIPT + '\'' +
                '}';
    }

} // SoftPosResponseLastTransaction
