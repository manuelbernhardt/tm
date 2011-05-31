package importer;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class ImportError extends Error {

    Integer rowIndex;
    Integer colIndex;

    public ImportError(String message) {
        super(message);
    }
}
