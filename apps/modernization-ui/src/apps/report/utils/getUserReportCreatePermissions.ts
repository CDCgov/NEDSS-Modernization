import { PERMISSION_GROUP_MAP } from '../constants.ts';
import { GROUP_OPTIONS } from '../management/configuration/ReportConfigurationContent.tsx';

export const getUserReportCreatePermissionsOptions = (userPermissions) => {
    const allowedKeys = Object.keys(PERMISSION_GROUP_MAP).filter((key) => {
        const createVal = PERMISSION_GROUP_MAP[key].create;
        return createVal && userPermissions.includes(createVal);
    });

    return GROUP_OPTIONS.filter((option) => allowedKeys.includes(option.value));
};
