package it.nexi.softpos_evo_java_light.domain;

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
public class SoftPosResponseAccountingClosure {

    private String result;

    private String callerTrxId;
    private String hostTotal;
    private String terminalTotal;

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
        return "SoftPosResponseAccountingClosure{" +
                "result='" + result + '\'' +
                ", callerTrxId='" + callerTrxId + '\'' +
                ", hostTotal='" + hostTotal + '\'' +
                ", terminalTotal='" + terminalTotal + '\'' +
                '}';
    }

} // end class
