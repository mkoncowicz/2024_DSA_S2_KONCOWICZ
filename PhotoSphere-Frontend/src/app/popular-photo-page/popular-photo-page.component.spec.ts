import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopularPhotoPageComponent } from './popular-photo-page.component';

describe('PopularPhotoPageComponent', () => {
  let component: PopularPhotoPageComponent;
  let fixture: ComponentFixture<PopularPhotoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PopularPhotoPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PopularPhotoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
