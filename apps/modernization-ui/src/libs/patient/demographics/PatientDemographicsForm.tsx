import { useCallback, useRef } from 'react';
import { UseFormReturn, useWatch } from 'react-hook-form';
import { Sizing } from 'design-system/field';
import { InPageNavigation } from 'design-system/inPageNavigation';
import { sections } from './sections';
import { BackToTop } from 'libs/page/back-to-top';
import { PatientDemographics, PatientDemographicsDefaults } from './demographics';
import { EditAdministrativeInformationCard } from './administrative';
import { EditGeneralInformationDemographicCard } from './general';
import { EditEthnicityDemographicCard } from './ethnicity';
import { EditMortalityDemographicCard } from './mortality';
import { EditSexBirthDemographicCard } from './sex-birth';
import { asOfAgeResolver } from 'date';
import { EditNameDemographicsCard } from './name';
import { EditAddressDemographicsCard } from './address';
import { EditPhoneEmailDemographicsCard } from './phoneEmail';
import { EditIdentificationDemographicsCard } from './identification';
import { EditRaceDemographicsCard } from './race';

import styles from './patient-demographics-form.module.scss';
import classNames from 'classnames';

type PatientDemographicsFormProps = {
    form: UseFormReturn<PatientDemographics>;
    defaults: PatientDemographicsDefaults;
    sizing?: Sizing;
} & JSX.IntrinsicElements['div'];

const PatientDemographicsForm = ({ form, defaults, sizing, className, ...remaining }: PatientDemographicsFormProps) => {
    const content = useRef<HTMLDivElement>(null);

    const deceasedOn = useWatch({ control: form.control, name: 'mortality.deceasedOn' });

    const ageResolver = useCallback(asOfAgeResolver(deceasedOn), [deceasedOn]);

    return (
        <div {...remaining} className={classNames(styles.demographics, className)}>
            <aside>
                <InPageNavigation sections={sections} />
            </aside>
            <div ref={content} className={styles.content}>
                <EditAdministrativeInformationCard id="administrative" form={form} sizing={sizing} />
                <EditNameDemographicsCard form={form} defaults={defaults} sizing={sizing} />
                <EditAddressDemographicsCard form={form} defaults={defaults} sizing={sizing} />
                <EditPhoneEmailDemographicsCard form={form} defaults={defaults} sizing={sizing} />
                <EditIdentificationDemographicsCard form={form} defaults={defaults} sizing={sizing} />
                <EditRaceDemographicsCard form={form} defaults={defaults} sizing={sizing} />
                <EditEthnicityDemographicCard id="ethnicity" form={form} sizing={sizing} />
                <EditSexBirthDemographicCard id="sex-birth" form={form} sizing={sizing} ageResolver={ageResolver} />
                <EditMortalityDemographicCard id="mortality" form={form} sizing={sizing} />
                <EditGeneralInformationDemographicCard id="general-information" form={form} sizing={sizing} />
                <BackToTop sizing={sizing} target={content.current} />
            </div>
        </div>
    );
};

export { PatientDemographicsForm };
export type { PatientDemographicsFormProps };
