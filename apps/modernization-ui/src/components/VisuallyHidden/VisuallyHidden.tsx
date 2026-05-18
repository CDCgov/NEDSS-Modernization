import React, { ReactNode } from 'react';
import styles from './VisuallyHidden.module.scss';

// From https://www.joshwcomeau.com/snippets/react-components/visually-hidden/
// Display text for screen readers only, but in dev mode can hold `Alt` to display the value
const VisuallyHidden = ({ children, ...delegated }: { children: ReactNode } & JSX.IntrinsicElements['span']) => {
    const [forceShow, setForceShow] = React.useState(false);

    React.useEffect(() => {
        if (import.meta.env.DEV) {
            const handleKeyDown = (ev: KeyboardEvent) => {
                if (ev.key === 'Alt') {
                    setForceShow(true);
                }
            };

            const handleKeyUp = (ev: KeyboardEvent) => {
                if (ev.key === 'Alt') {
                    setForceShow(false);
                }
            };

            window.addEventListener('keydown', handleKeyDown);
            window.addEventListener('keyup', handleKeyUp);

            return () => {
                window.removeEventListener('keydown', handleKeyDown);
                window.removeEventListener('keyup', handleKeyUp);
            };
        }
    }, []);

    if (forceShow) {
        return children;
    }

    return (
        <span className={styles['visually-hidden']} {...delegated}>
            {children}
        </span>
    );
};

export default VisuallyHidden;
