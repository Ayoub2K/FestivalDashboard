import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DjsComponent } from './djs.component';

describe('DjsComponent', () => {
  let component: DjsComponent;
  let fixture: ComponentFixture<DjsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DjsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DjsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
