export type MatchRequiringReviewResponse = {
    matches: MatchRequiringReview[];
    page: number;
    total: number;
};

export type MatchRequiringReview = {
    patientId: string;
    patientName: string;
    createdDate: string;
    identifiedDate: string;
    numOfMatchingRecords: number;
};
