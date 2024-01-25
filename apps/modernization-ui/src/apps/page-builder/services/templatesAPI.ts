import { Template, TemplateControllerService } from '../generated';

export const fetchTemplates = (token: string, type: string): Promise<Template[]> =>
    TemplateControllerService.findAllTemplatesUsingGet({
        authorization: token,
        type: type
    }).catch((error) => {
        console.log(error.toJSON());
        return Promise.reject(error);
    });
