import { useEffect, useState } from 'react';
import { Grid } from '@trussworks/react-uswds';
import { PatientBirth, PatientGender, useUpdatePatientBirthAndGenderMutation } from 'generated/graphql/schema';
import { PatientProfileBirthAndGenderResult, useFindPatientProfileBirth } from './useFindPatientProfileBirth';
import { internalizeDate } from 'date';
import { Data, EditableCard } from 'components/EditableCard';
import { maybeDescription, maybeId } from '../coded';
import { BirthAndGenderEntry, SexBirthForm } from './SexBirthForm';
import { maybeNumber, orNull } from 'utils';
import { useAlert } from 'alert/useAlert';
import { useParams } from 'react-router-dom';
import { usePatientProfile } from '../usePatientProfile';
import { useProfileContext } from '../ProfileContext';

const asView = (birth?: PatientBirth, gender?: PatientGender): Data[] => [
    {
        title: 'As of:',
        text: internalizeDate(birth?.asOf)
    },
    { title: 'Current age:', text: birth?.age?.toString() },
    { title: 'Date of birth:', text: internalizeDate(birth?.bornOn) },
    { title: 'Current sex:', text: gender?.current?.description },
    { title: 'Unknown reason:', text: maybeDescription(gender?.unknownReason) },
    { title: 'Transgender information:', text: maybeDescription(gender?.preferred) },
    { title: 'Additional gender:', text: gender?.additional },
    { title: 'Birth sex:', text: maybeDescription(gender?.birth) },
    { title: 'Multiple birth:', text: maybeDescription(birth?.multipleBirth) },
    { title: 'Birth order:', text: birth?.birthOrder?.toString() },
    { title: 'Birth city:', text: birth?.city },
    { title: 'Birth state:', text: maybeDescription(birth?.state) },
    { title: 'Birth county:', text: maybeDescription(birth?.county) },
    { title: 'Birth country:', text: maybeDescription(birth?.country) }
];

const asEntry = (birth: PatientBirth, gender: PatientGender): BirthAndGenderEntry => ({
    asOf: internalizeDate(birth.asOf),
    birth: {
        bornOn: internalizeDate(birth.bornOn),
        multipleBirth: maybeId(birth.multipleBirth),
        birthOrder: maybeNumber(birth.birthOrder),
        city: orNull(birth.city),
        state: maybeId(birth.state),
        county: maybeId(birth.county),
        country: maybeId(birth.country),
        gender: maybeId(gender.birth)
    },
    gender: {
        current: maybeId(gender.current),
        unknownReason: maybeId(gender.unknownReason),
        additional: orNull(gender.additional),
        preferred: maybeId(gender.preferred)
    }
});

const initialEntry = {
    asOf: null,
    birth: {
        bornOn: null,
        multipleBirth: null,
        birthOrder: null,
        city: null,
        state: null,
        county: null,
        country: null,
        gender: null
    },
    gender: {
        current: null,
        unknownReason: null,
        additional: null,
        preferred: null
    }
};

const initial = {
    view: asView(),
    entry: initialEntry
};

type BirthAndGenderState = {
    view: Data[];
    entry: BirthAndGenderEntry;
};

type Props = {
    patient: string;
};

export const SexBirth = ({ patient: patientId }: Props) => {
    const { showAlert } = useAlert();
    const { id } = useParams();
    const { changed } = useProfileContext();
    const [editing, isEditing] = useState<boolean>(false);
    const { patient } = usePatientProfile(id);

    const [state, setState] = useState<BirthAndGenderState>(initial);

    const handleComplete = (data: PatientProfileBirthAndGenderResult) => {
        const profile = data.findPatientProfile;

        if (profile) {
            const next = {
                view: asView(profile?.birth, profile?.gender),
                entry: asEntry(profile?.birth, profile?.gender)
            };
            setState(next);
        } else {
            setState(initial);
        }
    };

    const [fetch, { refetch }] = useFindPatientProfileBirth({ onCompleted: handleComplete });

    useEffect(() => {
        fetch({
            variables: {
                patient: patientId
            },
            notifyOnNetworkStatusChange: true
        });
    }, [patientId]);

    const [update] = useUpdatePatientBirthAndGenderMutation();

    const onUpdate = (updated: BirthAndGenderEntry) => {
        update({
            variables: {
                input: {
                    patient: patientId,
                    ...updated
                }
            }
        })
            .then(() => isEditing(false))
            .then(() => {
                refetch();
                showAlert({
                    type: 'success',
                    header: 'success',
                    message: `Updated sex & birth`
                });
                changed();
            });
    };

    return (
        <Grid col={12} className="margin-top-3 margin-bottom-2">
            <EditableCard
                readOnly={patient?.status !== 'ACTIVE'}
                title="Sex & birth"
                data={state.view}
                editing={editing}
                onEdit={() => isEditing(true)}>
                <SexBirthForm entry={state.entry} onChanged={onUpdate} onCancel={() => isEditing(false)} />
            </EditableCard>
        </Grid>
    );
};
