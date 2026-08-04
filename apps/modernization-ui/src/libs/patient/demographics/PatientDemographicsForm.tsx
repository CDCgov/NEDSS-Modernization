import { useCallback, useRef } from 'react';

import { SkipLink } from 'SkipLink';
import classNames from 'classnames';
import { asOfAgeResolver } from 'date';
import {
    PendingEntryAlert,
    PendingFormEntryInteraction,
    PendingMessageRendererProps,
} from 'design-system/entry/pending';
import { Sizing } from 'design-system/field';
import { InPageNavigation } from 'design-system/inPageNavigation';
import { BackToTop } from 'libs/page/back-to-top';
import { UseFormReturn, useWatch } from 'react-hook-form';

import { EditAddressDemographicsCard } from './address';
import { EditAdministrativeInformationCard } from './administrative';
import { PatientDemographicsDefaults, PatientDemographicsEntry } from './demographics';
import { EditEthnicityDemographicCard } from './ethnicity';
import { EditGeneralInformationDemographicCard } from './general';
import { EditIdentificationDemographicsCard } from './identification';
import { EditMortalityDemographicCard } from './mortality';
import { EditNameDemographicsCard } from './name';
import styles from './patient-demographics-form.module.scss';
import { EditPhoneEmailDemographicsCard } from './phoneEmail';
import { EditRaceDemographicsCard } from './race';
import { sections } from './sections';
import { EditSexBirthDemographicCard } from './sex-birth';

const NAME_ENTRY = { id: 'names', name: 'Name' };
const ADDRESS_ENTRY = { id: 'addresses', name: 'Address' };
const PHONE_EMAIL_ENTRY = { id: 'phone-emails', name: 'Phone & email' };
const IDENTIFICATION_ENTRY = { id: 'identifications', name: 'Identification' };
const RACE_ENTRY = { id: 'races', name: 'Race' };

type PatientDemographicsFormProps = {
    pending: PendingFormEntryInteraction;
    defaults: PatientDemographicsDefaults;
    form: UseFormReturn<PatientDemographicsEntry>;
    entry?: PatientDemographicsEntry;
    sizing?: Sizing;
} & JSX.IntrinsicElements['div'];

const PatientDemographicsForm = ({
    pending,
    defaults,
    form,
    entry,
    sizing,
    className,
    ...remaining
}: PatientDemographicsFormProps) => {
    const content = useRef<HTMLDivElement>(null);

    const deceasedOn = useWatch({ control: form.control, name: 'mortality.deceasedOn' });

    const ageResolver = useCallback(asOfAgeResolver(deceasedOn), [deceasedOn]);

    return (
        <div {...remaining} className={classNames(styles.demographics, className)}>
            <SkipLink id="administrative.asOf" />
            <aside>
                <InPageNavigation sections={sections} />
            </aside>
            <div ref={content} className={styles.content}>
                <PendingEntryAlert
                    pending={pending.pending}
                    title="Please fix the following errors:"
                    renderer={PendingEntryMessage}
                />
                <EditAdministrativeInformationCard id="administrative" form={form} sizing={sizing} />
                <EditNameDemographicsCard
                    form={form}
                    defaults={defaults}
                    sizing={sizing}
                    id={NAME_ENTRY.id}
                    title={NAME_ENTRY.name}
                    isDirty={pending.onEntry(NAME_ENTRY)}
                    isValid={pending.onValid(NAME_ENTRY)}
                />
                <EditAddressDemographicsCard
                    form={form}
                    defaults={defaults}
                    sizing={sizing}
                    id={ADDRESS_ENTRY.id}
                    title={ADDRESS_ENTRY.name}
                    isDirty={pending.onEntry(ADDRESS_ENTRY)}
                    isValid={pending.onValid(ADDRESS_ENTRY)}
                />
                <EditPhoneEmailDemographicsCard
                    form={form}
                    defaults={defaults}
                    sizing={sizing}
                    id={PHONE_EMAIL_ENTRY.id}
                    title={PHONE_EMAIL_ENTRY.name}
                    isDirty={pending.onEntry(PHONE_EMAIL_ENTRY)}
                    isValid={pending.onValid(PHONE_EMAIL_ENTRY)}
                />
                <EditIdentificationDemographicsCard
                    form={form}
                    defaults={defaults}
                    sizing={sizing}
                    id={IDENTIFICATION_ENTRY.id}
                    title={IDENTIFICATION_ENTRY.name}
                    isDirty={pending.onEntry(IDENTIFICATION_ENTRY)}
                    isValid={pending.onValid(IDENTIFICATION_ENTRY)}
                />
                <EditRaceDemographicsCard
                    form={form}
                    defaults={defaults}
                    sizing={sizing}
                    id={RACE_ENTRY.id}
                    title={RACE_ENTRY.name}
                    isDirty={pending.onEntry(RACE_ENTRY)}
                    isValid={pending.onValid(RACE_ENTRY)}
                />
                <EditEthnicityDemographicCard id="ethnicity" form={form} sizing={sizing} />
                <EditSexBirthDemographicCard
                    id="sex-birth"
                    form={form}
                    sizing={sizing}
                    ageResolver={ageResolver}
                    entry={entry?.sexBirth}
                />
                <EditMortalityDemographicCard id="mortality" form={form} sizing={sizing} entry={entry?.mortality} />
                <EditGeneralInformationDemographicCard id="general-information" form={form} sizing={sizing} />
                <BackToTop sizing={sizing} target={content.current} />
            </div>
        </div>
    );
};

const PendingEntryMessage = ({ entry }: PendingMessageRendererProps) => (
    <>
        Data has been entered in the <a href={`#${entry.id}`}>{entry.name}</a> section. Please press Add or clear the
        data and submit again.
    </>
);

export { PatientDemographicsForm };
export type { PatientDemographicsFormProps };
