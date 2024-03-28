import React from 'react'
import { Input } from './Input'

describe('<Input />', () => {
  it('renders input with correct value', () => {
    const onChange = () => {};
    cy.mount(
      <Input
          id="test-input-id"
          name="test-input-name"
          label="Test Input Label"
          className="test-input-class-name"
          htmlFor="test-input-id"
          type="text"
          onChange={onChange}
          defaultValue="test-input-defaultValue"
          error="invalid input"
      />
    )
    cy.get('input').should('have.value', 'test-input-defaultValue')        
  })

  it('renders input with correct name', () => {
    const onChange = () => {};
    cy.mount(
      <Input
          id="test-input-id"
          name="test-input-name"
          label="Test Input Label"
          className="test-input-class-name"
          htmlFor="test-input-id"
          type="text"
          onChange={onChange}
          defaultValue="test-input-defaultValue"
          error="invalid input"
      />
    )
    cy.get('[name=test-input-name]').should('have.value', 'test-input-defaultValue')
  })  

  it('renders input that is visible', () => {
    const onChange = () => {};
    cy.mount(
      <Input
          id="test-input-id"
          name="test-input-name"
          label="Test Input Label"
          className="test-input-class-name"
          htmlFor="test-input-id"
          type="text"
          onChange={onChange}
          defaultValue="test-input-defaultValue"
          error="invalid input"
      />
    )    

        cy.contains('input').should('be.visible')
  })  
})