import { Selectable } from 'components/FormInputs/SelectInput';
import { LabReportFilter } from 'generated/graphql/schema';
import { FormLabReportFilter } from './labReportFormTypes';
import { asValues } from 'options/selectable';

export const transformObject = (data: FormLabReportFilter): LabReportFilter => {
    const transformedObj: {
        [key: string]: any;
    } = {};

    for (const [key, value] of Object.entries(data)) {
        if (Array.isArray(value)) {
            if (typeof value[0] === 'object') {
                transformedObj[key] = (value as Selectable[]).map((obj) => obj.value);
            } else {
                transformedObj[key] = value;
            }
        } else {
            transformedObj[key] = value;
        }
    }
    return {
        ...data,
        ...(data.jurisdictions && { jurisdictions: asValues(data.jurisdictions) }),
        ...(data.codedResult && { codedResult: asValues(data.codedResult) })
    };
};
