package it.nexi.softposbase_java_light_01.domain;


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

public class SoftPosResponseReversal {

    private String terminalID;
    private String callerTrxId;
    private String operationType;
    private String result;
    private String hostTotal;
    private String terminalTotal;

    public String getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(String terminalID) {
        this.terminalID = terminalID;
    }

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

    public String getHostTotal() {
        return hostTotal;
    }

    public void setHostTotal(String hostTotal) {
        this.hostTotal = hostTotal;
    }

    public String getTerminalTotal() {
        return terminalTotal;
    }

    public void setTerminalTotal(String terminalTotal) {
        this.terminalTotal = terminalTotal;
    }

    @Override
    public String toString() {
        return "SoftPosResponseReversal{" +
                "terminalID='" + terminalID + '\'' +
                ", callerTrxId='" + callerTrxId + '\'' +
                ", operationType='" + operationType + '\'' +
                ", result='" + result + '\'' +
                ", hostTotal='" + hostTotal + '\'' +
                ", terminalTotal='" + terminalTotal + '\'' +
                '}';
    }

}
