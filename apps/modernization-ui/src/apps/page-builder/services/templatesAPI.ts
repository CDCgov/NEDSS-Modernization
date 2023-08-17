import { TemplateControllerService } from '../generated';

export const fetchTemplates = (token: string): Promise<any> => {
    return new Promise((resolve, reject) => {
        TemplateControllerService.findAllTemplatesUsingGet({
            authorization: token
        })
            .then((response: any) => {
                resolve(response.content);
            })
            .catch((error: any) => {
                console.log(error.toJSON());
                reject(error);
            });
    });
};
