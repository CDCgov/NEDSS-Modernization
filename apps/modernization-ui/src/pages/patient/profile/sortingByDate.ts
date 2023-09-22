import {
    PatientAddress,
    PatientAdministrative,
    PatientIdentification,
    PatientName,
    PatientPhone,
    PatientRace
} from 'generated/graphql/schema';

type DataProp =
    | Array<PatientName>
    | Array<PatientAddress>
    | Array<PatientAdministrative>
    | Array<PatientIdentification>
    | Array<PatientPhone>
    | Array<PatientRace>;

export const sortingByDate = (data: DataProp) => {
    return data?.slice().sort((a, b) => {
        const dateA: any = new Date(a?.asOf);
        const dateB: any = new Date(b?.asOf);
        return dateB - dateA;
    });
};
