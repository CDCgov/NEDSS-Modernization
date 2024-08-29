import { EncryptionControllerService } from 'generated';

const encrypt = (value: Record<string, any>) =>
    EncryptionControllerService.encrypt({
        requestBody: value
    });

export { encrypt };
