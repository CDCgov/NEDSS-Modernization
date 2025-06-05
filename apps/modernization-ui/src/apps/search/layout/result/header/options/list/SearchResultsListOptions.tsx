import { Button } from 'components/button';
import { Icon } from 'design-system/icon';
import { SortingPreferencesPanel } from 'design-system/sorting/preferences';
import { OverlayPanel } from 'overlay';

import styles from './search-results-list-options.module.scss';
import { Sizing } from 'design-system/field';
import React from 'react';

type Props = {
    disabled?: boolean;
    sizing?: Sizing;
    openerRef?: React.RefObject<HTMLButtonElement>;
};

const SearchResultsListOptions = ({ disabled = false, sizing, openerRef }: Props) => {
    return (
        <OverlayPanel
            className={styles.options}
            position="right"
            openerRef={openerRef}
            toggle={({ toggle }) => (
                <Button
                    ref={openerRef}
                    className={styles.opener}
                    aria-label="Sort list by"
                    data-tooltip-position="top"
                    data-tooltip-offset="left"
                    outline
                    disabled={disabled}
                    icon={<Icon name="sort_arrow" />}
                    onClick={toggle}
                    sizing={sizing}
                />
            )}
            render={(close) => <SortingPreferencesPanel onClose={close} />}
        />
    );
};

export { SearchResultsListOptions };
