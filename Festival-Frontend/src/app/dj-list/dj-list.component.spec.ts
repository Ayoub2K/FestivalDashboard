import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DjListComponent } from './dj-list.component';

describe('ItemListComponent', () => {
  let component: DjListComponent;
  let fixture: ComponentFixture<DjListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DjListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DjListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
