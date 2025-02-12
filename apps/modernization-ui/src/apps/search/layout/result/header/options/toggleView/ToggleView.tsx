import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import { Icon } from 'design-system/icon';
import classNames from 'classnames';
import { Button } from 'components/button';

import styles from './toggle-view.module.scss';
import { ButtonGroup } from 'design-system/button';

export const ToggleView = () => {
    const { view, asList, asTable } = useSearchResultDisplay();

    return (
        <div className={classNames(styles.toggle)}>
            <ButtonGroup>
                <Button
                    aria-label="Table view"
                    data-tooltip-position="top"
                    data-tooltip-offset="center"
                    className={view === 'table' ? styles.active : ''}
                    outline={view !== 'table'}
                    icon={<Icon name="table" />}
                    onClick={asTable}
                    sizing="small"
                />
                <Button
                    aria-label="List view"
                    data-tooltip-position="top"
                    data-tooltip-offset="center"
                    className={view === 'list' ? styles.active : ''}
                    icon={<Icon name="list" />}
                    onClick={asList}
                    outline={view !== 'list'}
                    sizing="small"
                />
            </ButtonGroup>
        </div>
    );
};
