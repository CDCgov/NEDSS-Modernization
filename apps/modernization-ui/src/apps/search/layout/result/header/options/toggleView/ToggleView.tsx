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
                    secondary={view !== 'table'}
                    active={view === 'table'}
                    icon="table"
                    onClick={asTable}
                    sizing={sizing}
                />
                <Button
                    aria-label="List view"
                    data-tooltip-position="top"
                    data-tooltip-offset="center"
                    icon="list"
                    onClick={asList}
                    secondary={view !== 'list'}
                    active={view === 'list'}
                    sizing={sizing}
                />
            </ButtonGroup>
        </div>
    );
};
