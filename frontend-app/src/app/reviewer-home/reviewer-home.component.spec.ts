import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewerHomeComponent } from './reviewer-home.component';

describe('ReviewerHomeComponent', () => {
  let component: ReviewerHomeComponent;
  let fixture: ComponentFixture<ReviewerHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewerHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewerHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
