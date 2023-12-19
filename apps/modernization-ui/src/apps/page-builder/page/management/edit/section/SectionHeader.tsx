import { Button, Icon } from '@trussworks/react-uswds';
import styles from './section.module.scss';
import classNames from 'classnames';

type Props = {
    name: string;
    subsectionCount: number;
    onAddSubsection: () => void;
    onExpandedChange: (expanded: boolean) => void;
    isExpanded: boolean;
};
export const SectionHeader = ({ name, subsectionCount, onAddSubsection, onExpandedChange, isExpanded }: Props) => {
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
                <Icon.MoreVert size={4} />
                {isExpanded ? (
                    <Icon.ExpandLess size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
        </div>
    );
};
