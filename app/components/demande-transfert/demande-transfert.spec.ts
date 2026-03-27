import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DemandeTransfert } from './demande-transfert';

describe('DemandeTransfert', () => {
  let component: DemandeTransfert;
  let fixture: ComponentFixture<DemandeTransfert>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DemandeTransfert],
    }).compileComponents();

    fixture = TestBed.createComponent(DemandeTransfert);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
