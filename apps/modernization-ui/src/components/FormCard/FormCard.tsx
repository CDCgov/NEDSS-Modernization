import { Grid } from '@trussworks/react-uswds';
import './FormCard.spec';

export default function FormCard({ id, title, children }: { id?: string; title?: string; children: any }) {
    return (
        <section id={id}>
            <Grid row className="flex-align-center bg-white border radius-md border-base-lighter margin-bottom-4">
                <Grid
                    col={12}
                    className="font-sans-lg text-bold padding-2 border-bottom border-base-lighter"
                    data-testid="title">
                    <h2 className="form-card-title margin-0">{title}</h2>
                </Grid>
                {children}
            </Grid>
        </section>
    );
}
