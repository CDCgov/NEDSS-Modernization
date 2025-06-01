import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';
import classNames from 'classnames';
import { Button } from 'components/button';
import { ButtonGroup } from 'design-system/button';
import { Sizing } from 'design-system/field';

import styles from './toggle-view.module.scss';

type ToggleViewProps = { sizing?: Sizing };

export const ToggleView = ({ sizing }: ToggleViewProps) => {
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
                    icon="table"
                    onClick={asTable}
                    sizing={sizing}
                />
                <Button
                    aria-label="List view"
                    data-tooltip-position="top"
                    data-tooltip-offset="center"
                    className={view === 'list' ? styles.active : ''}
                    icon="list"
                    onClick={asList}
                    outline={view !== 'list'}
                    sizing={sizing}
                />
            </ButtonGroup>
        </div>
    );
};
