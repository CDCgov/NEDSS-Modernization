import { DisplayableName } from 'name/types';

type Deletability = 'Deletable' | 'Has_Associations' | 'Is_Inactive';

type Patient = {
    id: number;
    patientId: string;
    local: string;
    status: string;
    deletability: Deletability;
    sex: string;
    birthday?: string;
    name?: DisplayableName;
};

export type { Deletability, Patient };
