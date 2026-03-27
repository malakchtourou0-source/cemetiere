import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InhumationNormale } from './inhumation-normale';

describe('InhumationNormale', () => {
  let component: InhumationNormale;
  let fixture: ComponentFixture<InhumationNormale>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InhumationNormale],
    }).compileComponents();

    fixture = TestBed.createComponent(InhumationNormale);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
