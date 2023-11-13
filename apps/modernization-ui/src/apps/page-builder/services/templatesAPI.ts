import { Template, TemplateControllerService } from '../generated';

export const fetchTemplates = (token: string): Promise<Template[]> => {
    return new Promise((resolve, reject) => {
        TemplateControllerService.findAllTemplatesUsingGet({
            authorization: token
        })
            .then((response) => {
                resolve(response.content ?? []);
            })
            .catch((error: any) => {
                console.log(error.toJSON());
                reject(error);
            });
    });
};
