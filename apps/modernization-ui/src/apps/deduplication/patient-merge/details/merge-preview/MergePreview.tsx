import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import styles from './merge-preview.module.scss';
import { PatientSummary } from './components/patient-summary/PatientSummary';
import { PatientMergeForm } from '../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../api/model/MergeCandidate';
import { AdministrativeComments } from './components/administrative-comments/AdministrativeComments';
import { PreviewName } from './components/name/PreviewName';
import { PreviewAddress } from './components/address/PreviewAddress';
import { PreviewPhoneAndEmail } from './components/phone-and-email/PreviewPhoneAndEmail';
import { PreviewIdentification } from './components/identification/PreviewIdentification';

type MergePreviewProps = {
    onBack: () => void;
    mergeFormData: PatientMergeForm;
    mergeCandidates: MergeCandidate[];
};

export const MergePreview = ({ onBack, mergeFormData, mergeCandidates }: MergePreviewProps) => {
    return (
        <div className={styles.mergePreview}>
            <header>
                <Heading level={1}>Merge preview</Heading>
                <div className={styles.buttons}>
                    <Button secondary onClick={onBack}>
                        Back
                    </Button>
                    <Button
                        aria-label="Confirm and merge patient records"
                        onClick={() => console.log('Merge record NYI')}>
                        Merge record
                    </Button>
                </div>
            </header>
            <section className={styles.summaryCardSection}>
                <PatientSummary mergeFormData={mergeFormData} mergeCandidates={mergeCandidates} />
                <AdministrativeComments mergeFormData={mergeFormData} mergeCandidates={mergeCandidates} />
                <PreviewName
                    selectedNames={mergeFormData.names.map(({ personUid, sequence }) => ({
                        personUid,
                        sequence
                    }))}
                    mergeCandidates={mergeCandidates}
                />
                <PreviewAddress
                    selectedAddresses={mergeFormData.addresses.map(({ locatorId }) => ({
                        locatorId
                    }))}
                    mergeCandidates={mergeCandidates}
                />
                <PreviewPhoneAndEmail
                    selectedPhoneEmails={mergeFormData.phoneEmails.map(({ locatorId }) => ({
                        locatorId
                    }))}
                    mergeCandidates={mergeCandidates}
                />
                <PreviewIdentification
                    selectedIdentifications={mergeFormData.identifications.map(({ personUid, sequence }) => ({
                        personUid,
                        sequence
                    }))}
                    mergeCandidates={mergeCandidates}
                />
            </section>
        </div>
    );
};
