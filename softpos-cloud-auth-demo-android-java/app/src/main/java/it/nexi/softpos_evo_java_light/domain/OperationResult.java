package it.nexi.softpos_evo_java_light.domain;

import java.util.Objects;

/**
 *
 * Nexi Payment
 *
 * Helper class used to return the result
 * of operations associated with the SDK's OperationListener
 *
 */
public class OperationResult {

    private final boolean isSuccess;
    private final String description;

    public OperationResult(boolean isSuccess, String description) {

        this.isSuccess = isSuccess;
        this.description = description;

    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OperationResult that = (OperationResult) o;

        return isSuccess == that.isSuccess && Objects.equals(description, that.description);

    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess, description);
    }

    @Override
    public String toString() {

        return "OperationResult{" +
                "isSuccess=" + isSuccess +
                ", description='" + description + '\'' +
                '}';

    }

} // end class
