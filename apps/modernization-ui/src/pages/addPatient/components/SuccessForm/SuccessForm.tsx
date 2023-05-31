import { Button, ButtonGroup, Grid, Icon } from '@trussworks/react-uswds';
import { useNavigate } from 'react-router-dom';

export const SuccessForm = ({ data, setSuccessSubmit }: any) => {
    const navigate = useNavigate();

    function handleViewPatient(): void {
        // TODO: Hard coded to this profile for now. Once new patient creation is ready, we can pass that id instead.
        // At the time of this code, patient creation is not ready.
        navigate('/patient-profile/63000');
    }
    return (
        <Grid row>
            <Grid col={10} className="margin-x-auto margin-top-5">
                <Grid
                    row
                    className="flex-align-center bg-white border radius-md border-base-lighter margin-bottom-4 flex-justify-center padding-5 maregin-5">
                    <Grid col={12} className="text-center">
                        <Icon.CheckCircle style={{ fontSize: '100px', color: '#00A91C' }} />
                        <p style={{ fontSize: '32px', fontWeight: '700' }} className="margin-y-2">
                            You have successfully added “{data?.['first-name']}, {data?.['last-name']}”
                        </p>
                        <ButtonGroup className="flex-justify-center margin-top-3">
                            <Button type="button" onClick={handleViewPatient}>
                                View patient
                            </Button>
                            <Button onClick={() => setSuccessSubmit(false)} type="button" outline>
                                Add another patient
                            </Button>
                        </ButtonGroup>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    );
};
