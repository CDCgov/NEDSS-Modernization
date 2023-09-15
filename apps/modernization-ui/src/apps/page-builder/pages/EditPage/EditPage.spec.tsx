import jwt from 'jsonwebtoken';


describe('when EditPage renders', () => {
    it('should fetch Page Details', () => {
        jest.spyOn(global, 'fetch').mockImplementationOnce(
            jest.fn(() => Promise.resolve()) as jest.Mock);
    });
});
