import { logErrorToUserConsole } from 'utils/logging';
import { Template, TemplateControllerService } from '../generated';

export const fetchTemplates = (type: string): Promise<Template[]> =>
    TemplateControllerService.findAllTemplates({
        type: type,
    }).catch((error) => {
        logErrorToUserConsole(error.toJSON());
        return Promise.reject(error);
    });
