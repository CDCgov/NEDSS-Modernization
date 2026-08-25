import { EncryptionControllerService } from 'generated';

const encrypt = (value: Record<string, unknown>) =>
    EncryptionControllerService.encrypt({
        requestBody: value,
    });

export { encrypt };
