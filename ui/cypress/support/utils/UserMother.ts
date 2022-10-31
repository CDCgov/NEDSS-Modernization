import User from '../models/User';
import { ProgramArea } from '../models/enums/ProgramArea';
import { PermissionSet } from '../models/enums/PermissionSet';

export default class UserMother {
    public static clericalDataEntry(): User {
        return {
            userId: 'test-clerical',
            firstName: 'test',
            lastName: 'clerical',
            isMasterSecurityAdmin: false,
            programAreaAdministrator: false,
            programAreas: [],
            roles: [
                {
                    jurisdiction: 'All',
                    programArea: ProgramArea.STD,
                    permissionSet: PermissionSet.NEDSS_CLERICAL_DATA_ENTRY,
                    isGuest: false
                }
            ]
        };
    }

    public static registryManager(): User {
        return {
            userId: 'test-registry-manager',
            firstName: 'test',
            lastName: 'registry-manager',
            isMasterSecurityAdmin: false,
            programAreaAdministrator: false,
            programAreas: [ProgramArea.STD],
            roles: [
                {
                    jurisdiction: 'All',
                    programArea: ProgramArea.STD,
                    permissionSet: PermissionSet.NEDSS_REGISTRY_MANAGER,
                    isGuest: false
                }
            ]
        };
    }

    public static supervisor(): User {
        return {
            userId: 'test-supervisor',
            firstName: 'test',
            lastName: 'supervisor',
            isMasterSecurityAdmin: false,
            programAreaAdministrator: false,
            programAreas: [ProgramArea.STD],
            roles: [
                {
                    jurisdiction: 'All',
                    programArea: ProgramArea.STD,
                    permissionSet: PermissionSet.NEDSS_DISEASE_SUPERVISOR,
                    isGuest: false
                }
            ]
        };
    }

    public static elrImporter(): User {
        return {
            userId: 'test-elr-importer',
            firstName: 'Elr',
            lastName: 'Importer',
            isMasterSecurityAdmin: false,
            programAreaAdministrator: false,
            programAreas: [],
            roles: [
                {
                    jurisdiction: 'All',
                    programArea: ProgramArea.ARBO,
                    permissionSet: PermissionSet.ELR_IMPORTER,
                    isGuest: false
                }
            ]
        };
    }

    public static nedssAdmin(): User {
        return {
            userId: 'test-nedss-admin',
            firstName: 'Nedss',
            lastName: 'Admin',
            isMasterSecurityAdmin: false,
            programAreaAdministrator: true,
            programAreas: [],
            roles: [
                {
                    jurisdiction: 'All',
                    programArea: ProgramArea.ARBO,
                    permissionSet: PermissionSet.NEDSS_SYSTEM_ADMIN,
                    isGuest: false
                }
            ]
        };
    }

    public static systemAdmin(): User {
        return {
            userId: 'msa',
            firstName: 'Master',
            lastName: 'Administrator',
            isMasterSecurityAdmin: true,
            programAreaAdministrator: true,
            programAreas: Object.values(ProgramArea),
            roles: []
        };
    }
}
