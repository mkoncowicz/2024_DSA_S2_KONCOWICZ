import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewPhotoComponent } from './add-new-photo.component';

describe('AddNewPhotoComponent', () => {
  let component: AddNewPhotoComponent;
  let fixture: ComponentFixture<AddNewPhotoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddNewPhotoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddNewPhotoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
