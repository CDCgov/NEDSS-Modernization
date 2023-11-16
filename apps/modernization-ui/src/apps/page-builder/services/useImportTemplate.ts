import { useContext, useState } from 'react';
import { Template } from '../generated/models/Template';
import { TemplateControllerService } from '../generated';
import { UserContext } from 'user';

export const useImportTemplate = (): {
    reset: () => void;
    isLoading: boolean;
    error: string | undefined;
    importTemplate: (file: File) => Promise<Template>;
} => {
    const { state } = useContext(UserContext);
    const [error, setError] = useState<string | undefined>();
    const [isLoading, setIsLoading] = useState(false);

    const reset = () => {
        setError(undefined);
        setIsLoading(false);
    };

    const importTemplate = (file: File): Promise<Template> => {
        return new Promise((resolve, reject) => {
            if (file.type !== 'text/xml') {
                setError('Only XML files are allowed');
                return;
            }
            setIsLoading(true);
            setError(undefined);

            TemplateControllerService.importTemplateUsingPost({
                authorization: `Bearer ${state.getToken()}`,
                fileInput: file
            })
                .then((response) => {
                    setIsLoading(false);
                    resolve(response);
                })
                .catch((error) => {
                    setError(error.body?.message ?? 'An error occured');
                    setIsLoading(false);
                    reject(error.body?.message ?? 'An error occured');
                });
        });
    };
    return { isLoading, error, importTemplate, reset };
};
