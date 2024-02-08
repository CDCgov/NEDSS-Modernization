import { Button, Icon } from '@trussworks/react-uswds';
import { Heading } from 'components/heading';
import { useState } from 'react';
import { ButtonBar } from '../../ButtonBar/ButtonBar';
import { CloseableHeader } from '../../CloseableHeader/CloseableHeader';
import { ValuesetSearchTable } from '../../ValuesetSearchTable/ValuesetSearchTable';
import styles from './valueset-search.module.scss';

type Props = {
    onCancel: () => void;
    onClose: () => void;
    onAccept: (selected: number) => void;
};
export const ValuesetSearch = ({ onCancel, onClose, onAccept }: Props) => {
    const [selectedValueset, setSelectedValueset] = useState<number | undefined>(undefined);
    return (
        <div className={styles.searchValueset}>
            <CloseableHeader
                title={
                    <div className={styles.addValuesetHeader}>
                        <Icon.ArrowBack onClick={onCancel} /> Add value set
                    </div>
                }
                onClose={onClose}
            />
            <div className={styles.scrollableContent}>
                <div className={styles.heading}>
                    <Heading level={3}>Let's find the right value set for your single choice question</Heading>
                </div>
                <div className={styles.tableContainer}>
                    <ValuesetSearchTable
                        onSelectionChange={setSelectedValueset}
                        valuesets={[]}
                        onCreateNew={() => {}}
                    />
                </div>
            </div>
            <ButtonBar>
                <Button onClick={onCancel} type="button" outline>
                    Cancel
                </Button>
                <Button
                    disabled={selectedValueset === undefined}
                    type="button"
                    onClick={() => selectedValueset && onAccept(selectedValueset)}>
                    Apply value set to question
                </Button>
            </ButtonBar>
        </div>
    );
};
