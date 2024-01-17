import { Button, Icon } from '@trussworks/react-uswds';
import styles from './section.module.scss';
import classNames from 'classnames';
import { MoreOptions } from 'apps/page-builder/components/MoreOptions/MoreOptions';

type Props = {
    name: string;
    subsectionCount: number;
    onAddSubsection: () => void;
    onExpandedChange: (expanded: boolean) => void;
    handleManageSection: () => void;
    isExpanded: boolean;
};
export const SectionHeader = ({
    name,
    subsectionCount,
    onAddSubsection,
    onExpandedChange,
    handleManageSection,
    isExpanded
}: Props) => {
    return (
        <div className={classNames(styles.header, { [styles.expanded]: isExpanded })}>
            <div className={styles.info}>
                <div className={styles.name}>{name}</div>
                <div className={styles.subsectionCount}>
                    {subsectionCount} subsection{subsectionCount > 1 ? 's' : ''}
                </div>
            </div>
            <div className={styles.buttons}>
                <Button type="button" onClick={onAddSubsection} outline>
                    Add subsection
                </Button>
                <MoreOptions header={<Icon.MoreVert size={4} />}>
                    <Button type="button" onClick={handleManageSection}>
                        <Icon.Edit size={3} /> Manage section
                    </Button>
                    <Button type="button" onClick={() => console.log('BLAH')}>
                        <Icon.Delete size={3} /> Delete
                    </Button>
                </MoreOptions>
                {isExpanded ? (
                    <Icon.ExpandLess size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
        </div>
    );
};
