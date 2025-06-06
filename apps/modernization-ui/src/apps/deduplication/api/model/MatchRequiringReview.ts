export type MatchRequiringReviewResponse = {
    matches: MatchRequiringReview[];
    page: number;
    total: number;
};

export type MatchRequiringReview = {
    matchId: number;
    patientId: string;
    patientLocalId: string;
    patientName: string;
    createdDate: string;
    identifiedDate: string;
    numOfMatchingRecords: number;
};
