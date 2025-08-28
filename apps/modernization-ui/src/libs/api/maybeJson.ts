/**
 * Attempts to extract a JSON payload from a {@link Response}.  If the response is not successful
 * the resulting {@link Promise} contains the rejected {@link Response}.
 *
 * @param {Response} response
 * @return {Promise}
 */
const maybeJson = (response: Response) => {
    if (response.ok) {
        return response.status !== 204 ? response.json().catch(() => Promise.resolve()) : Promise.resolve();
    } else {
        return Promise.reject(response);
    }
};

export { maybeJson };
