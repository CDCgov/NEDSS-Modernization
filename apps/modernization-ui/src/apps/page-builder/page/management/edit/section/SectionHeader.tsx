import { useState } from 'react';

import { Button, Icon } from '@trussworks/react-uswds';
import { MoreOptions } from 'apps/page-builder/components/MoreOptions/MoreOptions';
import classNames from 'classnames';

import styles from './section.module.scss';

type Props = {
    name: string;
    subsectionCount: number;
    onAddSubsection: () => void;
    onExpandedChange: (expanded: boolean) => void;
    handleEditSection: () => void;
    handleDeleteSection: () => void;
    handleManageSubsection: () => void;
    isExpanded: boolean;
};
export const SectionHeader = ({
    name,
    subsectionCount,
    onAddSubsection,
    onExpandedChange,
    handleEditSection,
    handleDeleteSection,
    handleManageSubsection,
    isExpanded,
}: Props) => {
    const [close, setClose] = useState(false);
    const handleDeleteSectionClick = () => {
        setClose(true);
        handleDeleteSection();
    };
    return (
        <div className={classNames(styles.header, { [styles.expanded]: isExpanded })}>
            <div className={styles.info}>
                <div className={styles.name}>
                    <h3>{name}</h3>
                </div>
                <div className={styles.subsectionCount}>
                    {subsectionCount} subsection{subsectionCount > 1 ? 's' : ''}
                </div>
            </div>
            <div className={styles.buttons}>
                <Button type="button" onClick={onAddSubsection} outline={true} data-testid="addNewSubsection">
                    Add subsection
                </Button>
                <Button
                    type="button"
                    onClick={handleManageSubsection}
                    outline={true}
                    data-testid={`manageSubsectionGearIcon-${subsectionCount ? 'yes' : 'no'}`}
                    className={styles.settingBtn}
                >
                    <Icon.Settings aria-label="manage" size={3} />
                </Button>
                <MoreOptions
                    header={<Icon.MoreVert aria-label="expand" size={4} onClick={() => setClose(false)} />}
                    close={close}
                    className={`moreOptionsSection-${subsectionCount ? 'yes' : 'no'}`}
                >
                    <Button type="button" onClick={handleEditSection}>
                        <Icon.Edit aria-label="edit" size={3} /> Edit section
                    </Button>

                    <Button type="button" onClick={handleDeleteSectionClick} className="deleteSectionBtn">
                        <Icon.Delete aria-label="delete" size={3} /> Delete section
                    </Button>
                </MoreOptions>
                {isExpanded ? (
                    <Icon.ExpandLess aria-label="collapse" size={4} onClick={() => onExpandedChange(false)} />
                ) : (
                    <Icon.ExpandMore aria-label="expand" size={4} onClick={() => onExpandedChange(true)} />
                )}
            </div>
        </div>
    );
};
