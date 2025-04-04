import { Shown } from 'conditional-render';
import { NotificationCard } from './NotificationCard';

type Props = {
    passCount: number;
};
export const SelectPass = ({ passCount }: Props) => {
    return (
        <Shown
            when={passCount === 0}
            fallback={
                <NotificationCard
                    heading="Select a pass configuration"
                    body='To get started, select a pass configuration from the left to edit or click "Add pass configuration" to create a new pass.'
                />
            }>
            <NotificationCard
                heading="No pass configurations have been created"
                body={
                    <span>
                        To get started, select <strong>"Add pass configuration"</strong> from the left panel.
                    </span>
                }
            />
        </Shown>
    );
};
