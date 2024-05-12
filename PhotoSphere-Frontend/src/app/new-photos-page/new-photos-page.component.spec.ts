import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPhotosPageComponent } from './new-photos-page.component';

describe('NewPhotosPageComponent', () => {
  let component: NewPhotosPageComponent;
  let fixture: ComponentFixture<NewPhotosPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NewPhotosPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NewPhotosPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
