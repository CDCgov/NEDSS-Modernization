import { PermissionSet } from './enums/PermissionSet';
import { ProgramArea } from './enums/ProgramArea';

export default interface User {
    userId: string;
    firstName: string;
    lastName: string;
    isMasterSecurityAdmin: boolean;
    programAreaAdministrator: boolean;
    programAreas: ProgramArea[];
    roles: Role[];
}

export interface Role {
    jurisdiction: string;
    programArea: ProgramArea;
    permissionSet: PermissionSet;
    isGuest: boolean;
}
