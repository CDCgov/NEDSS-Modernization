export { initial } from './general';
export type { GeneralInformationDemographic, HasGeneralInformationDemographic } from './general';

export { GeneralInformationDemographicCard } from './view/';
export type { GeneralInformationDemographicCardProps } from './view';

export { useGeneralInformationOptions } from './edit/useGeneralInformationOptions';
export type { GeneralInformationOptions } from './edit/useGeneralInformationOptions';

export { EditGeneralInformationDemographicCard } from './edit/EditGeneralInformationDemographicCard';

export type { GeneralInformationDemographicRequest } from './request/generalRequest';
export { asGeneral } from './request/asGeneral';
