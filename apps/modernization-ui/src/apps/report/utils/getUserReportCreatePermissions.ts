import { PERMISSION_GROUP_MAP } from '../constants.ts';
import { GROUP_OPTIONS } from '../management/configuration/ReportConfigurationContent.tsx';

export const getUserReportCreatePermissionsOptions = (userPermissions) => {
    const allowedKeys = Object.keys(PERMISSION_GROUP_MAP).filter((key) => 
        userPermissions.includes(PERMISSION_GROUP_MAP[key].create)
    );

    return GROUP_OPTIONS.filter((option) => allowedKeys.includes(option.value));
};
