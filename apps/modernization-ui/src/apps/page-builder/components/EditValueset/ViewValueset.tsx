import { Concept, Valueset } from 'apps/page-builder/generated';
import { CloseableHeader } from '../CloseableHeader/CloseableHeader';
import { ValuesetDetails } from './ValuesetDetails';
import { Button, Icon } from '@trussworks/react-uswds';
import { ConceptSort } from 'apps/page-builder/hooks/api/useFindConcepts';
import { ConceptTable } from './concept/ConceptTable';
import { ButtonBar } from '../ButtonBar/ButtonBar';
import styles from './edit-valueset.module.scss';

type Props = {
    valueset: Valueset;
    isLoading: boolean;
    concepts: Concept[];
    onClose: () => void;
    onCancel: () => void;
    onAccept: () => void;
    onEditDetails: () => void;
    onAddConcept: () => void;
    onEditConcept: (concept: Concept) => void;
    onSort: (sort: ConceptSort | undefined) => void;
};
export const ViewValueset = ({
    valueset,
    isLoading,
    concepts,
    onClose,
    onCancel,
    onAccept,
    onEditDetails,
    onAddConcept,
    onEditConcept,
    onSort
}: Props) => {
    return (
        <>
            <CloseableHeader title={<div className={styles.addValuesetHeader}>Edit value set</div>} onClose={onClose} />
            <div className={styles.content}>
                <ValuesetDetails valueset={valueset} onEdit={onEditDetails} />
                <div className={styles.sectionText}>Value set concepts</div>
                <ConceptTable loading={isLoading} concepts={concepts} onSort={onSort} onEditConcept={onEditConcept} />
                {concepts.length === 0 ? (
                    <div className={styles.noConceptsSection}>
                        <div className={styles.noConceptText}>
                            No value set concept is displayed. Please click the button below to add a new value set
                            concept.
                        </div>
                        <div>
                            <Button type="button" outline onClick={onAddConcept}>
                                Add new concept
                            </Button>
                        </div>
                    </div>
                ) : (
                    <div className={styles.addConceptLinkSection}>
                        <button className={styles.addConceptButton} onClick={onAddConcept}>
                            <Icon.Add size={3} /> Add new concept
                        </button>
                    </div>
                )}
            </div>
            <ButtonBar>
                <Button onClick={onCancel} type="button" outline>
                    Cancel
                </Button>
                <Button disabled={concepts.length == 0} onClick={onAccept} type="button">
                    Continue
                </Button>
            </ButtonBar>
        </>
    );
};
