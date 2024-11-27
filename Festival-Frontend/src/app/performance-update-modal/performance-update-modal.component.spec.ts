import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerformanceUpdateModalComponent } from './performance-update-modal.component';

describe('PerformanceUpdateModalComponent', () => {
  let component: PerformanceUpdateModalComponent;
  let fixture: ComponentFixture<PerformanceUpdateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerformanceUpdateModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PerformanceUpdateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
