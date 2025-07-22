import { useComponentSizing } from 'design-system/sizing';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileDemographicsSummaryCard } from '../demographics/summary';
import { PatientFileOpenInvestigationsCard } from './openInvestigations';
import { PatientDocumentRequiringReviewCard } from './documentRequiringReview';
import { PatientMergeHistoryCard } from './mergeHistory/PatientMergeHistoryCard';
import { FeatureToggle } from '../../../../feature';

const PatientFileSummary = () => {
    const { summary, demographics, patient } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <>
            <PatientFileDemographicsSummaryCard
                id="demographics-summary"
                provider={demographics.get().summary}
                sizing={sizing}
            />
            <PatientFileOpenInvestigationsCard
                id="open-investigations"
                provider={summary.get().openInvestigations}
                sizing={sizing}
            />
            <PatientDocumentRequiringReviewCard
                id="documents-requiring-review"
                provider={summary.get().drr}
                sizing={sizing}
            />
            <FeatureToggle guard={(features) => features.patient.file.mergeHistory?.enabled}>
                <PatientMergeHistoryCard id="merge-history" provider={summary.get().mergeHistory} patient={patient} />
            </FeatureToggle>
        </>
    );
};

export { PatientFileSummary };
