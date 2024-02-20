import { Valueset } from 'apps/page-builder/generated';
import styles from './edit-valueset.module.scss';
import { Icon } from '@trussworks/react-uswds';

type ValuesetDetailsProps = {
    onEdit: () => void;
    valueset: Valueset;
};
export const ValuesetDetails = ({ valueset, onEdit }: ValuesetDetailsProps) => {
    return (
        <>
            <div className={styles.detailsHeader}>
                <div className={styles.sectionText}>Value set details</div>
                <Icon.Edit size={3} onClick={onEdit} />
            </div>
            <div className={styles.valuesetInfo}>
                <div className={styles.data}>
                    <div className={styles.title}>VALUE SET TYPE</div>
                    <div>{valueset.type}</div>
                </div>
                <div className={styles.data}>
                    <div className={styles.title}>VALUE SET CODE</div>
                    <div>{valueset.code}</div>
                </div>
                <div className={styles.data}>
                    <div className={styles.title}>VALUE SET NAME</div>
                    <div>{valueset.name}</div>
                </div>
                <div className={styles.data}>
                    <div className={styles.title}>VALUE SET DESCRIPTION</div>
                    <div>{valueset.description}</div>
                </div>
            </div>
        </>
    );
};
