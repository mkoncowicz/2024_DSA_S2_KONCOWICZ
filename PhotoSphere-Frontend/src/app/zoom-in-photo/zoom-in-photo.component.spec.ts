import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZoomInPhotoComponent } from './zoom-in-photo.component';

describe('ZoomInPhotoComponent', () => {
  let component: ZoomInPhotoComponent;
  let fixture: ComponentFixture<ZoomInPhotoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ZoomInPhotoComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ZoomInPhotoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
