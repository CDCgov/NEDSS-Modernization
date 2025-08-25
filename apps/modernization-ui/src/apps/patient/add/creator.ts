import { PatientDemographicsRequest } from 'libs/patient/demographics';
import { isFailure, post, RequestError } from 'libs/api';
import { Creator } from './api';

const creator: Creator = (demographics: PatientDemographicsRequest) =>
    fetch(post('/nbs/api/profile', demographics)).then(async (response) => {
        if (response.ok) {
            return await response.json();
        } else {
            const message = await response
                .json()
                .then((failed) => (isFailure(failed) ? failed.reason : 'An unexpected error occurred.'));

            throw new RequestError(response.status, message);
        }
    });

export { creator };
