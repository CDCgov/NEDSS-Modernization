import { ReactNode } from 'react';
import { Shown } from 'conditional-render';
import { PendingEntry } from './pending';
import { AlertMessage } from 'design-system/message';

type PendingMessageRendererProps = { entry: PendingEntry };

type PendingMessageRenderer = ({ entry }: PendingMessageRendererProps) => ReactNode;

type PendingEntryAlertProps = {
    pending: PendingEntry[];
    title: string;
    renderer: PendingMessageRenderer;
};

const PendingEntryAlert = ({ pending, title, renderer }: PendingEntryAlertProps) => {
    return (
        <Shown when={pending.length > 0}>
            <div ref={scrollTo}>
                <AlertMessage title={title} type="error">
                    <ul>
                        {pending.map((entry, index) => (
                            <li key={index}>{renderer({ entry })}</li>
                        ))}
                    </ul>
                </AlertMessage>
            </div>
        </Shown>
    );
};

const scrollTo = (element: HTMLElement | null) => {
    if (element) {
        element.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
};

export { PendingEntryAlert };
export type { PendingMessageRendererProps, PendingMessageRenderer };
