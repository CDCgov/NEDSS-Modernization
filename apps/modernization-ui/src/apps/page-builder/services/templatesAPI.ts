import { Template, TemplateControllerService } from '../generated';

export const fetchTemplates = (token: string): Promise<Template[]> =>
    TemplateControllerService.findAllTemplatesUsingGet({
        authorization: token
    }).catch((error) => {
        console.log(error.toJSON());
        return Promise.reject(error);
    });
