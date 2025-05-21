export type PatientData = {
    personUid: string;
    adminComments: AdminComments;
};

export type AdminComments = {
    date: string;
    comment: string;
};
