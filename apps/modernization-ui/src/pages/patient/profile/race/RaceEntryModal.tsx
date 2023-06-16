import { RefObject } from 'react';
import { ModalRef } from '@trussworks/react-uswds';
import { ModalComponent } from 'components/ModalComponent/ModalComponent';
import { RaceEntryForm } from './RaceEntryForm';
import { RaceEntry } from './RaceEntry';

type Props = {
    modal: RefObject<ModalRef>;
    action: string;
    entry: () => RaceEntry | undefined;
    onChange: (updated: RaceEntry) => void;
    onCancel: () => void;
};

export const RaceEntryModal = ({ modal, action, entry, onChange, onCancel }: Props) => {
    const resovled = entry();

    const body = resovled && <RaceEntryForm action={action} entry={resovled} onCancel={onCancel} onChange={onChange} />;

    return <ModalComponent modalRef={modal} modalHeading={`${action} - Race`} modalBody={body} />;
};
