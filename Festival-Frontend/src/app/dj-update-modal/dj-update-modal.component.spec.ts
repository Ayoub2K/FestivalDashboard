import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DjUpdateModalComponent } from './dj-update-modal.component';

describe('DjUpdateModalComponent', () => {
  let component: DjUpdateModalComponent;
  let fixture: ComponentFixture<DjUpdateModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DjUpdateModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DjUpdateModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
