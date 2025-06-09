// import { render, screen, fireEvent } from '@testing-library/react';
// import { MergePreview } from './MergePreview';
//
// describe('MergePreview', () => {
//     it('renders the "Merge preview" heading', () => {
//         render(<MergePreview onBack={jest.fn()} />);
//         const heading = screen.getByRole('heading', { name: /merge preview/i });
//         expect(heading).toBeInTheDocument();
//     });
//
//     it('calls onBack when Back button is clicked', () => {
//         const onBackMock = jest.fn();
//         render(<MergePreview onBack={onBackMock} />);
//         const backButton = screen.getByRole('button', { name: /back/i });
//         fireEvent.click(backButton);
//         expect(onBackMock).toHaveBeenCalledTimes(1);
//     });
//
//     it('renders the "Merge record" button', () => {
//         render(<MergePreview onBack={jest.fn()} />);
//
//         const mergeButton = screen.getByRole('button', {
//             name: /confirm and merge patient records/i,
//         });
//
//         expect(mergeButton).toBeInTheDocument();
//     });
// });
