import { MergeMortality } from 'apps/deduplication/api/model/MergeCandidate';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { Controller, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataDisplay } from '../../shared/merge-data-display/MergeDataDisplay';
import { toDateDisplay } from '../../../../shared/toDateDisplay';

type Props = {
    personUid: string;
    mortality: MergeMortality;
    allowDetailedSelection: boolean;
};
export const Mortality = ({ personUid, mortality, allowDetailedSelection }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const [showAllOptions, setShowAllOptions] = useState<boolean>(false);

    useEffect(() => {
        setShowAllOptions(allowDetailedSelection && mortality.deceased === 'Yes');
    }, [allowDetailedSelection]);

    return (
        <section>
            <Shown when={showAllOptions}>
                <Controller
                    control={form.control}
                    name="mortality.asOf"
                    render={({ field }) => (
                        <MergeDataDisplay
                            label="As of date"
                            display={toDateDisplay(mortality.asOf)}
                            selectable={{
                                id: `${field.name}-${personUid}`,
                                formValue: personUid,
                                ...field
                            }}
                        />
                    )}
                />
                <MergeDataDisplay
                    label="Is the patient deceased?"
                    display={mortality.deceased}
                    groupType="last"
                    underlined
                />
                <Controller
                    control={form.control}
                    name="mortality.dateOfDeath"
                    render={({ field }) => (
                        <MergeDataDisplay
                            label="Date of death"
                            display={toDateDisplay(mortality.dateOfDeath)}
                            selectable={{
                                id: `${field.name}-${personUid}`,
                                formValue: personUid,
                                ...field
                            }}
                            underlined
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="mortality.deathCity"
                    render={({ field }) => (
                        <MergeDataDisplay
                            label="Death city"
                            display={mortality.deathCity}
                            selectable={{
                                id: `${field.name}-${personUid}`,
                                formValue: personUid,
                                ...field
                            }}
                            underlined
                        />
                    )}
                />
                <Controller
                    control={form.control}
                    name="mortality.deathState"
                    render={({ field }) => (
                        <MergeDataDisplay
                            label="Death state"
                            display={mortality.deathState}
                            selectable={{
                                id: `${field.name}-${personUid}`,
                                formValue: personUid,
                                ...field
                            }}
                        />
                    )}
                />
                <MergeDataDisplay label="Death county" display={mortality.deathCounty} groupType="last" underlined />
                <Controller
                    control={form.control}
                    name="mortality.deathCountry"
                    render={({ field }) => (
                        <MergeDataDisplay
                            label="Death country"
                            display={mortality.deathCountry}
                            selectable={{
                                id: `${field.name}-${personUid}`,
                                formValue: personUid,
                                ...field
                            }}
                        />
                    )}
                />
            </Shown>
            <Shown when={showAllOptions === false}>
                <Controller
                    control={form.control}
                    name="mortality.asOf"
                    render={({ field }) => (
                        <MergeDataDisplay
                            label="As of date"
                            display={toDateDisplay(mortality.asOf)}
                            selectable={{
                                id: `${field.name}-${personUid}`,
                                formValue: personUid,
                                ...field
                            }}
                        />
                    )}
                />
                <MergeDataDisplay label="Is the patient deceased?" display={mortality.deceased} groupType="linked" />
                <MergeDataDisplay
                    label="Date of death"
                    display={toDateDisplay(mortality.dateOfDeath)}
                    groupType="linked"
                />
                <MergeDataDisplay label="Death city" display={mortality.deathCity} groupType="linked" />
                <MergeDataDisplay label="Death state" display={mortality.deathState} groupType="linked" />
                <MergeDataDisplay label="Death county" display={mortality.deathCounty} groupType="linked" />
                <MergeDataDisplay label="Death country" display={mortality.deathCountry} groupType="last" />
            </Shown>
        </section>
    );
};
