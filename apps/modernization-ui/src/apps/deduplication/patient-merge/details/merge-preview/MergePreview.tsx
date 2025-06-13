import { Heading } from 'components/heading';
import { Button } from 'design-system/button';
import styles from './merge-preview.module.scss';
import { PatientMergeForm } from '../merge-review/model/PatientMergeForm';
import { MergeCandidate } from '../../../api/model/MergeCandidate';
import { PatientSummary } from './components/patient-summary/PatientSummary';
import { AdministrativeComments } from './components/administrative-comments/AdministrativeComments';
import { PreviewAddress } from './components/address/PreviewAddress';
import { PreviewIdentification } from './components/identification/PreviewIdentification';
import { PreviewName } from './components/name/PreviewName';
import { PreviewPhoneAndEmail } from './components/phone-and-email/PreviewPhoneAndEmail';
import { PreviewRace } from './components/race/PreviewRace';
import { PreviewEthnicity } from './components/ethnicity/PreviewEthnicity';

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
            <PatientSummary mergeFormData={mergeFormData} mergeCandidates={mergeCandidates} />
            <AdministrativeComments mergeFormData={mergeFormData} mergeCandidates={mergeCandidates} />
            <PreviewName
                mergeCandidates={mergeCandidates}
                selectedNames={mergeFormData.names.map(({ personUid, sequence }) => ({
                    personUid,
                    sequence
                }))}
            />
            <PreviewAddress
                mergeCandidates={mergeCandidates}
                selectedAddresses={mergeFormData.addresses.map(({ locatorId }) => ({
                    locatorId
                }))}
            />
            <PreviewPhoneAndEmail
                mergeCandidates={mergeCandidates}
                selectedPhoneEmails={mergeFormData.phoneEmails.map(({ locatorId }) => ({
                    locatorId
                }))}
            />
            <PreviewIdentification
                mergeCandidates={mergeCandidates}
                selectedIdentifications={mergeFormData.identifications.map(({ personUid, sequence }) => ({
                    personUid,
                    sequence
                }))}
            />
            <PreviewRace
                mergeCandidates={mergeCandidates}
                selectedRaces={mergeFormData.races.map(({ personUid, raceCode }) => ({
                    personUid,
                    raceCode
                }))}
            />
            <PreviewEthnicity mergeFormData={mergeFormData} mergeCandidates={mergeCandidates} />
        </div>
    );
};
