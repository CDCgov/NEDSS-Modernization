export type PatientData = {
    personUid: string;
    adminComments: AdminComments;
};

type AdminComments = {
    date: string;
    comment: string;
};
