package models.project.test;

/**
 * Execution Status for {@link Instance}, {@link Run} and {@link RunStep}.
 * We persist based on the key.
 *
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public enum ExecutionStatus {

    NOT_RUN(0, "Not run"), NOT_COMPLETED(1, "Not completed"), PASSED(2, "Passed"), FAILED(3, "Failed");


    private String label;
    private Integer position;

    ExecutionStatus(Integer position, String label) {
        this.label = label;
        this.position = position;
    }

    public String getKey() {
        return this.name();
    }

    public String getLabel() {
        return label;
    }

    public Integer getPosition() {
        return position;
    }
}
