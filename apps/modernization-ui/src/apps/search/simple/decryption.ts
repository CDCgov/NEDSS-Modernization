import { EncryptionControllerService } from 'generated';

const decrypt = <V>(value: string) =>
    EncryptionControllerService.decrypt({
        requestBody: value
    }).then((response) => response as V);

export { decrypt };
