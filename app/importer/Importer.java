package importer;

import java.io.File;
import java.util.List;
import java.util.Map;

import models.tm.Project;
import play.db.jpa.Model;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Importer {

    public List<ImportError> importFile(Class<? extends Model> baseModelType, Map<String, Object> contextData, Project project, File file) throws Throwable;

}
