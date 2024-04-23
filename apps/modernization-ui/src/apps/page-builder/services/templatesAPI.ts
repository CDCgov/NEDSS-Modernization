import { Template, TemplateControllerService } from '../generated';

export const fetchTemplates = (type: string): Promise<Template[]> =>
    TemplateControllerService.findAllTemplates({
        type: type
    }).catch((error) => {
        console.log(error.toJSON());
        return Promise.reject(error);
    });
