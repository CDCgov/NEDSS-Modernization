import { ButtonGroup, Icon } from '@trussworks/react-uswds';
import styles from '../search-results-options.module.scss';
import classNames from 'classnames';
import { Button } from 'components/button';
import { useSearchResultDisplay } from 'apps/search/useSearchResultDisplay';

export const ToggleView = () => {
    const { view, setView } = useSearchResultDisplay();

    return (
        <div className={classNames(styles.options)}>
            <strong>View as: </strong>
            <ButtonGroup type="segmented">
                <Button
                    className={classNames(styles.toggleButton)}
                    labelPosition="right"
                    icon={<Icon.GridView />}
                    onClick={() => setView('table')}
                    type="button"
                    outline={view !== 'table'}>
                    Table
                </Button>
                <Button
                    className={classNames(styles.toggleButton)}
                    labelPosition="right"
                    icon={<Icon.CheckCircle />}
                    onClick={() => setView('list')}
                    outline={view !== 'list'}
                    type="button">
                    List
                </Button>
            </ButtonGroup>
        </div>
    );
};
