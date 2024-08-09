import { Grid } from '@trussworks/react-uswds';
import './FormCard.scss';

export default function FormCard({
    id,
    title,
    required,
    children
}: {
    id?: string;
    title?: string;
    required?: string;
    children: any;
}) {
    return (
        <section id={id}>
            <Grid row className="flex-align-center bg-white border radius-md border-base-lighter margin-bottom-4">
                <Grid col={12} className="font-sans-lg text-bold padding-2 border-bottom border-base-lighter">
                    <h2 data-testid="title" className="form-card-title margin-0">
                        {title} {required ? <span>{required}</span> : null}
                    </h2>
                </Grid>
                {children}
            </Grid>
        </section>
    );
}
