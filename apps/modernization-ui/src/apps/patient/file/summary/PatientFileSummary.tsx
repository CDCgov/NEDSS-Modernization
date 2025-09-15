import { useComponentSizing } from 'design-system/sizing';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileView } from '../PatientFileView';
import { PatientFileDemographicsSummaryCard } from '../demographics/summary';
import { PatientFileOpenInvestigationsCard } from './openInvestigations';
import { PatientDocumentRequiringReviewCard } from './documentRequiringReview';
import { PatientMergeHistoryCard } from './mergeHistory/PatientMergeHistoryCard';
import { FeatureToggle } from 'feature';
import { MergeAlert } from './mergeHistory/MergeAlert';

const PatientFileSummary = () => {
    const { summary, demographics, patient } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <PatientFileView patient={patient} sizing={sizing}>
            {!!patient?.id && <MergeAlert patientId={patient.id} />}
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
        </PatientFileView>
    );
};

export { PatientFileSummary };
