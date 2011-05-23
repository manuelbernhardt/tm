package importer;

import java.io.File;
import java.util.List;
import java.util.Map;

import models.general.CompositeModel;
import models.tm.Project;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public interface Importer {

    public List<ImportError> importFile(Class<? extends CompositeModel> baseModelType, Map<String, Object> contextData, Project project, File file) throws Throwable;

}
