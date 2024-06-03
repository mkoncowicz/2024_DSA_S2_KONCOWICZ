import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExampleS3ApiComponent } from './example-s3-api.component';

describe('ExampleS3ApiComponent', () => {
  let component: ExampleS3ApiComponent;
  let fixture: ComponentFixture<ExampleS3ApiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExampleS3ApiComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ExampleS3ApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
