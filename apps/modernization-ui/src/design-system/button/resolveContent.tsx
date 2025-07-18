import { Icon } from 'design-system/icon';
import { isLabeled, StandardButtonProps } from './buttons';

const resolveContent = (props: StandardButtonProps) => {
    const labeled = isLabeled(props);

    return (
        <>
            {props.icon && <Icon name={props.icon} />}
            {labeled && props.children}
        </>
    );
};

export { resolveContent };
