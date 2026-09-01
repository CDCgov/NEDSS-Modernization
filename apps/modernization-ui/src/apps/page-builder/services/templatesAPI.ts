import { logErrorToUserConsole } from 'utils/logging';

import { Template, TemplateControllerService } from '../generated';

export const fetchTemplates = (type: string): Promise<Template[]> =>
    TemplateControllerService.findAllTemplates({
        type,
    }).catch((error) => {
        logErrorToUserConsole(error);
        return Promise.reject(error);
    });
